package org.serratec.backend.apireact.dto;

import org.serratec.backend.apireact.domain.Disciplina;

public record DisciplinaDTO(String nome,
                            String descricao,
                            Integer cargaHoraria) {

    public DisciplinaDTO (Disciplina disciplina) {
        this(disciplina.getNome(), disciplina.getDescricao(), disciplina.getCargaHoraria());
    }
}
