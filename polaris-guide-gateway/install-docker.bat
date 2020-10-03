@echo on
@echo =============================================================
@echo $                                                           $
@echo $                      Nepxion Polaris                      $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Nepxion Studio All Right Reserved                        $
@echo $  Copyright (C) 2017-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title Nepxion Polaris Gateway
@color 0a

@rem 只适用于Windows操作系统上
@set DOCKER_HOST=tcp://localhost:2375

@rem 在DEV（本地）环境下，配置Docker容器连接容器外中间件服务器的地址
@set NACOS_SERVER=10.0.75.1:8848

@set IMAGE_NAME=polaris-gateway
@set MACHINE_PORT=5001
@set CONTAINER_PORT=5001
@set RUN_MODE=-i -t
@rem Nepxion Discovery旗标在Docker容器控制台上以彩色模式显示
@set SHOWN_ANSI_MODE=true
@rem Nepxion Discovery灰度蓝绿的版本号、区域号，环境隔离路由的环境号的元数据
@set METADATA_VERSION=my-version
@set METADATA_REGION=my-region
@set METADATA_ENV=my-env
@set METADATA_ZONE=my-zone

if exist target rmdir /s/q target

@rem 执行Maven打包
call mvn clean package -U -DskipTests

@rem 停止和删除Docker容器
call docker stop %IMAGE_NAME%
@rem call docker kill %IMAGE_NAME%
call docker rm %IMAGE_NAME%

@rem 删除Docker镜像
call docker rmi %IMAGE_NAME%

@rem 安装Docker镜像
call docker build . --tag %IMAGE_NAME%

@rem 安装和启动Docker容器，并自动执行端口映射
call docker run %RUN_MODE% -e JAVA_OPTS="-Dspring.cloud.nacos.discovery.server-addr=%NACOS_SERVER% -Dspring.cloud.nacos.config.server-addr=%NACOS_SERVER% -Dspring.cloud.sentinel.datasource.nacos.server-addr=%NACOS_SERVER% -Dnacos.server-addr=%NACOS_SERVER% -Dnepxion.banner.shown.ansi.mode=%SHOWN_ANSI_MODE% -Dmetadata.version=%METADATA_VERSION% -Dmetadata.region=%METADATA_REGION% -Dmetadata.env=%METADATA_ENV% -Dmetadata.zone=%METADATA_ZONE%" -e TZ="Asia/Shanghai" -p %MACHINE_PORT%:%CONTAINER_PORT% -h %IMAGE_NAME% --name %IMAGE_NAME% %IMAGE_NAME%:latest

pause