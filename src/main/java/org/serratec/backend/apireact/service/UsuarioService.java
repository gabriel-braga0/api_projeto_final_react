package org.serratec.backend.apireact.service;

import org.serratec.backend.apireact.domain.Disciplina;
import org.serratec.backend.apireact.domain.Usuario;
import org.serratec.backend.apireact.dto.DisciplinaDTO;
import org.serratec.backend.apireact.dto.UsuarioDTO;
import org.serratec.backend.apireact.dto.UsuarioInserirDTO;
import org.serratec.backend.apireact.exception.EmailException;
import org.serratec.backend.apireact.exception.SenhaException;
import org.serratec.backend.apireact.repository.DisciplinaRepository;
import org.serratec.backend.apireact.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UsuarioDTO findEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.map(UsuarioDTO::new).orElse(null);
    }

    private List<Disciplina> criarIniciais() {
        List<Disciplina> disciplinas = new ArrayList<>();
        DisciplinaDTO disciplinaDTO1 = new DisciplinaDTO
                ("POO", "Programação orientada a objetos", 96);

        DisciplinaDTO disciplinaDTO2 = new DisciplinaDTO
                ("API", "Desenvolvimento de API Restful", 102);

        DisciplinaDTO disciplinaDTO3 = new DisciplinaDTO
                ("WEB", "Desenvolvimento WEB com React", 54);
        disciplinas.add(new Disciplina(disciplinaDTO1));
        disciplinas.add(new Disciplina(disciplinaDTO2));
        disciplinas.add(new Disciplina(disciplinaDTO3));
        return disciplinas;
    }

    public UsuarioDTO inserir(UsuarioInserirDTO usuarioInserirDTO) {
        if (!usuarioInserirDTO.senha().equals(usuarioInserirDTO.confirmaSenha())) {
            throw new SenhaException("Senhas não coincidem");
        }
        if (usuarioRepository.findByEmail(usuarioInserirDTO.email()).isPresent()) {
            throw new EmailException("Email já existente");
        }

//        Disciplina disciplina1 = new Disciplina();
//        disciplina1.setNome("API");
//        disciplina1.setDescricao("Desenvolvimento de API Restful");
//        disciplina1.setCargaHoraria(102);

        List<Disciplina> disciplinas = criarIniciais();

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioInserirDTO.email());
        usuario.setSenha(encoder.encode(usuarioInserirDTO.senha()));
        usuario = usuarioRepository.save(usuario);
        for (Disciplina disciplina : disciplinas) {
            disciplina.setUsuario(usuario);
            disciplinaRepository.save(disciplina);
            usuario.getDisciplinas().add(disciplina);
        }
//        disciplina1.setUsuario(usuario);
//        disciplinaRepository.save(disciplina1);
//        usuario.getDisciplinas().add(disciplina1);
        System.out.println(usuario.getDisciplinas().size());

        return new UsuarioDTO(usuario);
    }

    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(new UsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    public UsuarioDTO buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioDTO::new).orElse(null);
    }
}
