package com.pedido.infrastructure.feign;

import com.pedido.core.enums.ProdutoCategoriaEnum;
import com.pedido.infrastructure.dtos.ProdutoDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produto-servico", url = "${URL_PRODUTO}")
public interface ProdutoServicoExterno {

    @GetMapping("/api/produtos/{id}/produto")
    ProdutoDto findById(@PathVariable("id") String id);

    @GetMapping("/api/produtos/{categoria}")
    List<ProdutoDto> findByCategoria(ProdutoCategoriaEnum categoria);

}
