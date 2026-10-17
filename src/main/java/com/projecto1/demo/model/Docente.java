package com.projecto1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "profesores")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 60)
    private String nombre;

    @Column(nullable = false, length = 60)
    @NotBlank(message = "El apellido es obligatorio") @Size(max = 60)
    private String apellido;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El email es obligatorio") @Email(message = "Email inválido") @Size(max = 100)
    private String email;

    @Column(length = 15)
    @Size(max = 15)
    private String telefono;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departamento_id", nullable = false)
    @NotNull(message = "El departamento es obligatorio")
    private Area departamento;

    public Docente() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public Area getDepartamento() { return departamento; }
    public void setDepartamento(Area departamento) { this.departamento = departamento; }
}