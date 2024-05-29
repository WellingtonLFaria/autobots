package com.autobots.atvi.controles;

import org.springframework.web.bind.annotation.RestController;

import com.autobots.atvi.atualizadores.ClienteAtualizador;
import com.autobots.atvi.dto.ClienteDTO;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.entidades.Documento;
import com.autobots.atvi.entidades.Telefone;
import com.autobots.atvi.repositorios.RepositorioCliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            if (!repositorioCliente.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(repositorioCliente.findById(id), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = new Cliente();

            // Verify if nome, nomeSocial or dataNascimento is null and return a BAD REQUEST if is
            if (clienteDTO.nome() == "" || clienteDTO.nome() == null) return new ResponseEntity<>("Missing nome", HttpStatus.BAD_REQUEST);
            if (clienteDTO.nomeSocial() == "" || clienteDTO.nomeSocial() == null) return new ResponseEntity<>("Missing nomeSocial", HttpStatus.BAD_REQUEST);
            if (clienteDTO.dataNascimento() == null) return new ResponseEntity<>("Missing dataNascimento", HttpStatus.BAD_REQUEST);

            // Set nome, nomeSocial, dataNascimento and dataCadastro into Cliente entity
            cliente.setNome(clienteDTO.nome());
            cliente.setNomeSocial(clienteDTO.nomeSocial());
            cliente.setDataNascimento(clienteDTO.dataNascimento());
            cliente.setDataCadastro(new Date());

            List<Documento> documentos = new ArrayList<>();
            List<Telefone> telefones = new ArrayList<>();
            cliente.setDocumentos(documentos);
            cliente.setTelefones(telefones);

            // Save the entity in the database
            cliente = repositorioCliente.save(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        try {
            if (!repositorioCliente.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Cliente cliente = repositorioCliente.findById(id).get();

            // Verify if nome, nomeSocial or dataNascimento is null and return a BAD REQUEST if is
            if (clienteDTO.nome() == "" || clienteDTO.nome() == null) return new ResponseEntity<>("Missing nome", HttpStatus.BAD_REQUEST);
            if (clienteDTO.nomeSocial() == "" || clienteDTO.nomeSocial() == null) return new ResponseEntity<>("Missing nomeSocial", HttpStatus.BAD_REQUEST);
            if (clienteDTO.dataNascimento() == null) return new ResponseEntity<>("Missing dataNascimento", HttpStatus.BAD_REQUEST);

            cliente = clienteAtualizador.atualizar(cliente, clienteDTO);
            cliente = repositorioCliente.save(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            if (!repositorioCliente.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            repositorioCliente.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
