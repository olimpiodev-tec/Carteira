package com.example.carteira.model;

import com.example.carteira.enumeracao.LancamentoCategoria;

public class Lancamento {
    private int id;
    private String decricao;
    private LancamentoCategoria lancamentoCategoria;
    private Double valor;

    public Lancamento(int id, String decricao, LancamentoCategoria lancamentoCategoria, Double valor) {
        this.id = id;
        this.decricao = decricao;
        this.lancamentoCategoria = lancamentoCategoria;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public LancamentoCategoria getLancamentoCategoria() {
        return lancamentoCategoria;
    }

    public void setLancamentoCategoria(LancamentoCategoria lancamentoCategoria) {
        this.lancamentoCategoria = lancamentoCategoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public boolean isLancamentoDespesa() {
        if (this.lancamentoCategoria == LancamentoCategoria.SAIDA) {
            return true;
        } else {
            return false;
        }
    }
}
