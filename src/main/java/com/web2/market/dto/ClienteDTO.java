package com.web2.market.dto;

import com.web2.market.domain.ClienteEntity.Genero;
import java.time.LocalDate;

public record ClienteDTO(
        Long id,
        String nome,
        String cpf,
        Genero genero,
        LocalDate dataNascimento) {
}

