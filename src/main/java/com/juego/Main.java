package com.juego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {

        Scanner teclado = new Scanner(System.in);
        Random aleatorio = new Random();

        // GENERAR numero secreto entre 1 y 10 .
        int numeroSecreto = aleatorio.nextInt(10) + 1;

        int numeroIngresado;
        String resultado;

        System.out.println("=================================");
        System.out.println("     JUEGO ADIVINA EL NUMERO");
        System.out.println("=================================");

        System.out.println("Adivina un numero entre 1 y 10");
        System.out.print("Ingresa tu numero: ");    

    }
}