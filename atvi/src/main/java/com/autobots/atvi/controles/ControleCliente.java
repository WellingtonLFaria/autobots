package com.autobots.atvi.controles;

import org.springframework.web.bind.annotation.RestController;

import com.autobots.atvi.dtos.ClienteDTO;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.entidades.Documento;
import com.autobots.atvi.entidades.Endereco;
import com.autobots.atvi.entidades.Telefone;
import com.autobots.atvi.repositorios.RepositorioCliente;
import com.autobots.atvi.repositorios.RepositorioDocumento;
import com.autobots.atvi.repositorios.RepositorioEndereco;
import com.autobots.atvi.repositorios.RepositorioTelefone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private RepositorioDocumento repositorioDocumento;

    @Autowired
    private RepositorioEndereco repositorioEndereco;

    @Autowired
    private RepositorioTelefone repositorioTelefone;

    @GetMapping("")
    public ResponseEntity<List<Cliente>> getClientes() {
        try {
            return ResponseEntity.ok(repositorioCliente.findAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Cliente>> getCliente(@PathVariable Long id) {
        try {
            if (repositorioCliente.findById(id).isPresent() == false) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(repositorioCliente.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<Cliente> postCliente(@RequestBody ClienteDTO cliente) {
        List<Documento> documentos = new ArrayList<Documento>();
        List<Telefone> telefones = new ArrayList<Telefone>();
        try {
            for (Documento documento: cliente.documentos()) {
                documentos.add(repositorioDocumento.save(documento));
            }
            for (Telefone telefone: cliente.telefones()) {
                telefones.add(repositorioTelefone.save(telefone));
            }
            Endereco endereco = repositorioEndereco.save(cliente.endereco());
            
            Cliente clienteEntidade = new Cliente();
            clienteEntidade.setNome(cliente.nome());
            clienteEntidade.setNomeSocial(cliente.nomeSocial());
            clienteEntidade.setDataCadastro(cliente.dataCadastro());
            clienteEntidade.setDataNascimento(cliente.dataNascimento());
            clienteEntidade.setEndereco(endereco);
            clienteEntidade.setDocumentos(documentos);
            clienteEntidade.setTelefones(telefones);

            return ResponseEntity.ok(repositorioCliente.save(clienteEntidade));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Cliente> putMethodName(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (repositorioCliente.findById(id).isPresent() == false) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Cliente clienteEntidade = repositorioCliente.findById(id).get();
        clienteEntidade.setNome(cliente.getNome());
        clienteEntidade.setNomeSocial(cliente.getNomeSocial());
        clienteEntidade.setEndereco(cliente.getEndereco());
        clienteEntidade.setDataNascimento(cliente.getDataNascimento());
        clienteEntidade.setDocumentos(cliente.getDocumentos());
        clienteEntidade.setTelefones(cliente.getTelefones());

        repositorioCliente.save(clienteEntidade);

        return ResponseEntity.ok(repositorioCliente.findById(id).get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        try {
            repositorioCliente.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
