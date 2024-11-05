package org.serratec.backend.apireact.dto;

import org.serratec.backend.apireact.domain.Disciplina;
import org.serratec.backend.apireact.domain.Usuario;

import java.util.List;
import java.util.Set;

public record UsuarioDTO(Long id,
                         String email,
                         List<Disciplina> disciplinas) {

    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail(), usuario.getDisciplinas());
    }
}
