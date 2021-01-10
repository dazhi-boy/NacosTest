package com.dazhi.nacos.core.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class StartingSpringApplicationRunListener implements SpringApplicationRunListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartingSpringApplicationRunListener.class);

    private volatile boolean starting;

    public StartingSpringApplicationRunListener(SpringApplication application, String[]  args){
        System.out.println("constructor");
    }
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("在run()方法开始执行时，该方法就立即被调用，可用于在初始化最早期时做一些工作");
        starting = true;
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {

        System.out.println("当environment构建完成，ApplicationContext创建之前，该方法被调用");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("当ApplicationContext构建完成时，该方法被调用");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("在ApplicationContext完成加载，但没有被刷新前，该方法被调用");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("在ApplicationContext刷新并启动后，CommandLineRunners和ApplicationRunner未被调用前，该方法被调用");
        starting = false;
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("在run()方法执行完成前该方法被调用");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("当应用运行出错时该方法被调用");
        starting = false;
    }
}
