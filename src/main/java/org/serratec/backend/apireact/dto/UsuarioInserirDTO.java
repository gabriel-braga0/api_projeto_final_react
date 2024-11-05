package org.serratec.backend.apireact.dto;

public record UsuarioInserirDTO(String email,
                                String senha,
                                String confirmaSenha) {
}
