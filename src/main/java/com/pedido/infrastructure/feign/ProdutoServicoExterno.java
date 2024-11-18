package com.pedido.infrastructure.feign;

import com.pedido.core.enums.ProdutoCategoriaEnum;
import com.pedido.infrastructure.dtos.ProdutoDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "produto-servico", url = "http://localhost:8081")
public interface ProdutoServicoExterno {

    @GetMapping("/produto/{id}")
    ProdutoDto findById(Long id);

    @GetMapping("/produto/{categoria}")
    List<ProdutoDto> findByCategoria(ProdutoCategoriaEnum categoria);

}
