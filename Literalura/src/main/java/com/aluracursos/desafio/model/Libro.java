package com.aluracursos.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name ="libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idiomas;
    private Double numeroDeDescargas;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private  List<Autor> autor;

    public Libro(DatosLibros datosLibros) {
        try{
            this.titulo = datosLibros.titulo();
            this.idiomas = Idioma.fromString(datosLibros.idiomas().get(0));
            this.numeroDeDescargas = OptionalDouble.of(datosLibros.numeroDeDescargas()).orElse(0.0);
        }
        catch (Exception e){
            this.titulo = "n/a";
            this.idiomas = null;
        }

    }

    public Libro() {

    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autores) {
        this.autor = autores;
        if (autores != null) {
            for (Autor autor : autores) {
                autor.setLibro(this);
            }
        }
    }

}
