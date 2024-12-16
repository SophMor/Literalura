package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.*;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;
import com.aluracursos.desafio.service.LibroRepository;
import com.aluracursos.desafio.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convertir = new ConvierteDatos();
    private View view = new View();
    private LibroRepository libroRepository;

    @Autowired
    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }
    public Principal(){}

    public void muestraElMenu() {
        int opcion = 1;
        while (opcion != 0) {
           view.showMenu();

            opcion = view.readInt();
            view.readString();
            switch (opcion) {
                case 1: {
                    buscarLibroBd();
                    break;
                }
                case 2: {
                    consultarLibro();
                    break;
                }
                case 3 :{
                    consultarAutores();
                    break;
                }
                case 4 :{
                    consultarFechaAutor();
                    break;
                }
                case 5:{
                    consultarPorIdioma();
                    break;
                }
                case 6:{
                    consultarLibroJSON();
                    break;
                }

                case 0:{
                    break;
                }
            }

        }
    }



    private DatosLibros conectarPorLibro() {
/*var json = consumoAPI.obtenerDatos(URL_BASE);

        var datos = convertir.obtenerDatos(json, Datos.class);
        System.out.println(datos);*/
        try {
            view.print("Ingrese el nombre del libro que desea buscar");
            var tituloLibro = view.readString();
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            var datosBusqueda = convertir.obtenerDatos(json, Datos.class);
            Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .findFirst();
            if (libroBuscado.isPresent()) {
                view.print("Libro Encontrado ");
                return (libroBuscado.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            view.print("Error al obtener el libro");
            return null;
        }
    }

    public void buscarLibroBd() {
      try {
          DatosLibros libroBuscado = conectarPorLibro();
          Libro libro = new Libro(libroBuscado);
          List<Autor> autores = libroBuscado.autor().stream()
                  .map(nombreAutor -> new Autor(nombreAutor.nombre()
                          , nombreAutor.fechaDeNacimiento(), nombreAutor.fechaDeFallecimiento(), libro))
                  .collect(Collectors.toList());
          libro.setAutor(autores);

          libroRepository.save(libro);
          System.out.println(libroBuscado);


      } catch (Exception e) {
          view.print

("error al guardar libro");
      }
      }
    public void consultarLibro() {
        try{
            List<Libro> libros = libroRepository.buscarLibro();

            for (Libro libro : libros) {
                view.print("LIBRO------\n");
                view.print("Título: " + libro.getTitulo());
                for (Autor autor : libro.getAutor()) {
                    view.print("Autor: " + autor.getNombre());
                }
                view.print ("Idioma: " + libro.getIdiomas());
                view.print("Número descargas: "+libro.getNumeroDeDescargas());
                view.print("---------------------------------");
            }} catch (RuntimeException e) {
            view.print("Error con los datos");
        }
        }

    public void consultarAutores() {
        try{
        List<Autor> autores = libroRepository.buscarAutor();

        for (Autor autor : autores) {
            view.print("Autor: " + autor.getNombre());
            view.print("Fecha Nacimiento: " + autor.getFechaDeNacimiento());
            view.print("Fecha Fallecimiento: " + autor.getFechaDeFallecimiento());
            view.print("Libros:"+autor.getLibro().getTitulo());

            }
        }
        catch (RuntimeException e) {
            view.print

("Error con los datos");
        }}

        public void consultarFechaAutor() {
            try{
            view.print

("Ingrese el año de los autores que desea buscar");
            var anio = view.readFloat();
            List<Autor> autorList = libroRepository.buscarAutorEpoca(anio);

            for (Autor autor : autorList) {
                view.print

("Autor: " + autor.getNombre()
                        + " Fecha Nacimiento " + autor.getFechaDeNacimiento() +
                        " Fecha Fallecimiento " + autor.getFechaDeFallecimiento()
                        + " Libros: " + autor.getLibro().getTitulo());
            }
        }catch(Exception e){
                view.print

("Error ");
            }


        }

    public void consultarPorIdioma() {
        view.print("Ingrese el idioma que desea buscar (EN, PT, FR, IT, ES):");
        var idiomaStr = view.read().toUpperCase();

        try {
            Idioma idioma = Idioma.fromString(idiomaStr);
            List<Libro> librosPorIdioma = libroRepository.findByIdiomas(idioma);

            if (!librosPorIdioma.isEmpty()) {
                for (Libro libro : librosPorIdioma) {
                   view.print("Título: " + libro.getTitulo());
                    view.print("Autor: "+libro.getAutor());
                    view.print("Idioma: "+libro.getIdiomas());
                    view.print("Número descargas "+libro.getNumeroDeDescargas());
                }
            } else {
                view.print("No se encontraron libros en el idioma.");
             }
        } catch (IllegalArgumentException e) {
            view.print("Idioma no válido: " + idiomaStr);
        }
    }

    public void consultarLibroJSON() {
        try{
            DatosLibros libro = conectarPorLibro();

            if (libro != null) {
                String libroJson = convertir.convertirLibroBuscadoAJson(libro);
                view.print("Libro: " + libroJson);
                convertir.guardarJsonEnArchivo(libroJson,libro.titulo()+".json");
            }
            else{
                view.print("No se encontro libro");
            }
            }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


