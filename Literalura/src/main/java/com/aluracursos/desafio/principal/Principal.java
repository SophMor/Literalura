package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.*;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;
import com.aluracursos.desafio.service.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convertir = new ConvierteDatos();
    private Scanner scanner = new Scanner(System.in);
    private LibroRepository libroRepository;

    @Autowired
    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }
    public Principal(){}

    public void muestraElMenu() {
        int opcion = 1;
        while (opcion != 0) {
            System.out.println("1. Buscar por nombre \n" +
                    "2. Consultar Libros\n"+
                    "3. Listar autores registrados\n" +
                    "4. Consultar Por Fecha autor\n" +
                    "5. Consultar por Idioma"
            );
            System.out.println("0. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();
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
            System.out.println("Ingrese el nombre del libro que desea buscar");
            var tituloLibro = scanner.nextLine();
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            var datosBusqueda = convertir.obtenerDatos(json, Datos.class);
            Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .findFirst();
            if (libroBuscado.isPresent()) {
                System.out.println("Libro Encontrado ");
                return (libroBuscado.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el libro");
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
          System.out.println("error al guardar libro");
      }
      }
    public void consultarLibro() {
        try{
            List<Libro> libros = libroRepository.buscarLibro();

            for (Libro libro : libros) {
                System.out.println("LIBRO------\n");
                System.out.println("Título: " + libro.getTitulo());
                for (Autor autor : libro.getAutor()) {
                    System.out.println("Autor: " + autor.getNombre());
                }
                System.out.println("Idioma: " + libro.getIdiomas());
                System.out.println("Número descargas: "+libro.getNumeroDeDescargas());
                System.out.println("---------------------------------");
            }} catch (RuntimeException e) {
            System.out.println("Error con los datos");
        }
        }

    public void consultarAutores() {
        try{
        List<Autor> autores = libroRepository.buscarAutor();

        for (Autor autor : autores) {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha Nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fecha Fallecimiento: " + autor.getFechaDeFallecimiento());

                System.out.println("Libros:"+autor.getLibro().getTitulo());

            }
        }
        catch (RuntimeException e) {
            System.out.println("Error con los datos");
        }}

        public void consultarFechaAutor() {
            try{
            System.out.println("Ingrese el año de los autores que desea buscar");
            var anio = scanner.nextFloat();
            List<Autor> autorList = libroRepository.buscarAutorEpoca(anio);

            for (Autor autor : autorList) {
                System.out.println("Autor: " + autor.getNombre()
                        + " Fecha Nacimiento " + autor.getFechaDeNacimiento() +
                        " Fecha Fallecimiento " + autor.getFechaDeFallecimiento()
                        + " Libros: " + autor.getLibro().getTitulo());
            }
        }catch(Exception e){
                System.out.println("Error ");
            }


        }

    public void consultarPorIdioma() {
        System.out.println("Ingrese el idioma que desea buscar (EN, PT, FR, IT, ES):");
        var idiomaStr = scanner.next().toUpperCase();

        try {
            Idioma idioma = Idioma.fromString(idiomaStr);
            List<Libro> librosPorIdioma = libroRepository.findByIdiomas(idioma);

            if (!librosPorIdioma.isEmpty()) {
                for (Libro libro : librosPorIdioma) {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autor: "+libro.getAutor());
                    System.out.println("Idioma: "+libro.getIdiomas());
                    System.out.println("Número descargas "+libro.getNumeroDeDescargas());
                }
            } else {
                System.out.println("No se encontraron libros en el idioma.");
             }
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no válido: " + idiomaStr);
        }
    }

}


