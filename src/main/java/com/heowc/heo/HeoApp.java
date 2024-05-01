package com.heowc.heo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@CommandScan(basePackageClasses = HeoApp.class)
@SpringBootApplication
public class HeoApp {

    public static void main(String[] args) {
        SpringApplication.run(HeoApp.class, args);
    }

}
