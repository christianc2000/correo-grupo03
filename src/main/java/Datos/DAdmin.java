/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import DBConexion.ConexionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class DAdmin {

    private int id;
    private String createdAt;
    private String updatedAt;
    private ConexionDB m_conexion;
    private Connection m_con;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ConexionDB getM_conexion() {
        return m_conexion;
    }

    public void setM_conexion(ConexionDB m_conexion) {
        this.m_conexion = m_conexion;
    }

    public DAdmin() {
        this.m_conexion = ConexionDB.getInstancia();
        this.m_con = m_conexion.getConexion();
    }

    public boolean create() {
        try {
            Statement st = m_con.createStatement();
            String s_sql = "INSERT INTO admins (id) "
                    + "VALUES (" + id + ")";
            int rowsAffected = st.executeUpdate(s_sql);
            st.close();
            return rowsAffected > 0; // Devuelve true si se insertó al menos una fila correctamente
        } catch (SQLException ex) {
            Logger.getLogger(DUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<DAdmin> getAllAdmins() {
        ArrayList<DAdmin> adminList = new ArrayList<>();
        try {
            Statement st = m_con.createStatement();
            String s_sql = "SELECT id, created_at, updated_at FROM admins;";
            ResultSet rs = st.executeQuery(s_sql);
            while (rs.next()) {
                DAdmin admin = new DAdmin();
                admin.setId(rs.getInt("id"));
                admin.setCreatedAt(rs.getString("created_at"));
                admin.setUpdatedAt(rs.getString("updated_at"));
                adminList.add(admin);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAdmin.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return adminList;
    }

    public void mostrar(ArrayList<DAdmin> lista) {
        System.out.println("LISTA DE ADMINS:");
        for (DAdmin admin : lista) {
            System.out.println("ID: " + admin.getId());
            System.out.println("Fecha de Creación: " + admin.getCreatedAt());
            System.out.println("Fecha de Actualización: " + admin.getUpdatedAt());
            System.out.println("------------------------------");
        }
    }

    public void update() {

    }

    public Connection getM_con() {
        return m_con;
    }

    public void setM_con(Connection m_con) {
        this.m_con = m_con;
    }

    public static void main(String[] args) {
         DAdmin dAdmin = new DAdmin(); // Crear una instancia de la clase DUser
        ArrayList<DAdmin> adminList = dAdmin.getAllAdmins(); // Obtener la lista de usuarios

        if (adminList != null) {
            dAdmin.mostrar(adminList); // Mostrar la lista de usuarios
        } else {
            System.out.println("No se pudo obtener la lista de admins.");
        }
        /*DAdmin dAdmin = new DAdmin(); // Crear una instancia de la clase DUser

        // Configurar los datos del nuevo usuario utilizando los métodos setters
        dAdmin.setId(11);
        if (dAdmin.create()) {
            System.out.println("Nuevo Admin insertado correctamente.");
        } else {
            System.out.println("No se pudo insertar el nuevo Admin.");
        }
*/
    }
}
