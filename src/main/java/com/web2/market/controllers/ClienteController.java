package com.web2.market.controllers;

import com.web2.market.domain.ClienteEntity;
import com.web2.market.dto.ClienteDTO;
import com.web2.market.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity getAll() {
        var allClientes = clienteRepository.findAll();
        return ResponseEntity.ok(allClientes); // Retorna todos os clientes cadastrados do repositório /clientes

    }

    @GetMapping("/{id}")
    public ClienteEntity getById(@PathVariable Long id) {
        return (ClienteEntity) clienteRepository.findById(id).orElse(null); // Retorna um cliente específico através do id, caso não, retorna null
    }

    @PostMapping
    public ResponseEntity postCliente(@RequestBody ClienteDTO cliente){
        ClienteEntity newCliente = new ClienteEntity(cliente);
        clienteRepository.save(newCliente);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    public ResponseEntity<ClienteEntity> updateCliente(@RequestBody ClienteDTO cliente) {
        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(cliente.id()); // Verifica se o cliente existe através do id

        if (clienteOptional.isPresent()) {
            ClienteEntity clienteEntity = clienteOptional.get();

            // Atualiza os campos que não são nulos
            if (cliente.nome() != null) {
                clienteEntity.setNome(cliente.nome());
            }
            if (cliente.cpf() != null) {
                clienteEntity.setCpf(cliente.cpf());
            }
            if (cliente.genero() != null) {
                clienteEntity.setGenero(cliente.genero());
            }
            if (cliente.dataNascimento() != null) {
                clienteEntity.setDataNascimento(cliente.dataNascimento());
            }

            clienteRepository.save(clienteEntity);
            return ResponseEntity.ok(clienteEntity); // Retorna o produto atualizado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o produto não for encontrado
        }
    }

    @PutMapping("/deleteLogic/{id}")
    public ResponseEntity<String> deleteLogicCliente(@PathVariable Long id) {
        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            ClienteEntity cliente = clienteOptional.get();
            cliente.setAtivo(false); // Marca o cliente como inativo
            clienteRepository.save(cliente);
            return ResponseEntity.ok("Cliente desativado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok("Cliente removido com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }

}