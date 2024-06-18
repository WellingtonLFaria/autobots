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

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkEmail;
import com.autobots.automanager.modelos.atualizadores.EmailAtualizador;
import com.autobots.automanager.repositorios.RepositorioEmail;

@RestController
public class EmailControle {
    @Autowired
    private RepositorioEmail repositorio;

    @Autowired
    private AdicionadorLinkEmail adicionadorLink;

    @Autowired
    private EmailAtualizador atualizador;

    @GetMapping("/email/{id}")
    public ResponseEntity<?> obterEmail(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Email email = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(email);

            return new ResponseEntity<>(email, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/emails")
    public ResponseEntity<?> obterEmails() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Email> emails = repositorio.findAll();
            adicionadorLink.adicionarLink(emails);

            return new ResponseEntity<>(emails, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/email/cadastro")
    public ResponseEntity<?> cadastrarEmail(@RequestBody Email email) {
        try {
            repositorio.save(email);

            adicionadorLink.adicionarLink(email);

            return new ResponseEntity<>(email, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/email/atualizar")
    public ResponseEntity<?> atualizarEmail(@RequestBody Email atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Email email = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(email, atualizacao);
            repositorio.save(email);

            adicionadorLink.adicionarLink(email);

            return new ResponseEntity<>(email, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/email/excluir")
    public ResponseEntity<?> deletarEmail(@RequestBody Email exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Email email = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
