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
public class DDoctor {

    //DOCTOR usa el mismo id que su usuario
    private int id;
    private String cv;
    private String speciality;
    private String createdAt;
    private String updatedAt;

    private ConexionDB m_conexion;
    private Connection m_con;

    public DDoctor() {
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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
            String s_sql = "INSERT INTO doctors (id, cv, speciality) VALUES (" + id + ", '" + cv + "', '" + speciality + "')";
            int rowsAffected = st.executeUpdate(s_sql);
            st.close();
            return rowsAffected > 0; // Devuelve true si se insertó al menos una fila correctamente
        } catch (SQLException ex) {
            Logger.getLogger(DDoctor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<DDoctor> getAllDoctors() {
        ArrayList<DDoctor> doctorsList = new ArrayList<>();
        try {
            Statement st = m_con.createStatement();
            String s_sql = "SELECT id, cv, speciality, created_at, updated_at FROM Doctors;";
            ResultSet rs = st.executeQuery(s_sql);
            while (rs.next()) {
                DDoctor doctors = new DDoctor();
                doctors.setId(rs.getInt("id"));
                doctors.setCv(rs.getString("cv"));
                doctors.setSpeciality(rs.getString("speciality"));
                doctors.setCreatedAt(rs.getString("created_at"));
                doctors.setUpdatedAt(rs.getString("updated_at"));
                doctorsList.add(doctors);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DDoctor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return doctorsList;
    }

    public void mostrar(ArrayList<DDoctor> lista) {
        System.out.println("LISTA DE Doctors:");
        for (DDoctor doctors : lista) {
            System.out.println("ID: " + doctors.getId());
            System.out.println("cv: " + doctors.getCv());
            System.out.println("speciality: " + doctors.getSpeciality());
            System.out.println("Fecha de Creación: " + doctors.getCreatedAt());
            System.out.println("Fecha de Actualización: " + doctors.getUpdatedAt());
            System.out.println("------------------------------");
        }
    }

    public static void main(String[] args) {
        DDoctor DDoctor = new DDoctor(); // Crear una instancia de la clase DDoctors
        ArrayList<DDoctor> doctorsList = DDoctor.getAllDoctors(); // Obtener la lista de Doctors
        if (doctorsList != null) {
            DDoctor.mostrar(doctorsList); // Mostrar la lista de Doctors
        } else {
            System.out.println("No se pudo obtener la lista de Doctors.");
        }
        /*DDoctor dDoctor = new DDoctor(); // Crear una instancia de la clase DUser

        // Configurar los datos del nuevo usuario utilizando los métodos setters
        dDoctor.setId(13);
        dDoctor.setCv("cv - new doctor");
        dDoctor.setSpeciality("Medico general");
        if (dDoctor.create()) {
            System.out.println("Nuevo Doctor insertado correctamente.");
        } else {
            System.out.println("No se pudo insertar el nuevo DOCTOR.");
        }*/

    }
}
