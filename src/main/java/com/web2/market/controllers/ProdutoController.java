package com.web2.market.controllers;

import com.web2.market.domain.ProdutoEntity;
import com.web2.market.dto.ProdutoDTO;
import com.web2.market.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/*
    @RestController: é usada para marcar a classe como um controller padrão MVC  (Model-View-Controller);
    @RequestMapping: especifica a URL que um método do controlador irá manipular. Neste caso, localhost:8080/produtos.
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    /*
       @Autowired: é utilizada para injetar dependências automaticamente em uma classe, permitindo obter
                    uma instância de uma classe necessária sem criar manualmente.
                    Neste caso, a instância é classe ProdutoRepository injetando na variável de mesmo nome.

       @GetMapping: anotação usada para o metodo getAll() e getById() indicando que é um endpoint que responderá
                    às requisições GET do HTTP.

       @PostMapping: usada para indicar que o método deve responder as requisições POST do HTTP.

       @PutMapping: indica que o método deve responder as requisições PUT através do id.

       @DeleteMapping: anotação usada para responder as requisições de DELETE por id.

    */
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity getAll() {
        var allProdutos = produtoRepository.findAll();
        return ResponseEntity.ok(allProdutos); // Retorna todos os produtos do repositório /produtos
    }

    @GetMapping("/{id}")
    public ProdutoEntity getById(@PathVariable Long id) {
        return (ProdutoEntity) produtoRepository.findById(id).orElse(null); // Retorna um produto específico através do id, caso não, retorna null
    }

    @PostMapping
    public ResponseEntity postProduto(@RequestBody ProdutoDTO produto){
        ProdutoEntity newProduto = new ProdutoEntity(produto);
        produtoRepository.save(newProduto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<ProdutoEntity> updateProduto(@RequestBody ProdutoDTO produto) {
        Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(produto.id()); // Verifica se o produto existe através do id

        // Atualiza os campos que não são nulos
        if (produtoOptional.isPresent()) {
            ProdutoEntity produtoEntity = produtoOptional.get();

            // Atualiza os campos que não são nulos
            if (produto.nomeProduto() != null) {
                produtoEntity.setNomeProduto(produto.nomeProduto());
            }
            if (produto.marca() != null) {
                produtoEntity.setMarca(produto.marca());
            }
            if (produto.dataFabricacao() != null) {
                produtoEntity.setDataFabricacao(produto.dataFabricacao());
            }
            if (produto.dataValidade() != null) {
                produtoEntity.setDataValidade(produto.dataValidade());
            }
            if (produto.genero() != null) {
                produtoEntity.setGenero(produto.genero());
            }
            if (produto.lote() != null) {
                produtoEntity.setLote(produto.lote());
            }

            produtoRepository.save(produtoEntity);
            return ResponseEntity.ok(produtoEntity); // Retorna o produto atualizado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o produto não for encontrado
        }
    }

    @PutMapping("/deleteLogic/{id}")
    public ResponseEntity<String> deleteLogicProduto(@PathVariable Long id) {
        Optional<ProdutoEntity> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isPresent()) {
            ProdutoEntity produto = optionalProduto.get();
            produto.setAtivo(false); // Marca o produto como inativo
            produtoRepository.save(produto); // Salva a alteração
            return ResponseEntity.ok("Produto desativado com sucesso!"); // Resposta de sucesso
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado."); // Resposta se o produto não existir
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduto(@PathVariable Long id) {
        Optional<ProdutoEntity> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            produtoRepository.deleteById(id);
            return ResponseEntity.ok("Produto removido com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }
}
