package com.etraveli.cardcost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import com.etraveli.cardcost.exceptions.ExternalCommunicationException;
import com.etraveli.cardcost.service.external.BinRestClient;
import reactor.core.publisher.Mono;

@Configuration
public class BinRestClientConfig {
  @Bean
  public BinRestClient characterClient(BinRestClientConfigProperties properties) {
    WebClient webClient =
        WebClient.builder().baseUrl(properties.getUrl())
            .defaultStatusHandler(httpStatusCode -> HttpStatus.NOT_FOUND == httpStatusCode,
                response -> Mono.empty())
            .defaultStatusHandler(HttpStatusCode::is5xxServerError,
                response -> Mono
                    .error(new ExternalCommunicationException(response.statusCode().value())))
            .build();

    return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build()
        .createClient(BinRestClient.class);
  }
}
