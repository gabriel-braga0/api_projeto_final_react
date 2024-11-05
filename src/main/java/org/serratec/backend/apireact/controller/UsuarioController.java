package org.serratec.backend.apireact.controller;

import org.serratec.backend.apireact.domain.Usuario;
import org.serratec.backend.apireact.dto.UsuarioDTO;
import org.serratec.backend.apireact.dto.UsuarioInserirDTO;
import org.serratec.backend.apireact.repository.UsuarioRepository;
import org.serratec.backend.apireact.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<UsuarioDTO> post(@RequestBody UsuarioInserirDTO usuario) {
        UsuarioDTO usuarioDTO = usuarioService.inserir(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/email")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam("email") String email) {
        if(usuarioService.findEmail(email) != null) {
            return ResponseEntity.ok(usuarioService.findEmail(email));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.buscarPorId(id);
        if (usuarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDTO);
    }

}
