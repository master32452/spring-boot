package com.projecto1.demo.controller;

import com.projecto1.demo.model.Area;
import com.projecto1.demo.model.Docente;
import com.projecto1.demo.repository.AreaRepository;
import com.projecto1.demo.repository.DocenteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class DocenteController {

    private final DocenteRepository profesores;
    private final AreaRepository departamentos;

    public DocenteController(DocenteRepository profesores, AreaRepository departamentos) {
        this.profesores = profesores;
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
    public List<Docente> listar(@RequestParam(required = false) Long departamentoId) {
        return departamentoId == null ? profesores.findAll() : profesores.findByDepartamentoId(departamentoId);
    }

    @GetMapping("/{id}")
    public Docente obtener(@PathVariable Long id) {
        return profesores.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profesor no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Docente crear(@Valid @RequestBody Docente body) {
        body.setId(null);
        body.setDepartamento(resolverDepartamento(body.getDepartamento()));
        return profesores.save(body);
    }

    @PutMapping("/{id}")
    public Docente actualizar(@PathVariable Long id, @Valid @RequestBody Docente body) {
        Docente p = obtener(id);
        p.setDepartamento(resolverDepartamento(body.getDepartamento()));
        p.setNombre(body.getNombre());
        p.setApellido(body.getApellido());
        p.setEmail(body.getEmail());
        p.setTelefono(body.getTelefono());
        p.setFechaContratacion(body.getFechaContratacion());
        return profesores.save(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        obtener(id);
        profesores.deleteById(id);
    }
}
