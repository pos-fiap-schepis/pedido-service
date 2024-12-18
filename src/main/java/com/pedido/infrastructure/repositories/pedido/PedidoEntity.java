package com.pedido.infrastructure.repositories.pedido;

import com.pedido.core.enums.SituacaoPagamentoEnum;
import com.pedido.core.enums.StatusPedidoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "PEDIDO", schema = "PEDIDO")
@SequenceGenerator(name = "SQ_PEDIDO", sequenceName = "PEDIDO.SQ_PEDIDO", allocationSize = 1, initialValue = 1)
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEDIDO")
    private Long id;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "situacao_pagamento")
    @Enumerated(EnumType.STRING)
    private SituacaoPagamentoEnum situacaoPagamento;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum status;

    @Column(name = "data_hora_pagamento")
    private LocalDateTime dataHoraPagamento;

    @Column(name = "data_hora_criacao")
    private LocalDateTime dataHoraCriacao;

    @Column(name = "data_hora_entrega")
    private LocalDateTime dataHoraEntrega;

    @OneToMany(mappedBy = "pedido", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedidoEntity> itens;
}
