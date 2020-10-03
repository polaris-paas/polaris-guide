echo -e '\033[32m'
echo '============================================================='
echo '$                                                           $'
echo '$                      Nepxion Polaris                      $'
echo '$                                                           $'
echo '$                                                           $'
echo '$                                                           $'
echo '$  Nepxion Studio All Right Reserved                        $'
echo '$  Copyright (C) 2017-2050                                  $'
echo '$                                                           $'
echo '============================================================='

echo -n $'\e'"]0;Nepxion Polaris Console"$'\a'

# 只适用于Windows操作系统上
DOCKER_HOST='tcp://localhost:2375'

# 在DEV（本地）环境下，配置Docker容器连接容器外中间件服务器的地址
NACOS_SERVER=10.0.75.1:8848

IMAGE_NAME=polaris-console
MACHINE_PORT=6001
CONTAINER_PORT=6001
RUN_MODE='-i -t'
# Nepxion Discovery旗标在Docker容器控制台上以彩色模式显示
SHOWN_ANSI_MODE=true
# Nepxion Discovery灰度蓝绿的版本号、区域号，环境隔离路由的环境号的元数据
METADATA_VERSION=my-version
METADATA_REGION=my-region
METADATA_ENV=my-env
METADATA_ZONE=my-zone

if [ ! -d target ];then
  rmdir /s/q target
fi

# 执行Maven打包
mvn clean package -U -DskipTests

# 停止和删除Docker容器
docker stop ${IMAGE_NAME}
# docker kill ${IMAGE_NAME}
docker rm ${IMAGE_NAME}

# 删除Docker镜像
docker rmi ${IMAGE_NAME}

# 安装Docker镜像
docker build . --tag ${IMAGE_NAME}

# 安装和启动Docker容器，并自动执行端口映射
# Windows下运行需要改成“winpty docker run”
docker run ${RUN_MODE} -e JAVA_OPTS="-Dspring.cloud.nacos.discovery.server-addr=${NACOS_SERVER} -Dspring.cloud.nacos.config.server-addr=${NACOS_SERVER} -Dspring.cloud.sentinel.datasource.nacos.server-addr=${NACOS_SERVER} -Dnacos.server-addr=${NACOS_SERVER} -Dnepxion.banner.shown.ansi.mode=${SHOWN_ANSI_MODE} -Dmetadata.version=${METADATA_VERSION} -Dmetadata.region=${METADATA_REGION} -Dmetadata.env=${METADATA_ENV} -Dmetadata.zone=${METADATA_ZONE}" -e TZ="Asia/Shanghai" -p ${MACHINE_PORT}:${CONTAINER_PORT} -h ${IMAGE_NAME} --name ${IMAGE_NAME} ${IMAGE_NAME}:latest

function pause(){
  echo 'Press any key to continue...'
  read -n 1 -p "$*" str_inp
  if [ -z "$str_inp" ];then
    str_inp=1
  fi
    if [ $str_inp != '' ];then
      echo -ne '\b \n'
    fi
}

pause