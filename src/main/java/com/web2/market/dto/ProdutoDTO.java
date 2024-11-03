package com.web2.market.dto;

import com.web2.market.domain.ProdutoEntity.Genero;
import java.time.LocalDate;

public record ProdutoDTO(
        Long id,
        String nomeProduto,
        String marca,
        LocalDate dataFabricacao,
        LocalDate dataValidade,
        Genero genero,
        String lote) {
}