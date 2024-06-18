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

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.adicionadoresLink.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.atualizadores.EmpresaAtualizador;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@RestController
public class EmpresaControle {
    @Autowired
    private RepositorioEmpresa repositorio;

    @Autowired
    private AdicionadorLinkEmpresa adicionadorLink;

    @Autowired
    private EmpresaAtualizador atualizador;

    @GetMapping("/empresa/{id}")
    public ResponseEntity<?> obterEmpresa(@PathVariable Long id) {
        try {
            if (!repositorio.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Empresa empresa = repositorio.findById(id).get();
            adicionadorLink.adicionarLink(empresa);

            return new ResponseEntity<>(empresa, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/empresas")
    public ResponseEntity<?> obterEmpresas() {
        try {
            if (repositorio.findAll().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            List<Empresa> empresas = repositorio.findAll();
            adicionadorLink.adicionarLink(empresas);

            return new ResponseEntity<>(empresas, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/empresa/cadastro")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
        try {
            repositorio.save(empresa);

            adicionadorLink.adicionarLink(empresa);

            return new ResponseEntity<>(empresa, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/empresa/atualizar")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa atualizacao) {
        try {
            if (!repositorio.findById(atualizacao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            Empresa empresa = repositorio.findById(atualizacao.getId()).get();
            atualizador.atualizar(empresa, atualizacao);
            repositorio.save(empresa);

            adicionadorLink.adicionarLink(empresa);

            return new ResponseEntity<>(empresa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/empresa/excluir")
    public ResponseEntity<?> deletarEmpresa(@RequestBody Empresa exclusao) {
        try {
            if (!repositorio.findById(exclusao.getId()).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Empresa empresa = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(empresa);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
