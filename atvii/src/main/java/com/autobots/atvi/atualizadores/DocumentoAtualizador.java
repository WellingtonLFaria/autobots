package com.autobots.atvi.atualizadores;

import java.util.List;

import com.autobots.atvi.entidades.Documento;

public class DocumentoAtualizador {
    public Documento atualizar(Documento documento, Documento atualizacao) {
        if (atualizacao.getTipo() != null && atualizacao.getTipo() != "") documento.setTipo(atualizacao.getTipo());
        if (atualizacao.getNumero() != null && atualizacao.getNumero() != "") documento.setNumero(atualizacao.getNumero());
        return documento;
    }

    public List<Documento> atualizar(List<Documento> documentos, List<Documento> atualizacoes) {
        for (int i = 0; i < documentos.size(); i++) {
            documentos.set(i, atualizar(documentos.get(i), atualizacoes.get(i)));
        }
        return documentos;
    }
}
