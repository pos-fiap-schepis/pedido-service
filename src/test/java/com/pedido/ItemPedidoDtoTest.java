package com.pedido;

import com.pedido.infrastructure.dtos.ItemPedidoDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoDtoTest {

    @Test
    void testItemPedidoDtoCreation() {
        // Arrange
        Long expectedId = 123L;

        // Act
        ItemPedidoDto itemPedidoDto = new ItemPedidoDto(expectedId);

        // Assert
        assertNotNull(itemPedidoDto, "ItemPedidoDto instance should not be null");
        assertEquals(expectedId, itemPedidoDto.idItem(), "ID should match the expected value");
    }
}