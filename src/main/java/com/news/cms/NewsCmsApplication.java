package com.news.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsCmsApplication.class, args);
        System.out.println("\n🚀 Uygulama çalışıyor: http://localhost:8080\n");
    }

}