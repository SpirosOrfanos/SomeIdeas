package com.etraveli.cardcost.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Profile(value = "default")
@Configuration

public class SwaggerConfig {
  @Bean
  public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.etraveli.cardcost.api"))
              .paths(PathSelectors.any())
              .build()
              .apiInfo(apiInfo());
  }
  
  private ApiInfo apiInfo() {
    return new ApiInfo(
      "Card clearance API", 
      "Card clearance API", 
      "1.0", 
      "", 
      "na@dummy.com",
      "Apache 2.0", 
      "Apache 2.0");
}

}
