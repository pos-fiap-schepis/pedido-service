package com.pedido.core.enums;

public enum StatusPagamentoEnum {

    A("APROVADO"),
    R("RECUSADO");
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    StatusPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }

}
