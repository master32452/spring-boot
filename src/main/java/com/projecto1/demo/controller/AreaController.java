package com.projecto1.demo.controller;

import com.projecto1.demo.model.Area;
import com.projecto1.demo.repository.AreaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class AreaController {

    private final AreaRepository departamentos;

    public AreaController(AreaRepository departamentos) {
        this.departamentos = departamentos;
    }

    @GetMapping
    public List<Area> listar() {
        return departamentos.findAll();
    }

    @GetMapping("/{id}")
    public Area obtener(@PathVariable Long id) {
        return departamentos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Area crear(@Valid @RequestBody Area body) {
        body.setId(null);
        return departamentos.save(body);
    }

    @PutMapping("/{id}")
    public Area actualizar(@PathVariable Long id, @Valid @RequestBody Area body) {
        Area d = obtener(id);
        d.setNombre(body.getNombre());
        d.setCodigo(body.getCodigo());
        d.setDescripcion(body.getDescripcion());
        d.setPresupuesto(body.getPresupuesto());
        d.setActivo(body.getActivo());
        return departamentos.save(d);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        obtener(id);
        departamentos.deleteById(id);
    }
}
