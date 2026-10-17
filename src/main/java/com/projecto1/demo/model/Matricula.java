package com.projecto1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones", uniqueConstraints = @UniqueConstraint(
        name = "uk_inscripcion_est_curso_sem",
        columnNames = {"estudiante_id", "curso_id", "semestre_cursado"}))
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inscripcion", nullable = false)
    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate fechaInscripcion;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|RETIRADO|APROBADO|REPROBADO", message = "Estado válido: ACTIVO, RETIRADO, APROBADO o REPROBADO")
    private String estado;

    @Column(name = "calificacion_final")
    @DecimalMin("0.0") @DecimalMax("10.0")
    private Double calificacionFinal;

    @Column(name = "semestre_cursado", nullable = false, length = 10)
    @NotBlank(message = "El semestre es obligatorio") @Size(max = 10)
    private String semestreCursado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    @NotNull(message = "El estudiante es obligatorio")
    private Alumno estudiante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    @NotNull(message = "El curso es obligatorio")
    private Materia curso;

    public Matricula() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Double getCalificacionFinal() { return calificacionFinal; }
    public void setCalificacionFinal(Double calificacionFinal) { this.calificacionFinal = calificacionFinal; }
    public String getSemestreCursado() { return semestreCursado; }
    public void setSemestreCursado(String semestreCursado) { this.semestreCursado = semestreCursado; }
    public Alumno getEstudiante() { return estudiante; }
    public void setEstudiante(Alumno estudiante) { this.estudiante = estudiante; }
    public Materia getCurso() { return curso; }
    public void setCurso(Materia curso) { this.curso = curso; }
}