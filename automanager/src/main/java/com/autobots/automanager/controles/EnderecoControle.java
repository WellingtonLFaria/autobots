package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.atualizadores.EnderecoAtualizador;
import com.autobots.automanager.repositorios.RepositorioEndereco;


@RestController
public class EnderecoControle {
    @Autowired
    private RepositorioEndereco repositorio;

    @Autowired
    private EnderecoAtualizador atualizador;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping("/endereco/{id}")
    public ResponseEntity<?> obterEndereco(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Endereco endereco = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(endereco);

            return new ResponseEntity<>(endereco, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/enderecos")
    public ResponseEntity<?> obterEnderecos() {
        try {
            if (repositorio.findAll().isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<Endereco> enderecos = repositorio.findAll();
            adicionadorLink.adicionarLink(enderecos);

            return new ResponseEntity<>(enderecos, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/endereco/cadastro")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
        try {
            repositorio.save(endereco);

            adicionadorLink.adicionarLink(endereco);

            return new ResponseEntity<>(endereco, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/endereco/atualizar")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Endereco endereco = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(endereco, atualizacao);
            repositorio.save(endereco);

            adicionadorLink.adicionarLink(endereco);

            return new ResponseEntity<>(endereco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/endereco/excluir")
    public ResponseEntity<?> deletarEndereco(@RequestBody Endereco exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Endereco endereco = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(endereco);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
