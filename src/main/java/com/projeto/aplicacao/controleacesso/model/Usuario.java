package com.projeto.aplicacao.controleacesso.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_usuario", schema = "dre")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;
    @Column(name = "EMAIL_USUARIO")
    private String email;
    @Column(name = "SENHA_USUARIO")
    private String senha;
    @Column(name = "DATA_INCLUSAO")
    private LocalDateTime dataInclusao;
    @Column(name = "DATA_EXCLUSAO")
    private LocalDateTime dataExclusao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }
}
