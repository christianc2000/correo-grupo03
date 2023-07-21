/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import DBConexion.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
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
public class DNurse {

    private int id;
    private String cv;
    private String function;
    private String createdAt;
    private String updatedAt;
    private ConexionDB m_conexion;
    private Connection m_con;

    public DNurse() {
        this.m_conexion = ConexionDB.getInstancia();
        this.m_con = m_conexion.getConexion();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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

    public Connection getM_con() {
        return m_con;
    }

    public void setM_con(Connection m_con) {
        this.m_con = m_con;
    }

    public boolean create() {
        try {
            Statement st = m_con.createStatement();
            String s_sql = "INSERT INTO nurses (id, cv, function) VALUES (" + id + ", '" + cv + "', '" + function + "')";
            int rowsAffected = st.executeUpdate(s_sql);
            st.close();
            return rowsAffected > 0; // Devuelve true si se insertó al menos una fila correctamente
        } catch (SQLException ex) {
            Logger.getLogger(DDoctor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<DNurse> getAllNurses() {
        ArrayList<DNurse> nurseList = new ArrayList<>();
        try {
            Statement st = m_con.createStatement();
            String s_sql = "SELECT id, cv, function, created_at, updated_at FROM nurses;";
            ResultSet rs = st.executeQuery(s_sql);
            while (rs.next()) {
                DNurse nurse = new DNurse();
                nurse.setId(rs.getInt("id"));
                nurse.setCv(rs.getString("cv"));
                nurse.setFunction(rs.getString("function"));
                nurse.setCreatedAt(rs.getString("created_at"));
                nurse.setUpdatedAt(rs.getString("updated_at"));
                nurseList.add(nurse);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DNurse.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return nurseList;
    }

    public void mostrar(ArrayList<DNurse> lista) {
        System.out.println("LISTA DE NURSES:");
        for (DNurse nurse : lista) {
            System.out.println("ID: " + nurse.getId());
            System.out.println("cv: " + nurse.getCv());
            System.out.println("function: " + nurse.getFunction());
            System.out.println("Fecha de Creación: " + nurse.getCreatedAt());
            System.out.println("Fecha de Actualización: " + nurse.getUpdatedAt());
            System.out.println("------------------------------");
        }
    }

    public static void main(String[] args) {
         DNurse DNurse = new DNurse(); // Crear una instancia de la clase DNurses
        ArrayList<DNurse> nurseList = DNurse.getAllNurses(); // Obtener la lista de nurses
        if (nurseList != null) {
            DNurse.mostrar(nurseList); // Mostrar la lista de NURSES
        } else {
            System.out.println("No se pudo obtener la lista de Nurses.");
        }

  /*      DNurse dNurse = new DNurse(); // Crear una instancia de la clase DNurse

        // Configurar los datos del nuevo usuario utilizando los métodos setters
        dNurse.setId(13);
        dNurse.setCv("cv - new enfermera");
        dNurse.setFunction("Área de inyecciones");
        if (dNurse.create()) {
            System.out.println("Nuevo ENFERMERA insertado correctamente.");
        } else {
            System.out.println("No se pudo insertar el nuevo ENFERMERA.");
        }
*/
    }
}
