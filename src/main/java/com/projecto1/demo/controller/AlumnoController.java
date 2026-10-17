package com.projecto1.demo.controller;

import com.projecto1.demo.model.Alumno;
import com.projecto1.demo.repository.AlumnoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class AlumnoController {

    private final AlumnoRepository estudiantes;

    public AlumnoController(AlumnoRepository estudiantes) {
        this.estudiantes = estudiantes;
    }

    @GetMapping
    public List<Alumno> listar() {
        return estudiantes.findAll();
    }

    @GetMapping("/{id}")
    public Alumno obtener(@PathVariable Long id) {
        return estudiantes.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alumno crear(@Valid @RequestBody Alumno body) {
        body.setId(null);
        return estudiantes.save(body);
    }

    @PutMapping("/{id}")
    public Alumno actualizar(@PathVariable Long id, @Valid @RequestBody Alumno body) {
        Alumno e = obtener(id);
        e.setNombre(body.getNombre());
        e.setApellido(body.getApellido());
        e.setCorreoElectronico(body.getCorreoElectronico());
        e.setTelefono(body.getTelefono());
        e.setFechaNacimiento(body.getFechaNacimiento());
        e.setPromedioAcumulado(body.getPromedioAcumulado());
        return estudiantes.save(e);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        obtener(id);
        estudiantes.deleteById(id);
    }
}
