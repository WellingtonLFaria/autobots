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

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelos.atualizadores.MercadoriaAtualizador;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
public class MercadoriaControle {
    @Autowired
    private RepositorioMercadoria repositorio;

    @Autowired
    private AdicionadorLinkMercadoria adicionadorLink;

    @Autowired
    private MercadoriaAtualizador atualizador;

    @GetMapping("/mercadoria/{id}")
    public ResponseEntity<?> obterMercadoria(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Mercadoria mercadoria = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(mercadoria);

            return new ResponseEntity<>(mercadoria, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mercadorias")
    public ResponseEntity<?> obterMercadorias() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Mercadoria> mercadorias = repositorio.findAll();
            adicionadorLink.adicionarLink(mercadorias);

            return new ResponseEntity<>(mercadorias, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/mercadoria/cadastro")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        try {
            repositorio.save(mercadoria);

            adicionadorLink.adicionarLink(mercadoria);

            return new ResponseEntity<>(mercadoria, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/mercadoria/atualizar")
    public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Mercadoria mercadoria = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(mercadoria, atualizacao);
            repositorio.save(mercadoria);

            adicionadorLink.adicionarLink(mercadoria);

            return new ResponseEntity<>(mercadoria, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/mercadoria/excluir")
    public ResponseEntity<?> deletarMercadoria(@RequestBody Mercadoria exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Mercadoria mercadoria = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(mercadoria);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
