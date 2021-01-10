package com.dazhi.nacos.sys.utils;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationUtils implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static ApplicationContext applicationContext;

    private static ConfigurableEnvironment environment;

    public static void injectEnvironment(ConfigurableEnvironment environment) {
        ApplicationUtils.environment = environment;
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        applicationContext = context;
        environment = context.getEnvironment();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\dazhi\\Desktop\\world.txt")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\Users\\dazhi\\Desktop\\world1.txt")));
        FileReader fileReader = null;
        StringBuilder builder = new StringBuilder();
        List<String> list = new ArrayList<String>();
        String line = null;

        while ((line = reader.readLine())!=null){
            list.add(line);
//            builder.append(line.trim()+"\r\n");
        }

        String s = list.stream().collect(Collectors.joining("\r\n"));

        writer.write(s);
        writer.close();
        reader.close();
    }
}
