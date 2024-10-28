package com.olimpiodev.tec.carteira.enumeracao;

public enum LancamentoCategoria {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    private String descricao;

    LancamentoCategoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
