package org.serratec.backend.apireact.service;

import jakarta.servlet.http.HttpServletRequest;
import org.serratec.backend.apireact.domain.Disciplina;
import org.serratec.backend.apireact.domain.Usuario;
import org.serratec.backend.apireact.dto.DisciplinaDTO;
import org.serratec.backend.apireact.dto.UsuarioDTO;
import org.serratec.backend.apireact.repository.DisciplinaRepository;
import org.serratec.backend.apireact.repository.UsuarioRepository;
import org.serratec.backend.apireact.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;


    public Disciplina addDisciplina(DisciplinaDTO disciplinaDTO) {
        Optional<Usuario> usuario = userToken();

        Disciplina disciplina = new Disciplina(disciplinaDTO);
        disciplina.setUsuario(usuario.get());
        disciplinaRepository.save(disciplina);
        usuario.get().getDisciplinas().add(disciplina);
//        usuarioRepository.save(usuario.get());
        return disciplina;
    }

    public List<Disciplina> getAllDisciplinas() {
        Usuario usuario = userToken().get();
        return usuario.getDisciplinas();
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinaRepository.findAll();
    }

    public Optional<Usuario> userToken() {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String email = jwtUtil.getUserName(token);
        return usuarioRepository.findByEmail(email);
    }

    public Disciplina getDisciplinaById(Long id) {
        Optional<Usuario> usuario = userToken();

        Optional<Disciplina> disc = disciplinaRepository.findById(id);
        if (disc.isPresent() && usuario.isPresent()
                && usuario.get().getDisciplinas().contains(disc.get())) {
            return disc.get();
        }
        return null;
    }

    @Transactional
    public void deleteDisciplina(Long id) {
        Usuario usuario = userToken().get();
        Optional<Disciplina> disc = disciplinaRepository.findById(id);
        Disciplina disciplina = new Disciplina();
        if (disc.isPresent()) {
            disciplina = disc.get();
        }

        if (usuario.getDisciplinas().contains(disciplina)) {
            System.out.println("Entrou aqui");
            usuario.getDisciplinas().remove(disciplina);
            disciplinaRepository.delete(disciplina);
        }
    }

    public Optional<Disciplina> alterar(Long id, DisciplinaDTO disciplinaDTO) {
        Optional<Usuario> usuario = userToken();
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(id);
        if (disciplinaOptional.isPresent() && usuario.isPresent() &&
                usuario.get().getDisciplinas().contains(disciplinaOptional.get())) {
            Disciplina disciplina = new Disciplina(disciplinaDTO);
            disciplina.setUsuario(usuario.get());
            disciplina.setId(id);
            disciplinaRepository.save(disciplina);
            return Optional.of(disciplina);
        }
        return Optional.empty();
    }

}
