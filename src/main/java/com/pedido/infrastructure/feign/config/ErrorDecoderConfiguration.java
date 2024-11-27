package com.pedido.infrastructure.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDecoderConfiguration {

    @Bean
    public ViolacaoDominioErrorDecoder violacaoDominioErrorDecoder() {
        return new ViolacaoDominioErrorDecoder();
    }

}
