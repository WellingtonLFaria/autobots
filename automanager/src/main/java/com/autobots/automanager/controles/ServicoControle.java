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

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkServico;
import com.autobots.automanager.modelos.atualizadores.ServicoAtualizador;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
public class ServicoControle {
    @Autowired
    private RepositorioServico repositorio;

    @Autowired
    private AdicionadorLinkServico adicionadorLink;

    @Autowired
    private ServicoAtualizador atualizador;

    @GetMapping("/servico/{id}")
    public ResponseEntity<?> obterServico(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Servico servico = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(servico);

            return new ResponseEntity<>(servico, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/servicos")
    public ResponseEntity<?> obterServicos() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Servico> servicos = repositorio.findAll();
            adicionadorLink.adicionarLink(servicos);

            return new ResponseEntity<>(servicos, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/servico/cadastro")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
        try {
            repositorio.save(servico);

            adicionadorLink.adicionarLink(servico);

            return new ResponseEntity<>(servico, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/servico/atualizar")
    public ResponseEntity<?> atualizarServico(@RequestBody Servico atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Servico servico = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(servico, atualizacao);
            repositorio.save(servico);

            adicionadorLink.adicionarLink(servico);

            return new ResponseEntity<>(servico, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/servico/excluir")
    public ResponseEntity<?> deletarServico(@RequestBody Servico exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Servico servico = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(servico);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
