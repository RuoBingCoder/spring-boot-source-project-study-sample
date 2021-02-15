package com.github.spring.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBeansExploreSampleApplication {

    public static void main(String[] args) {
        System.out.println("     ██                               ██                ███████           ██      ██              \n" +
                "    ████    ██████                   ░██               ░██░░░░██         ░██     ░██              \n" +
                "   ██░░██  ░██░░░██  ██████    █████ ░██       █████   ░██    ░██ ██   ██░██     ░██       ██████ \n" +
                "  ██  ░░██ ░██  ░██ ░░░░░░██  ██░░░██░██████  ██░░░██  ░██    ░██░██  ░██░██████ ░██████  ██░░░░██\n" +
                " ██████████░██████   ███████ ░██  ░░ ░██░░░██░███████  ░██    ░██░██  ░██░██░░░██░██░░░██░██   ░██\n" +
                "░██░░░░░░██░██░░░   ██░░░░██ ░██   ██░██  ░██░██░░░░   ░██    ██ ░██  ░██░██  ░██░██  ░██░██   ░██\n" +
                "░██     ░██░██     ░░████████░░█████ ░██  ░██░░██████  ░███████  ░░██████░██████ ░██████ ░░██████ \n" +
                "░░      ░░ ░░       ░░░░░░░░  ░░░░░  ░░   ░░  ░░░░░░   ░░░░░░░    ░░░░░░ ░░░░░   ░░░░░    ░░░░░░  \n");
        SpringApplication.run(SpringBeansExploreSampleApplication.class, args);
    }

}
