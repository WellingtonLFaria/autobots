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

import com.autobots.atvi.entidades.Documento;
import com.autobots.atvi.repositorios.RepositorioCliente;
import com.autobots.atvi.repositorios.RepositorioDocumento;

@RestController
@RequestMapping("documentos")
public class ControleDocumentos {
    @Autowired
    private RepositorioDocumento repositorioDocumento;

    @Autowired
    private RepositorioCliente repositorioCliente;

    @GetMapping
    public ResponseEntity<?> getDocumentos() {
        try {
            if (repositorioDocumento.findAll().isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioDocumento.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getDocumento(@PathVariable Long id) {
        try {
            if (repositorioDocumento.findById(id).isPresent()) return new ResponseEntity<>(repositorioDocumento.findById(id), HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postDocumento(@RequestBody Documento documento) {
        try {
            if (documento.getCliente().getId() == null || !repositorioCliente.findById(documento.getCliente().getId()).isPresent()) return new ResponseEntity<>("Cliente não informado ou não existe", HttpStatus.BAD_REQUEST);
            Documento documentoEntity = repositorioDocumento.save(documento);
            return new ResponseEntity<>(documentoEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteDocumento(@PathVariable Long id) {
        try {
            if (!repositorioDocumento.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            repositorioDocumento.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putDocumento(@PathVariable Long id, @RequestBody Documento atualizacao) {
        try {
            if (repositorioDocumento.findById(id).isEmpty())
                return new ResponseEntity<>("Não há nenhum documento com o id informado cadastrado no sistema!",
                        HttpStatus.BAD_REQUEST);
            atualizacao.setId(id);
            repositorioDocumento.save(atualizacao);
            return new ResponseEntity<>(repositorioDocumento.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
