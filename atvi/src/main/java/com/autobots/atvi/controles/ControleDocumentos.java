package com.autobots.atvi.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.atvi.atualizadores.DocumentoAtualizador;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.entidades.Documento;
import com.autobots.atvi.repositorios.RepositorioCliente;
import com.autobots.atvi.repositorios.RepositorioDocumento;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("documentos")
public class ControleDocumentos {
    @Autowired
    private RepositorioDocumento repositorioDocumento;

    @Autowired
    private RepositorioCliente repositorioCliente;

    private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();

    @GetMapping
    public ResponseEntity<?> getDocumentos() {
        try {
            if (repositorioDocumento.findAll().isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioDocumento.findAll(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getDocumento(@PathVariable Long id) {
        try {
            if (!repositorioDocumento.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(repositorioDocumento.findById(id).get(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteDocumento(@PathVariable Long id) {
        try {
            if (!repositorioDocumento.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            repositorioDocumento.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putDocumento(@PathVariable Long id, @RequestBody Documento documento) {
        try {
            if (!repositorioDocumento.findById(id).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Documento documentoBanco = repositorioDocumento.findById(id).get();
            documentoBanco = documentoAtualizador.atualizar(documentoBanco, documento);
            return new ResponseEntity<>(documentoBanco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{clienteID}")
    public ResponseEntity<?> postDocumento(@PathVariable Long clienteID, @RequestBody Documento documento) {
        try {
            if (!repositorioCliente.findById(clienteID).isPresent()) return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
            if (repositorioDocumento.findByNumero(documento.getNumero()).isPresent()) return new ResponseEntity<>("Documento já cadastrado", HttpStatus.CONFLICT);
            Cliente cliente = repositorioCliente.findById(clienteID).get();
            cliente.getDocumentos().add(documento);
            repositorioCliente.save(cliente);
            return new ResponseEntity<>(documento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
