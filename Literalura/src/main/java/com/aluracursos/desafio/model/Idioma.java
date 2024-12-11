package com.aluracursos.desafio.model;

public enum Idioma {
    INGLES("EN"),
    PORTUGUES("PT"),
    FRANCE("FR"),
    ITALIANO("IT"),
    ESPAÑOL("ES");
    private String idioma;
    Idioma(String idioma) {
        this.idioma = idioma;
    }
    public static Idioma fromString(String text) {
        for (Idioma idioma1 : Idioma.values()) {
            if (idioma1.idioma.equalsIgnoreCase(text)) {
                return idioma1;
            }
        }
        throw new IllegalArgumentException("Ninguna encontrada"+text);}
}
