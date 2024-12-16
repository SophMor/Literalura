package com.aluracursos.desafio.service;

import com.aluracursos.desafio.model.DatosLibros;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConvierteDatos implements IConvierteDatos {
    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return mapper.readValue(json.toString(), clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertirLibroBuscadoAJson(DatosLibros libroBuscado) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(libroBuscado);
        } catch (JsonProcessingException e) {
            System.err.println("Error al convertir el libro a JSON: " + e.getMessage());
            return null;
        }
    }
    public static void guardarJsonEnArchivo(String json, String nombreArchivo) {
        try {
        String routeDoc = "src/main/resources/libroJSON";
        File archivo = new File(routeDoc + File.separator + nombreArchivo);
        try (FileWriter fileWriter = new FileWriter(archivo)) {
            fileWriter.write(json);
            System.out.println("Guardado en " + nombreArchivo);

        }
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON en: " + e.getMessage());
        }
    }


}
