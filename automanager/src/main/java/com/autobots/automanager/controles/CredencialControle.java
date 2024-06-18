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

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkCredencial;
import com.autobots.automanager.modelos.atualizadores.CredencialAtualizador;
import com.autobots.automanager.repositorios.RepositorioCredencial;

@RestController
public class CredencialControle {
    @Autowired
    private RepositorioCredencial repositorio;

    @Autowired
    private AdicionadorLinkCredencial adicionadorLink;

    @Autowired
    private CredencialAtualizador atualizador;

    @GetMapping("/credencial/{id}")
    public ResponseEntity<?> obterCredencial(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Credencial credencial = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(credencial);

            return new ResponseEntity<>(credencial, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/credencials")
    public ResponseEntity<?> obterCredencials() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Credencial> credencials = repositorio.findAll();
            adicionadorLink.adicionarLink(credencials);

            return new ResponseEntity<>(credencials, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/credencial/cadastro")
    public ResponseEntity<?> cadastrarCredencial(@RequestBody Credencial credencial) {
        try {
            repositorio.save(credencial);

            adicionadorLink.adicionarLink(credencial);

            return new ResponseEntity<>(credencial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/credencial/atualizar")
    public ResponseEntity<?> atualizarCredencial(@RequestBody Credencial atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Credencial credencial = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(credencial, atualizacao);
            repositorio.save(credencial);

            adicionadorLink.adicionarLink(credencial);

            return new ResponseEntity<>(credencial, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/credencial/excluir")
    public ResponseEntity<?> deletarCredencial(@RequestBody Credencial exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Credencial credencial = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
