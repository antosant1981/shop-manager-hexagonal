package org.shop.manager.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopManagerApplication.class, args);
    }

}
