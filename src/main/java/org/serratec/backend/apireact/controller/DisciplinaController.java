package org.serratec.backend.apireact.controller;

import org.serratec.backend.apireact.domain.Disciplina;
import org.serratec.backend.apireact.dto.DisciplinaDTO;
import org.serratec.backend.apireact.service.DisciplinaService;
import org.serratec.backend.apireact.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Disciplina> save(@RequestBody DisciplinaDTO disciplinaDTO) {
        Disciplina d = disciplinaService.addDisciplina(disciplinaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(d.getId())
                .toUri();
        return ResponseEntity.created(uri).body(d);
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> getAll() {
        return ResponseEntity.ok(disciplinaService.getAllDisciplinas());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Disciplina>> findAll() {
        return ResponseEntity.ok(disciplinaService.getDisciplinas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> findById(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.getDisciplinaById(id);
        if (disciplina == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(disciplina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disciplinaService.deleteDisciplina(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> update(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        Optional<Disciplina> disciplinaUpdate = disciplinaService.alterar(id, disciplinaDTO);
        return disciplinaUpdate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
