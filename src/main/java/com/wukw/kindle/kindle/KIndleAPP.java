package com.wukw.kindle.kindle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KIndleAPP {

    public static ApplicationContext springContext= null;
    public static void main(String[] args) {

        springContext= SpringApplication.run(KIndleAPP.class,args);
    }
}
