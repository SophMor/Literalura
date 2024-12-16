package com.aluracursos.desafio.view;

import java.util.Scanner;

public class View {
    private Scanner sc;

    public View() {
        sc = new Scanner(System.in);
    }

    public void showMenu(){
        System.out.println("1. Buscar por nombre \n" +
                "2. Consultar Libros\n"+
                "3. Listar autores registrados\n" +
                "4. Consultar Por Fecha autor\n" +
                "5. Consultar por Idioma\n"+
                "6. Convertir libro a JSON \n"+
                "0. Salir"

        );
    }

    public void print(String message){
        System.out.println(message);
    }

    public String read(){
        return sc.next();
    }
    public int readInt(){
    return sc.nextInt();
    }
    public String readString(){
        return sc.nextLine();
    }
    public float readFloat(){
        return sc.nextFloat();
    }
}
