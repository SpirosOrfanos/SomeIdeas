package com.etraveli.cardcost.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("app.config.bintable")
public class BinRestClientConfigProperties {
  private String url;
  private String apiKey;
}
