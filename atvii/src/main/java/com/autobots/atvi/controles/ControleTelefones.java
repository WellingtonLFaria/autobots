package com.autobots.atvi.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.atvi.atualizadores.TelefoneAtualizador;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.entidades.Telefone;
import com.autobots.atvi.repositorios.RepositorioCliente;
import com.autobots.atvi.repositorios.RepositorioTelefone;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("telefones")
public class ControleTelefones {
    @Autowired
    private RepositorioTelefone repositorioTelefone;

    @Autowired
    private RepositorioCliente repositorioCliente;

    private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

    @GetMapping
    public ResponseEntity<?> getTelefones() {
        try {
            if (repositorioTelefone.findAll().isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioTelefone.findAll(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTelefone(@PathVariable Long id) {
        try {
            if (!repositorioTelefone.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioTelefone.findById(id).get(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTelefones(@PathVariable Long id) {
        try {
            if (!repositorioTelefone.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            repositorioTelefone.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putTelefone(@PathVariable Long id, @RequestBody Telefone atualizacao) {
        try {
            if (!repositorioTelefone.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Telefone telefoneBanco = repositorioTelefone.findById(id).get();
            telefoneBanco = telefoneAtualizador.atualizar(telefoneBanco, atualizacao);
            return new ResponseEntity<>(telefoneBanco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{clienteID}")
    public ResponseEntity<?> postTelefone(@PathVariable Long clienteID, @RequestBody Telefone telefone) {
        try {
            if (!repositorioCliente.findById(clienteID).isPresent())
                return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
            Cliente cliente = repositorioCliente.findById(clienteID).get();
            cliente.getTelefones().add(telefone);
            cliente = repositorioCliente.save(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
