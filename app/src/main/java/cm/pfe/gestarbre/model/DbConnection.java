package cm.pfe.gestarbre.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

    public static Connection connexion=null;
    public static Statement statement=null;

    public static String connecter(){

        String error = "OK";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {

             error = e1.getMessage();
            return error+"1";
        }

        try {
            connexion= DriverManager.getConnection("jdbc:mysql://PC-MARTIAL:3306/gestarbre", "gestarbre", "gestarbre");
        } catch (SQLException e1) {
            error = e1.getMessage();
            return error+"2";

        }

        try {
            statement=connexion.createStatement();
        }catch(SQLException e) {

            error = e.getMessage();
            return error+"3";
        }

        return error;
    }
}
