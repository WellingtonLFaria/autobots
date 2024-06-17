package com.autobots.atvi.controles;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.atvi.atualizadores.ClienteAtualizador;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.repositorios.RepositorioCliente;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
            List<Cliente> todosClientes = repositorioCliente.findAll();
            if (todosClientes.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            todosClientes.forEach(cliente -> cliente
                    .add(linkTo(methodOn(ControleCliente.class).getCliente(cliente.getId())).withSelfRel()));

            Link link = linkTo(methodOn(ControleCliente.class).getClientes()).withSelfRel();
            CollectionModel<Cliente> resultado = CollectionModel.of(todosClientes, link);

            return new ResponseEntity<>(resultado, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCliente(@PathVariable Long id) {
        try {
            if (!repositorioCliente.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Cliente cliente = repositorioCliente.findById(id).get();
            cliente.add(linkTo(methodOn(ControleCliente.class).getCliente(cliente.getId())).withSelfRel());
            return new ResponseEntity<>(cliente, HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = repositorioCliente.save(cliente);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(clienteSalvo.getId()).toUri();

            EntityModel<Cliente> resource = EntityModel.of(clienteSalvo,
                    linkTo(methodOn(ControleCliente.class).getCliente(clienteSalvo.getId())).withSelfRel(),
                    linkTo(methodOn(ControleCliente.class).getClientes()).withRel("Todos os Clientes"));

            return ResponseEntity.created(location).body(resource);
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
            clienteBanco.add(linkTo(methodOn(ControleCliente.class).getCliente(cliente.getId())).withSelfRel());
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
            Cliente cliente = repositorioCliente.findById(id).get();
            cliente.add(linkTo(methodOn(ControleCliente.class).getClientes()).withRel("Todos os Clientes"));
            repositorioCliente.deleteById(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
