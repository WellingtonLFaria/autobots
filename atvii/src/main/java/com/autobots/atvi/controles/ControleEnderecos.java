package com.autobots.atvi.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.atvi.atualizadores.EnderecoAtualizador;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.entidades.Endereco;
import com.autobots.atvi.repositorios.RepositorioCliente;
import com.autobots.atvi.repositorios.RepositorioEndereco;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("enderecos")
public class ControleEnderecos {
    @Autowired
    private RepositorioEndereco repositorioEndereco;

    @Autowired
    private RepositorioCliente repositorioCliente;

    private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();

    @GetMapping
    public ResponseEntity<?> getEnderecos() {
        try {
            if (repositorioEndereco.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioEndereco.findAll(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getEndereco(@PathVariable Long id) {
        try {
            if (!repositorioEndereco.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioEndereco.findById(id).get(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEndereco(@PathVariable Long id) {
        try {
            if (!repositorioEndereco.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            repositorioEndereco.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putMapping(@PathVariable Long id, @RequestBody Endereco endereco) {
        try {
            if (!repositorioEndereco.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Endereco enderecoBanco = repositorioEndereco.findById(id).get();
            enderecoBanco = enderecoAtualizador.atualizar(enderecoBanco, endereco);
            return new ResponseEntity<>(enderecoBanco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{clienteID}")
    public ResponseEntity<?> postMapping(@PathVariable Long clienteID, @RequestBody Endereco endereco) {
        try {
            if (!repositorioCliente.findById(clienteID).isPresent()) return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
            Cliente cliente = repositorioCliente.findById(clienteID).get();
            if (cliente.getEndereco() != null) return new ResponseEntity<>("Cliente já possui endereço", HttpStatus.BAD_REQUEST);

            cliente.setEndereco(endereco);
            return new ResponseEntity<>(repositorioEndereco.save(endereco), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
