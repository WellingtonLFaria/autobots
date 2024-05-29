package com.autobots.atvi.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.atvi.entidades.Endereco;
import com.autobots.atvi.repositorios.RepositorioEndereco;

@RestController
@RequestMapping("enderecos")
public class ControleEnderecos {
    @Autowired
    private RepositorioEndereco repositorioEndereco;

    @GetMapping
    public ResponseEntity<?> getEnderecos() {
        try {
            if (repositorioEndereco.findAll().isEmpty())
                return new ResponseEntity<>("Não há nenhum endereço cadastrado no sistema!", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(repositorioEndereco.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getEndereco(@PathVariable Long id) {
        try {
            if (repositorioEndereco.findById(id).isEmpty())
                return new ResponseEntity<>("Não há nenhum endereço com o id informado cadastrado no sistema!",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(repositorioEndereco.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postEndereco(@RequestBody Endereco endereco) {
        try {
            return new ResponseEntity<>(repositorioEndereco.save(endereco), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEndereco(@PathVariable Long id) {
        try {
            if (repositorioEndereco.findById(id).isEmpty())
                return new ResponseEntity<>("Não há nenhum endereço com o id informado cadastrado no sistema!",
                        HttpStatus.BAD_REQUEST);
            repositorioEndereco.deleteById(id);
            return new ResponseEntity<>("Endereco deletado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putMapping(@PathVariable Long id, @RequestBody Endereco atualizacao) {
        try {
            if (repositorioEndereco.findById(id).isEmpty())
                return new ResponseEntity<>("Não há nenhum endereço com o id informado cadastrado no sistema!",
                        HttpStatus.BAD_REQUEST);
            atualizacao.setId(id);
            repositorioEndereco.save(atualizacao);
            return new ResponseEntity<>(repositorioEndereco.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
