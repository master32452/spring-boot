package com.projecto1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudiantes")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El apellido es obligatorio") @Size(max = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El correo es obligatorio") @Email(message = "Correo inválido") @Size(max = 100)
    private String correoElectronico;

    @Column(length = 15)
    @Size(max = 15)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    @Past(message = "La fecha de nacimiento debe ser pasada")
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    @NotNull(message = "El promedio es obligatorio") @DecimalMin("0.0") @DecimalMax("10.0")
    private Double promedioAcumulado;

    public Alumno() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public Double getPromedioAcumulado() { return promedioAcumulado; }
    public void setPromedioAcumulado(Double promedioAcumulado) { this.promedioAcumulado = promedioAcumulado; }
}