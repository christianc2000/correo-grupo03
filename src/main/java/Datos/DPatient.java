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
public class DPatient {

    private int id;
    private String age;
    private String occupation;
    private String children;
    private String createdAt;
    private String updatedAt;
    private ConexionDB m_conexion;
    private Connection m_con;

    public DPatient() {
        this.m_conexion = ConexionDB.getInstancia();
        this.m_con = m_conexion.getConexion();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
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
            String s_sql = "INSERT INTO patients (id, age, occupation, children) VALUES (" + id + ", " + Integer.parseInt(age) + ", '" + occupation + "', '" + children + "')";
            int rowsAffected = st.executeUpdate(s_sql);
            st.close();
            return rowsAffected > 0; // Devuelve true si se insertó al menos una fila correctamente
        } catch (SQLException ex) {
            Logger.getLogger(DPatient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<DPatient> getAllPatients() {
        ArrayList<DPatient> patientsList = new ArrayList<>();
        try {
            Statement st = m_con.createStatement();
            String s_sql = "SELECT id, age, occupation, children, created_at, updated_at FROM Patients;";
            ResultSet rs = st.executeQuery(s_sql);
            while (rs.next()) {
                DPatient patients = new DPatient();
                patients.setId(rs.getInt("id"));
                patients.setAge(rs.getString("age"));
                patients.setOccupation(rs.getString("occupation"));
                patients.setChildren(rs.getString("children"));
                patients.setCreatedAt(rs.getString("created_at"));
                patients.setUpdatedAt(rs.getString("updated_at"));
                patientsList.add(patients);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DPatient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return patientsList;
    }

    public void mostrar(ArrayList<DPatient> lista) {
        System.out.println("LISTA DE Patients:");
        for (DPatient patients : lista) {
            System.out.println("ID: " + patients.getId());
            System.out.println("age: " + patients.getAge());
            System.out.println("occupation: " + patients.getOccupation());
            System.out.println("children: " + patients.getChildren());
            System.out.println("Fecha de Creación: " + patients.getCreatedAt());
            System.out.println("Fecha de Actualización: " + patients.getUpdatedAt());
            System.out.println("------------------------------");
        }
    }

    public static void main(String[] args) {
        DPatient DPatient = new DPatient(); // Crear una instancia de la clase DPatients
        ArrayList<DPatient> patientsList = DPatient.getAllPatients(); // Obtener la lista de Patients
        if (patientsList != null) {
            DPatient.mostrar(patientsList); // Mostrar la lista de Patients
        } else {
            System.out.println("No se pudo obtener la lista de Patients.");
        }

        /*DPatient dPatient = new DPatient(); // Crear una instancia de la clase dPatient

        // Configurar los datos del nuevo usuario utilizando los métodos setters
        dPatient.setId(13);
        dPatient.setAge("33");
        dPatient.setOccupation("Carpinter");
        dPatient.setChildren("1 hijo");
        if (dPatient.create()) {
            System.out.println("Nuevo PACIENTE insertado correctamente.");
        } else {
            System.out.println("No se pudo insertar el nuevo PACIENTE.");
        }*/

    }
}
