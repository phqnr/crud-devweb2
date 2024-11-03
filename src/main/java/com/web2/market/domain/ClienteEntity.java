package com.web2.market.domain;

import com.web2.market.dto.ClienteDTO;
import com.web2.market.dto.ProdutoDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity(name = "clientes")
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Genero genero;
    private LocalDate dataNascimento;
    private boolean ativo = true;

    public ClienteEntity(ClienteDTO clienteDTO) {
        this.id = clienteDTO.id();
        this.nome = clienteDTO.nome();
        this.cpf = clienteDTO.cpf();
        this.genero = clienteDTO.genero();
        this.dataNascimento = clienteDTO.dataNascimento();

    }

    public enum Genero {
        MASCULINO, FEMININO, OUTROS
    }
}