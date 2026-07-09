package Clases;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static Connection cn = null;

    public static Connection getConnection() {

        try {

            // Cargar el driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

         // Modifica la línea de la URL para que quede así:
            String url =
                    "jdbc:sqlserver://localhost:1433;" // Cambiado a localhost:1433
                    + "databaseName=DB_VENTAS_INVENTARIO;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

            	String usuario = "sa";
            	String password = "123456";	

            cn = DriverManager.getConnection(
                    url,
                    usuario,
                    password
            );

            System.out.println("Conexión exitosa");

        } catch (Exception e) {

            System.out.println("Error de conexión:");
            e.printStackTrace();
            cn = null;
        }

        return cn;
    }

    public static void main(String[] args) {

        Connection c = getConnection();

        if (c != null)
            System.out.println("Conectado");
        else
            System.out.println("Desconectado");
    }

	public static Connection conectar() {
		// TODO Auto-generated method stub
		return null;
	}
}