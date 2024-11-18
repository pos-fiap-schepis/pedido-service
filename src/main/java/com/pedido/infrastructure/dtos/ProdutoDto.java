package com.pedido.infrastructure.dtos;

import com.pedido.core.enums.ProdutoCategoriaEnum;
import java.math.BigDecimal;

public class ProdutoDto {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal valor;

    private ProdutoCategoriaEnum categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public ProdutoCategoriaEnum getCategoria() {
        return categoria;
    }

    public void setCategoria(ProdutoCategoriaEnum categoria) {
        this.categoria = categoria;
    }
}
