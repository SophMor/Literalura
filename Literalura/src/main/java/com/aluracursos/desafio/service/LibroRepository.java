package com.aluracursos.desafio.service;

import com.aluracursos.desafio.model.Autor;
import com.aluracursos.desafio.model.Idioma;
import com.aluracursos.desafio.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l JOIN FETCH l.autor a")
    List<Libro> buscarLibro();

    @Query("SELECT a From Autor a JOIN FETCH a.libro l ")
    List<Autor> buscarAutor();

    @Query("select a from Autor a JOIN FETCH a.libro l where (a.fechaDeNacimiento <= :fecha and (a.fechaDeFallecimiento >= :fecha or a.fechaDeFallecimiento is null))")
    List<Autor> buscarAutorEpoca(Float fecha);

    List<Libro> findByIdiomas(Idioma idioma);

}
