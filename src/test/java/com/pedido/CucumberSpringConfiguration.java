package com.pedido;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = PedidoServiceApplication.class)
public class CucumberSpringConfiguration {

}
