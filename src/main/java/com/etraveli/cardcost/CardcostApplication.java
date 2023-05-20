package com.etraveli.cardcost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class CardcostApplication {

  public static void main(String[] args) {
    SpringApplication.run(CardcostApplication.class, args);
  }

}
