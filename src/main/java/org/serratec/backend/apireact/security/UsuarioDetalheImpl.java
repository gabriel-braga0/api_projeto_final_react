package org.serratec.backend.apireact.security;

import org.serratec.backend.apireact.domain.Usuario;
import org.serratec.backend.apireact.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetalheImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

        if (usuario.isEmpty()) {
            throw new RuntimeException();
        }
        return usuario.get();
    }
}
