package com.pedido.infrastructure.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.util.Arrays;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class ViolacaoDominioErrorDecoder implements ErrorDecoder {

    ErrorDecoder defaultErrorDecoder = new Default();
    private static final Integer INTERNAL_SERVER_ERROR = 500;

    @Override
    public Exception decode(String methodKey, Response response) {
        List<HttpStatus> codigosFalha = Arrays.asList(HttpStatus.PRECONDITION_FAILED, HttpStatus.UNPROCESSABLE_ENTITY,
                HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND);


        try {
            String responseBody = Util.toString(response.body().asReader());
            ObjectMapper objectMapper = new ObjectMapper();
            Mensagem erro = objectMapper.readValue(responseBody, Mensagem.class);

            return new ViolacaoDominioExcecao(erro.mensagem(), String.valueOf(response.status()));
        } catch (Exception e) {
            throw new ViolacaoDominioExcecao("Erro interno do servidor", INTERNAL_SERVER_ERROR.toString(), e);
        }


    }
}

record Mensagem(String mensagem) {
}
