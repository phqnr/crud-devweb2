package com.web2.market.domain;

import com.web2.market.dto.ProdutoDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity(name = "produtos") // entidade JPA que representa uma tabela no banco de dados
@Table(name = "produtos") // especifica a tabela
@Data // anotação lombok getter e setter para evitar boilerplate codes
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class ProdutoEntity {


    @Id // indica que o ID é uma PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que o id será autoincrement, gerado automaticamente pelo banco de dados
    private Long id;
    private String nomeProduto;
    private String marca;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING) // indica que é um enum e o valor será armazenado como uma String ao inves de um numero
    private Genero genero;
    private String lote;
    private boolean ativo = true;


    // Construtor Adicional para utilizar nas conversões entre DTO e JPA na transferência de dados
    public ProdutoEntity(ProdutoDTO produtoDTO) {
        this.id = produtoDTO.id();
        this.nomeProduto = produtoDTO.nomeProduto();
        this.marca = produtoDTO.marca();
        this.dataFabricacao = produtoDTO.dataFabricacao();
        this.dataValidade = produtoDTO.dataValidade();
        this.genero = produtoDTO.genero();
        this.lote = produtoDTO.lote();
    }

    public enum Genero {
        COSMETICO, ALIMENTICIO, HIGIENE_PESSOAL, LIMPEZA
    }
}

