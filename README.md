# -_juegoAdivinaNumeroOracle19c_- :. 
Juego Adivina el Número en Java con Oracle 19c:
Aplicación de consola desarrollada en Java 21, utilizando IntelliJ IDEA, Maven y Oracle Database 19c.

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/9de83b08-53d1-4b90-92e7-46ff4fe5769d" />

```

El juego genera un número aleatorio entre 1 y 10. El usuario debe ingresar un número y el programa evalúa el resultado mediante tres condicionales (if, else if y else). Cada partida se almacena en una tabla de Oracle 19c mediante JDBC.
Características
- Genera un número secreto entre 1 y 10.
- Captura un número ingresado por el usuario.
- Utiliza tres condicionales para evaluar el resultado.
- Guarda el número secreto, el número ingresado, el resultado y la fecha de la partida.
- Se conecta a Oracle Database 19c usando JDBC.
- Utiliza PreparedStatement para ejecutar el INSERT.
Tecnologías
- Java 21
- IntelliJ IDEA
- Maven
- Oracle Database 19c
- JDBC
- Oracle JDBC Driver (ojdbc11)
Requisitos previos
1. Tener instalado JDK 21.
2. Tener instalado IntelliJ IDEA.
3. Tener instalado y en ejecución Oracle Database 19c.
4. Tener Maven disponible desde IntelliJ IDEA.
5. Contar con un usuario de Oracle con permisos para crear tablas e insertar registros.
Estructura del proyecto
JuegoAdivinaNumeroOracle19c/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── juego/
                    ├── Main.java
                    └── ConexionOracle.java
1. Crear la tabla en Oracle 19c
Conéctate a Oracle mediante SQL Developer o SQL*Plus y ejecuta el siguiente script.
CREATE TABLE PARTIDAS_JUEGO (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NUMERO_SECRETO NUMBER NOT NULL,
    NUMERO_INGRESADO NUMBER NOT NULL,
    RESULTADO VARCHAR2(30) NOT NULL,
    FECHA_PARTIDA DATE DEFAULT SYSDATE
);
Consultar las partidas
SELECT
    ID,
    NUMERO_SECRETO,
    NUMERO_INGRESADO,
    RESULTADO,
    FECHA_PARTIDA
FROM PARTIDAS_JUEGO
ORDER BY ID DESC;
2. Archivo pom.xml
Este archivo configura Maven y agrega el controlador JDBC de Oracle.
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.juego</groupId>
    <artifactId>JuegoAdivinaNumeroOracle19c</artifactId>
    <version>1.0</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.6.0.24.10</version>
        </dependency>
    </dependencies>

</project>
3. Clase ConexionOracle.java
Ubicación:

src/main/java/com/juego/ConexionOracle.java

Esta clase contiene los parámetros de conexión y el método que abre la conexión con Oracle.
package com.juego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/orcl";

    private static final String USUARIO = "SYSTEM";

    private static final String PASSWORD = "TU_PASSWORD";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}
Configurar la conexión
Modifica los siguientes valores según la configuración de tu base de datos:
private static final String URL =
        "jdbc:oracle:thin:@localhost:1521/orcl";

private static final String USUARIO = "SYSTEM";

private static final String PASSWORD = "TU_PASSWORD";
- localhost: servidor donde está instalado Oracle.
- 1521: puerto predeterminado de Oracle Listener.
- orcl: nombre del servicio de Oracle.
- SYSTEM: usuario de la base de datos.
- TU_PASSWORD: contraseña del usuario.
Si tu base de datos usa otro servicio, reemplaza orcl por el nombre correcto.
4. Clase Main.java
Ubicación:

src/main/java/com/juego/Main.java

Esta es la clase principal del juego. Captura el número ingresado, evalúa el resultado mediante tres condicionales y guarda la partida en Oracle 19c.
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

        // Generar numero secreto entre 1 y 10
        int numeroSecreto = aleatorio.nextInt(10) + 1;

        int numeroIngresado;

        String resultado;

        System.out.println("=================================");
        System.out.println("     JUEGO ADIVINA EL NUMERO");
        System.out.println("=================================");

        System.out.println("Adivina un numero entre 1 y 10");
        System.out.print("Ingresa tu numero: ");

        numeroIngresado = teclado.nextInt();

        // Primer condicional
        if (numeroIngresado == numeroSecreto) {

            System.out.println("¡FELICIDADES! GANASTE EL JUEGO");

            resultado = "GANADO";

        }

        // Segundo condicional
        else if (numeroIngresado > numeroSecreto) {

            System.out.println("El numero ingresado es demasiado alto");

            resultado = "PERDIDO";

        }

        // Tercer condicional
        else {

            System.out.println("El numero ingresado es demasiado bajo");

            resultado = "PERDIDO";

        }

        System.out.println("Numero secreto: " + numeroSecreto);

        System.out.println("Resultado: " + resultado);

        // Procesar informacion en Oracle 19c
        guardarPartida(
                numeroSecreto,
                numeroIngresado,
                resultado
        );

        teclado.close();

    }

    public static void guardarPartida(
            int numeroSecreto,
            int numeroIngresado,
            String resultado) {

        String sql = """
                INSERT INTO PARTIDAS_JUEGO
                (NUMERO_SECRETO, NUMERO_INGRESADO, RESULTADO)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conexion = ConexionOracle.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, numeroSecreto);

            sentencia.setInt(2, numeroIngresado);

            sentencia.setString(3, resultado);

            int filas = sentencia.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Partida almacenada correctamente en Oracle 19c"
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
5. Ejecutar el proyecto en IntelliJ IDEA
    1. Abre IntelliJ IDEA.
    2. Selecciona New Project.
    3. Selecciona Maven.
    4. Configura el JDK 21.
    5. Asigna el nombre JuegoAdivinaNumeroOracle19c.
    6. Crea los paquetes y las clases según la estructura del proyecto.
    7. Copia el contenido de los archivos pom.xml, ConexionOracle.java y Main.java.
    8. Configura las credenciales de Oracle en ConexionOracle.java.
    9. Ejecuta el script SQL para crear la tabla PARTIDAS_JUEGO.
    10. Recarga Maven para descargar la dependencia JDBC.
    11. Ejecuta la clase Main.java.
6. Ejemplo de ejecución
=================================
     JUEGO ADIVINA EL NUMERO
=================================
Adivina un numero entre 1 y 10
Ingresa tu numero: 7

El numero ingresado es demasiado alto
Numero secreto: 4
Resultado: PERDIDO

Partida almacenada correctamente en Oracle 19c
Otro ejemplo:
=================================
     JUEGO ADIVINA EL NUMERO
=================================
Adivina un numero entre 1 y 10
Ingresa tu numero: 8

¡FELICIDADES! GANASTE EL JUEGO
Numero secreto: 8
Resultado: GANADO

Partida almacenada correctamente en Oracle 19c
7. Verificar los datos en Oracle 19c
Después de jugar, ejecuta:
SELECT
    ID,
    NUMERO_SECRETO,
    NUMERO_INGRESADO,
    RESULTADO,
    FECHA_PARTIDA
FROM PARTIDAS_JUEGO
ORDER BY ID DESC;
Ejemplo de resultado:
ID	NUMERO_SECRETO	NUMERO_INGRESADO	RESULTADO
1	4	7	PERDIDO
2	8	8	GANADO
3	2	1	PERDIDO


8. Conceptos de programación utilizados
Variables
Se utilizan variables de tipo int para almacenar los números y String para almacenar el resultado.
int numeroSecreto;
int numeroIngresado;
String resultado;
Número aleatorio
La clase Random genera un número aleatorio entre 1 y 10.
Random aleatorio = new Random();

int numeroSecreto = aleatorio.nextInt(10) + 1;
Tres condicionales
if (numeroIngresado == numeroSecreto) {
    resultado = "GANADO";
}
else if (numeroIngresado > numeroSecreto) {
    resultado = "PERDIDO";
}
else {
    resultado = "PERDIDO";
}
JDBC
Java utiliza JDBC para conectarse con Oracle 19c y ejecutar el comando SQL INSERT.
PreparedStatement sentencia =
        conexion.prepareStatement(sql);
9. Resultado final
El proyecto permite practicar programación básica en Java y persistencia de datos en Oracle Database 19c.

Al ejecutar el juego:
1. Java genera el número secreto.
2. El usuario ingresa un número.
3. Los condicionales evalúan el resultado.
4. Java muestra el mensaje correspondiente.
5. JDBC guarda la información de la partida en Oracle 19c.
6. Los registros pueden consultarse desde SQL Developer.
Proyecto: Juego Adivina el Número
Lenguaje: Java 21
IDE: IntelliJ IDEA
Base de datos: Oracle Database 19c
Conectividad: JDBC
:. . / .
