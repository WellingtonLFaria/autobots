package com.autobots.atvi.controles;

import com.autobots.atvi.atualizadores.EnderecoAtualizador;
import com.autobots.atvi.entidades.Cliente;
import com.autobots.atvi.entidades.Endereco;
import com.autobots.atvi.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @GetMapping("/cliente/{id}")
    public Optional<Cliente> pegarCliente(@PathVariable Long id) {
        return repositorio.findById(id);
    }

    @GetMapping("/clientes")
    public List<Cliente> pegarClientes() {
        return repositorio.findAll();
    }

    @PostMapping("/cadastrar/cliente")
    public void cadastrarCliente(@RequestBody Cliente cliente) {
        repositorio.save(cliente);
    }

    @PostMapping("/cadastrar/clientes")
    public void cadastrarClientes(@RequestBody List<Cliente> clientes) {
        repositorio.saveAll(clientes);
    }

    @DeleteMapping("/deletar/cliente/{id}")
    public void deletarCliente(@PathVariable Long id) {
        repositorio.deleteById(id);
    }

    @PutMapping("/atualizar/cliente/{id}/endereco")
    public void atualizarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        Cliente cliente = repositorio.findById(id).get();
        EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
        enderecoAtualizador.atualizar(cliente.getEndereco(), endereco);
        String msg = String.format("{%s}, {%s}, {%s}, {%s}", endereco.getEstado(), endereco.getCidade(), endereco.getBairro(), endereco.getRua());
        System.out.println(msg);
    }

}
