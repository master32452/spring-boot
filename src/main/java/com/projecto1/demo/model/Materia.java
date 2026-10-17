package com.projecto1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "cursos")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "El código del curso es obligatorio") @Size(max = 20)
    private String codigoCurso;

    @Column(columnDefinition = "TEXT")
    private String temario;

    @Column(nullable = false)
    @NotNull(message = "Los créditos son obligatorios") @Min(value = 1, message = "Mínimo 1 crédito") @Max(value = 30, message = "Máximo 30 créditos")
    private Integer creditos;

    @Column(nullable = false)
    @NotNull(message = "activo es obligatorio")
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departamento_id", nullable = false)
    @NotNull(message = "El departamento es obligatorio")
    private Area departamento;

    public Materia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }
    public String getTemario() { return temario; }
    public void setTemario(String temario) { this.temario = temario; }
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public Area getDepartamento() { return departamento; }
    public void setDepartamento(Area departamento) { this.departamento = departamento; }
}