package com.olimpiodev.tec.carteira.model;

public class Usuario {
    private String nome;
    private String sobrenome;
    private String senha;
    private String telefone;

    public Usuario(String nome, String sobrenome, String senha, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.senha = senha;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getSenha() {
        return senha;
    }

    public String getTelefone() {
        return telefone;
    }
}
