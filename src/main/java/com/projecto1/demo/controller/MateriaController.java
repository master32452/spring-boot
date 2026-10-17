package com.projecto1.demo.controller;

import com.projecto1.demo.model.Materia;
import com.projecto1.demo.model.Area;
import com.projecto1.demo.repository.MateriaRepository;
import com.projecto1.demo.repository.AreaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class MateriaController {

    private final MateriaRepository cursos;
    private final AreaRepository departamentos;

    public MateriaController(MateriaRepository cursos, AreaRepository departamentos) {
        this.cursos = cursos;
        this.departamentos = departamentos;
    }

    private Area resolverDepartamento(Area ref) {
        if (ref == null || ref.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar departamento.id");
        }
        return departamentos.findById(ref.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "El departamento " + ref.getId() + " no existe"));
    }

    @GetMapping
    public List<Materia> listar(@RequestParam(required = false) Long departamentoId) {
        return departamentoId == null ? cursos.findAll() : cursos.findByDepartamentoId(departamentoId);
    }

    @GetMapping("/{id}")
    public Materia obtener(@PathVariable Long id) {
        return cursos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Materia crear(@Valid @RequestBody Materia body) {
        body.setId(null);
        body.setDepartamento(resolverDepartamento(body.getDepartamento()));
        return cursos.save(body);
    }

    @PutMapping("/{id}")
    public Materia actualizar(@PathVariable Long id, @Valid @RequestBody Materia body) {
        Materia c = obtener(id);
        c.setDepartamento(resolverDepartamento(body.getDepartamento()));
        c.setNombre(body.getNombre());
        c.setCodigoCurso(body.getCodigoCurso());
        c.setTemario(body.getTemario());
        c.setCreditos(body.getCreditos());
        c.setActivo(body.getActivo());
        return cursos.save(c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        obtener(id);
        cursos.deleteById(id);
    }
}
