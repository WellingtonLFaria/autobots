package com.autobots.atvi.controles;

import org.springframework.web.bind.annotation.RestController;

import com.autobots.atvi.atualizadores.ClienteAtualizador;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.repositorios.RepositorioCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping("clientes")
@RestController
public class ControleCliente {
    @Autowired
    private RepositorioCliente repositorioCliente;

    private ClienteAtualizador clienteAtualizador = new ClienteAtualizador();

    @GetMapping
    public ResponseEntity<?> getClientes() {
        try {
            if (repositorioCliente.findAll().isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioCliente.findAll(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCliente(@PathVariable Long id) {
        try {
            if (!repositorioCliente.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioCliente.findById(id), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody Cliente cliente) {
        try {
            return new ResponseEntity<>(repositorioCliente.save(cliente), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            if (!repositorioCliente.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Cliente clienteBanco = repositorioCliente.findById(id).get();

            clienteBanco = clienteAtualizador.atualizar(clienteBanco, cliente);
            clienteBanco = repositorioCliente.save(clienteBanco);
            return new ResponseEntity<>(clienteBanco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            if (!repositorioCliente.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            repositorioCliente.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
