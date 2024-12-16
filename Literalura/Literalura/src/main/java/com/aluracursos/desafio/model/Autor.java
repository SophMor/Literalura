package com.aluracursos.desafio.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Float fechaDeNacimiento;

    private Float fechaDeFallecimiento;

    @ManyToOne
    @JoinColumn(name="libroId")
    private Libro libro;

    // Constructor que recibe el nombre del autor y el libro asociado
    /*public Autor(String nombre, Libro libro) {
        this.nombre = nombre;
        this.libro = libro;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    }*/

    public Autor(String nombre, String fechaDeNacimiento, String fechaDeFallecimiento,Libro libro) {
       try{
        this.nombre = nombre;
        this.libro = libro;
        this.fechaDeNacimiento = Float.valueOf(fechaDeNacimiento);
        this.fechaDeFallecimiento = Float.valueOf(fechaDeFallecimiento);

        }
        catch (Exception e) {
            this.fechaDeNacimiento = null;
            this.fechaDeFallecimiento = null;
            System.out.println("Error al procesar las fechas: " + e.getMessage());
        }
    }

    public Autor() {

    }


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Float getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Float fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public Float getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Float fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
}
