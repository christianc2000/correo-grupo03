/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class ConexionDB {

    String URL_DB = "jdbc:postgresql://mail.tecnoweb.org.bo:5432/";
    // local
    //String URL_DB = "jdbc:postgresql://localhost:5432/";
    String USER_DB = "grupo03sa";
    String NAME_DB = "db_grupo03sa";
    String PASS_DB = "grup003grup003";

    private Connection con = null;
    private static ConexionDB conexion;

    private ConexionDB() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL_DB + NAME_DB, USER_DB, PASS_DB);
            if (con != null) {
                System.out.println("conexion exitosa a db_grupo05sc..!!!");
            } else {
                System.out.println("error en la conexion .!!!");
            }
        } catch (ClassNotFoundException ex) { //error de driver
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {  // error de conexion a bd
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static ConexionDB getInstancia() {
        if (conexion == null) {
            conexion = new ConexionDB();
        }
        return conexion;
    }

    public Connection getConexion() {
        return con;
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
         ConexionDB mConexion=ConexionDB.getInstancia();
    }
    
}
