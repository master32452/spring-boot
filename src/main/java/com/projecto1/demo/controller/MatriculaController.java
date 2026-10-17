package com.projecto1.demo.controller;

import com.projecto1.demo.model.Materia;
import com.projecto1.demo.model.Alumno;
import com.projecto1.demo.model.Matricula;
import com.projecto1.demo.repository.MateriaRepository;
import com.projecto1.demo.repository.AlumnoRepository;
import com.projecto1.demo.repository.MatriculaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class MatriculaController {

    private final MatriculaRepository inscripciones;
    private final AlumnoRepository estudiantes;
    private final MateriaRepository cursos;

    public MatriculaController(MatriculaRepository inscripciones,
                                 AlumnoRepository estudiantes,
                                 MateriaRepository cursos) {
        this.inscripciones = inscripciones;
        this.estudiantes = estudiantes;
        this.cursos = cursos;
    }

    private Alumno resolverEstudiante(Alumno ref) {
        if (ref == null || ref.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar estudiante.id");
        }
        return estudiantes.findById(ref.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante " + ref.getId() + " no existe"));
    }

    private Materia resolverCurso(Materia ref) {
        if (ref == null || ref.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar curso.id");
        }
        Materia c = cursos.findById(ref.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "El curso " + ref.getId() + " no existe"));
        if (!Boolean.TRUE.equals(c.getActivo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El curso " + c.getId() + " está inactivo");
        }
        return c;
    }

    @GetMapping
    public List<Matricula> listar(@RequestParam(required = false) Long estudianteId,
                                    @RequestParam(required = false) Long cursoId) {
        if (estudianteId != null) return inscripciones.findByEstudianteId(estudianteId);
        if (cursoId != null) return inscripciones.findByCursoId(cursoId);
        return inscripciones.findAll();
    }

    @GetMapping("/{id}")
    public Matricula obtener(@PathVariable Long id) {
        return inscripciones.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Matricula crear(@Valid @RequestBody Matricula body) {
        body.setId(null);
        Alumno e = resolverEstudiante(body.getEstudiante());
        Materia c = resolverCurso(body.getCurso());
        if (inscripciones.existsByEstudianteIdAndCursoIdAndSemestreCursado(e.getId(), c.getId(), body.getSemestreCursado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El estudiante ya está inscrito en ese curso para el semestre indicado");
        }
        body.setEstudiante(e);
        body.setCurso(c);
        return inscripciones.save(body);
    }

    @PutMapping("/{id}")
    public Matricula actualizar(@PathVariable Long id, @Valid @RequestBody Matricula body) {
        Matricula i = obtener(id);
        i.setEstudiante(resolverEstudiante(body.getEstudiante()));
        i.setCurso(resolverCurso(body.getCurso()));
        i.setFechaInscripcion(body.getFechaInscripcion());
        i.setEstado(body.getEstado());
        i.setCalificacionFinal(body.getCalificacionFinal());
        i.setSemestreCursado(body.getSemestreCursado());
        return inscripciones.save(i);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        obtener(id);
        inscripciones.deleteById(id);
    }
}
