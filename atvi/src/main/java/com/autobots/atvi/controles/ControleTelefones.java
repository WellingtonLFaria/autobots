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

import com.autobots.atvi.entidades.Telefone;
import com.autobots.atvi.repositorios.RepositorioTelefone;

@RestController
@RequestMapping("telefones")
public class ControleTelefones {
    @Autowired
    private RepositorioTelefone repositorioTelefone;

    @GetMapping
    public ResponseEntity<?> getTelefones() {
        try {
            if (repositorioTelefone.findAll().isEmpty())
                return new ResponseEntity<>("Não há nenhum telefone cadastrado no sistema!", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(repositorioTelefone.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTelefone(@PathVariable Long id) {
        try {
            if (repositorioTelefone.findById(id).isPresent())
                return new ResponseEntity<>(repositorioTelefone.findById(id), HttpStatus.OK);
            return new ResponseEntity<>("Não há nenhum telefone com o id informado cadastrado no sistema!",
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postTelefones(@RequestBody Telefone telefone) {
        try {
            return new ResponseEntity<>(repositorioTelefone.save(telefone), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTelefones(@PathVariable Long id) {
        try {
            if (repositorioTelefone.findById(id).isPresent()) {
                repositorioTelefone.deleteById(id);
                return new ResponseEntity<>("Telefone deletado com sucesso!", HttpStatus.OK);
            } return new ResponseEntity<>("Não há nenhum telefone com o id informado cadastrado no sistema!",
            HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putTelefone(@PathVariable Long id, @RequestBody Telefone atualizacao) {
        try {
            if (repositorioTelefone.findById(id).isEmpty())
                return new ResponseEntity<>("Não há nenhum telefone com o id informado cadastrado no sistema!",
                        HttpStatus.BAD_REQUEST);
            atualizacao.setId(id);
            repositorioTelefone.save(atualizacao);
            return new ResponseEntity<>(repositorioTelefone.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
