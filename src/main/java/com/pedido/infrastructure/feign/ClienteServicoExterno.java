package com.pedido.infrastructure.feign;

import com.pedido.core.entities.Cliente;
import com.pedido.infrastructure.dtos.ClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-servico", url = "http://localhost:8080")
public interface ClienteServicoExterno {

    @GetMapping("/api/clientes/{id}/cliente")
    ClienteDto findById(@PathVariable("id") Long id);

}
