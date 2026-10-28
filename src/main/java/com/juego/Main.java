package com.juego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

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

        numeroIngresado = teclado.nextInt();

        // primer condicional.
        if (numeroIngresado == numeroSecreto) {

            System.out.println("¡FELICIDADES! GANASTE EL JUEGO");
            resultado = "GANADO";
        }

        // segundo condicional.
        else if (numeroIngresado > numeroSecreto) {
            System.out.println("El numero ingresado es demasiado alto");
            resultado = "PERDIDO";
        }

        // tercer condicional.
        else {
            System.out.println("El numero ingresado es demasiado bajo");
            resultado = "PERDIDO";
        }


        System.out.println("Numero secreto: " + numeroSecreto);
        System.out.println("Resultado: " + resultado);

        // PROCESAR INFORMACION en ORACLE 19C.
        guardarPartida(numeroSecreto, numeroIngresado, resultado);
        teclado.close();
    }

    public static void guardarPartida(int numeroSecreto, int numeroIngresado, String resultado)
    {
            String sql = """
                    INSERT INTO PARTIDAS_JUEGO
                    (NUMERO_SECRETO, NUMERO_INGRESADO, RESULTADO)
                    VALUES (?,?,?)
                    """;
            try (
                    Connection conexion = ConexionOracle.conectar();
                    PreparedStatement sentencia = conexion.prepareStatement(sql)
            ) {

                sentencia.setInt(1, numeroSecreto);
                sentencia.setInt(2, numeroIngresado);
                sentencia.setString(3, resultado);

                int filas = sentencia.executeUpdate();

                if (filas > 0) {
                    System.out.println(
                            "Partida almacenada correctamente en Oracle!"
                    );
                }
            } catch (SQLException e) {
                System.out.println(
                        "Error al guardar la partida en Oracle"
                );

                System.out.println(e.getMessage());
            }

    }
}