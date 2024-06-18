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

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.atualizadores.TelefoneAtualizador;
import com.autobots.automanager.repositorios.RepositorioTelefone;

@RestController
public class TelefoneControle {
    @Autowired
    private RepositorioTelefone repositorio;

    @Autowired
    private TelefoneAtualizador atualizador;

    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/telefone/{id}")
    public ResponseEntity<?> obterTelefone(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Telefone telefone = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(telefone);

            return new ResponseEntity<>(telefone, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/telefones")
    public ResponseEntity<?> obterTelefones() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Telefone> telefones = repositorio.findAll();
            adicionadorLink.adicionarLink(telefones);

            return new ResponseEntity<>(telefones, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/telefone/cadastro")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
        try {
            repositorio.save(telefone);

            adicionadorLink.adicionarLink(telefone);

            return new ResponseEntity<>(telefone, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/telefone/atualizar")
    public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Telefone telefone = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(telefone, atualizacao);
            repositorio.save(telefone);

            adicionadorLink.adicionarLink(telefone);

            return new ResponseEntity<>(telefone, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/telefone/excluir")
    public ResponseEntity<?> deletarTelefone(@PathVariable Telefone exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Telefone telefone = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(telefone);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
