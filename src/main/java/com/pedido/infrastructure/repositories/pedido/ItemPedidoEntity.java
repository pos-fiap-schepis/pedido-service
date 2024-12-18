package com.pedido.infrastructure.repositories.pedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ITEM_PEDIDO", schema = "PEDIDO")
@SequenceGenerator(name = "SQ_ITEM_PEDIDO", sequenceName = "PEDIDO.SQ_ITEM_PEDIDO", allocationSize = 1, initialValue = 1)
public class ItemPedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ITEM_PEDIDO")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_produto", nullable = false)
    private String produto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedido;

    @Column(name = "quantidade")
    private Integer quantidade;

}
