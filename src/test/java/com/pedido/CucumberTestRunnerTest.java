package com.pedido;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources", // Caminho para o arquivo .feature
        glue = "com.pedido",   // Pacote onde os steps estão implementados
        plugin = {"pretty", "summary"}
)
public class CucumberTestRunnerTest {
}