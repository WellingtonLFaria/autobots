package com.autobots.automanager.controles;

import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.atualizadores.DocumentoAtualizador;
import com.autobots.automanager.repositorios.RepositorioDocumento;

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


@RestController
public class DocumentoControle {
    @Autowired
    private RepositorioDocumento repositorio;

    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @Autowired
    private DocumentoAtualizador atualizador;

    @GetMapping("/documento/{id}")
    public ResponseEntity<?> obterDocumento(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Documento documento = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(documento);

            return new ResponseEntity<>(documento, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/documentos")
    public ResponseEntity<?> obterDocumentos() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Documento> documentos = repositorio.findAll();
            adicionadorLink.adicionarLink(documentos);

            return new ResponseEntity<>(documentos, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/documento/cadastro")
    public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento) {
        try {
            repositorio.save(documento);

            adicionadorLink.adicionarLink(documento);

            return new ResponseEntity<>(documento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/documento/atualizar")
    public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Documento documento = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(documento, atualizacao);
            repositorio.save(documento);

            adicionadorLink.adicionarLink(documento);

            return new ResponseEntity<>(documento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/documento/excluir")
    public ResponseEntity<?> deletarDocumento(@RequestBody Documento exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Documento documento = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(documento);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
