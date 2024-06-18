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

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelos.atualizadores.VeiculoAtualizador;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@RestController
public class VeiculoControle {
    @Autowired
    private RepositorioVeiculo repositorio;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLink;

    @Autowired
    private VeiculoAtualizador atualizador;

    @GetMapping("/veiculo/{id}")
    public ResponseEntity<?> obterVeiculo(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Veiculo veiculo = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(veiculo);

            return new ResponseEntity<>(veiculo, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/veiculos")
    public ResponseEntity<?> obterVeiculos() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Veiculo> veiculos = repositorio.findAll();
            adicionadorLink.adicionarLink(veiculos);

            return new ResponseEntity<>(veiculos, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/veiculo/cadastro")
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        try {
            repositorio.save(veiculo);

            adicionadorLink.adicionarLink(veiculo);

            return new ResponseEntity<>(veiculo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/veiculo/atualizar")
    public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Veiculo veiculo = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(veiculo, atualizacao);
            repositorio.save(veiculo);

            adicionadorLink.adicionarLink(veiculo);

            return new ResponseEntity<>(veiculo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/veiculo/excluir")
    public ResponseEntity<?> deletarVeiculo(@RequestBody Veiculo exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Veiculo veiculo = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(veiculo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
