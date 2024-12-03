package com.pedido;

import com.pedido.core.entities.Produto;
import com.pedido.core.enums.ProdutoCategoriaEnum;
import com.pedido.infrastructure.converters.ProdutoConverter;
import com.pedido.infrastructure.dtos.ProdutoDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoConverterTest {

    @Test
    public void testConverterDtoToProduto() {
        // Arrange
        ProdutoDto produtoDto = new ProdutoDto("asdfasdfase", "Produto Teste", "Descrição Teste", BigDecimal.TEN, ProdutoCategoriaEnum.A);

        // Act
        Produto produto = ProdutoConverter.converterDtoToProduto(produtoDto);

        // Assert
        assertThat(produto.getId()).isEqualTo(produtoDto.getId());
        assertThat(produto.getNome()).isEqualTo(produtoDto.getNome());
        assertThat(produto.getDescricao()).isEqualTo(produtoDto.getDescricao());
        assertThat(produto.getValor()).isEqualTo(produtoDto.getValor());
        assertThat(produto.getCategoria()).isEqualTo(produtoDto.getCategoria());
    }

}
