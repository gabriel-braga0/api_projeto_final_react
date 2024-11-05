package org.serratec.backend.apireact.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.serratec.backend.apireact.dto.DisciplinaDTO;

import java.util.Objects;

@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    private Integer cargaHoraria;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Disciplina() {}

    public Disciplina(DisciplinaDTO disciplinaDTO) {
        this.nome = disciplinaDTO.nome();
        this.descricao = disciplinaDTO.descricao();
        this.cargaHoraria = disciplinaDTO.cargaHoraria();
    }

    public Disciplina(Long id, String nome, String descricao, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
