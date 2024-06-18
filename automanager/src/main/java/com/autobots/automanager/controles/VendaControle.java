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

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkVenda;
import com.autobots.automanager.modelos.atualizadores.VendaAtualizador;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
public class VendaControle {
    @Autowired
    private RepositorioVenda repositorio;

    @Autowired
    private AdicionadorLinkVenda adicionadorLink;

    @Autowired
    private VendaAtualizador atualizador;

    @GetMapping("/venda/{id}")
    public ResponseEntity<?> obterVenda(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Venda venda = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(venda);

            return new ResponseEntity<>(venda, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vendas")
    public ResponseEntity<?> obterVendas() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Venda> vendas = repositorio.findAll();
            adicionadorLink.adicionarLink(vendas);

            return new ResponseEntity<>(vendas, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/venda/cadastro")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        try {
            repositorio.save(venda);

            adicionadorLink.adicionarLink(venda);

            return new ResponseEntity<>(venda, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/venda/atualizar")
    public ResponseEntity<?> atualizarVenda(@RequestBody Venda atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Venda venda = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(venda, atualizacao);
            repositorio.save(venda);

            adicionadorLink.adicionarLink(venda);

            return new ResponseEntity<>(venda, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/venda/excluir")
    public ResponseEntity<?> deletarVenda(@RequestBody Venda exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Venda venda = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(venda);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
