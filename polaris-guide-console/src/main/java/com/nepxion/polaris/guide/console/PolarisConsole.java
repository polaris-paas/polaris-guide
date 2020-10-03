package com.nepxion.polaris.guide.console;

import org.springframework.boot.builder.SpringApplicationBuilder;

import com.nepxion.polaris.framework.console.annotation.EnablePolarisConsole;

@EnablePolarisConsole
public class PolarisConsole {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PolarisConsole.class).run(args);
    }
}