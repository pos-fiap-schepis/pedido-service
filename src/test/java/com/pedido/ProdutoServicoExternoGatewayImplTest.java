package com.pedido;

import com.pedido.core.entities.Produto;
import com.pedido.core.enums.ProdutoCategoriaEnum;
import com.pedido.infrastructure.dtos.ProdutoDto;
import com.pedido.infrastructure.feign.ProdutoServicoExterno;
import com.pedido.infrastructure.feign.ProdutoServicoExternoGatewayImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ProdutoServicoExternoGatewayImplTest {

    @Mock
    private ProdutoServicoExterno produtoServicoExterno;

    @InjectMocks
    private ProdutoServicoExternoGatewayImpl produtoServicoExternoGatewayImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProdutoById() {
        ProdutoDto produtoDto = new ProdutoDto("asdfas", "Produto Teste", "Descrição Teste", BigDecimal.TEN,
                ProdutoCategoriaEnum.A);
        when(produtoServicoExterno.findById(anyString())).thenReturn(produtoDto);

        Produto produto = produtoServicoExternoGatewayImpl.getProdutoById("1");

        assertEquals(produtoDto.getId(), produto.getId());
        assertEquals(produtoDto.getNome(), produto.getNome());
        assertEquals(produtoDto.getDescricao(), produto.getDescricao());
        assertEquals(produtoDto.getValor(), produto.getValor());
        assertEquals(produtoDto.getCategoria(), produto.getCategoria());
    }

}
