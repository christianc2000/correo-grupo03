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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class DUser {

    //Usuario
    private int id;
    private String ci;
    private String name;
    private String lastname;
    private String birthDate;
    private String gender;
    private String numberPhone;
    private String maritalStatus;
    private String currentResidence;
    private String type;
    private String email;
    private String password;
    private String registrationDate;
    private String createdAt;
    private String updatedAt;
    private ConexionDB m_conexion;
    private Connection m_con;

    public DUser() {
        this.m_conexion = ConexionDB.getInstancia();
        this.m_con = m_conexion.getConexion();
    }

    public ArrayList<DUser> getAllUsers() {
        ArrayList<DUser> userList = new ArrayList<>();
        try {
            Statement st = m_con.createStatement();
            String s_sql = "SELECT id, ci, name, lastname, birth_date, gender, number_phone, marital_status, current_residence, photo, type, email, password, registration_date, created_at, updated_at FROM users;";
            ResultSet rs = st.executeQuery(s_sql);
            while (rs.next()) {
                DUser user = new DUser();
                user.setId(rs.getInt("id"));
                user.setCi(rs.getString("ci"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setBirthDate(rs.getString("birth_date"));
                user.setGender(rs.getString("gender"));
                user.setNumberPhone(rs.getString("number_phone"));
                user.setMaritalStatus(rs.getString("marital_status"));
                user.setCurrentResidence(rs.getString("current_residence"));
                user.setType(rs.getString("type"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRegistrationDate(rs.getString("registration_date"));
                user.setCreatedAt(rs.getString("created_at"));
                user.setUpdatedAt(rs.getString("updated_at"));
                userList.add(user);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DUser.class.getName()).log(Level.SEVERE, null, ex);
            // Aquí, en lugar de retornar una lista l_usuario, podrías retornar null o una lista vacía.
            // Por ejemplo:
            // return new ArrayList<DUser>();
            return null;
        }
        return userList;
    }

    public void mostrar(ArrayList<DUser> lista) {
        System.out.println("LISTA DE USUARIOS:");
        for (DUser user : lista) {
            System.out.println("ID: " + user.getId());
            System.out.println("CI: " + user.getCi());
            System.out.println("Nombre: " + user.getName());
            System.out.println("Apellido: " + user.getLastname());
            System.out.println("Fecha de Nacimiento: " + user.getBirthDate());
            System.out.println("Género: " + user.getGender());
            System.out.println("Número de Teléfono: " + user.getNumberPhone());
            System.out.println("Estado Civil: " + user.getMaritalStatus());
            System.out.println("Residencia Actual: " + user.getCurrentResidence());
            System.out.println("Tipo: " + user.getType());
            System.out.println("Correo Electrónico: " + user.getEmail());
            System.out.println("Contraseña: " + user.getPassword());
            System.out.println("Fecha de Registro: " + user.getRegistrationDate());
            System.out.println("Fecha de Creación: " + user.getCreatedAt());
            System.out.println("Fecha de Actualización: " + user.getUpdatedAt());
            System.out.println("------------------------------");
        }
    }

    public boolean create() {
        try {
            Statement st = m_con.createStatement();
            String s_sql = "INSERT INTO users (ci, name, lastname, birth_date, gender, number_phone, marital_status, current_residence, type, email, password, registration_date) "
                    + "VALUES ('" + ci + "', '" + name + "', '" + lastname + "', '" + birthDate + "', '" + gender + "', '" + numberPhone + "', '" + maritalStatus + "', '" + currentResidence + "', '" + type + "', '" + email + "', '" + password + "', '" + registrationDate + "')";
            int rowsAffected = st.executeUpdate(s_sql);
            st.close();
            return rowsAffected > 0; // Devuelve true si se insertó al menos una fila correctamente
        } catch (SQLException ex) {
            Logger.getLogger(DUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public void update() {

    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCurrentResidence() {
        return currentResidence;
    }

    public void setCurrentResidence(String currentResidence) {
        this.currentResidence = currentResidence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
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

    public static void main(String[] args) {
       DUser dUser = new DUser(); // Crear una instancia de la clase DUser
        ArrayList<DUser> userList = dUser.getAllUsers(); // Obtener la lista de usuarios

        if (userList != null) {
            dUser.mostrar(userList); // Mostrar la lista de usuarios
        } else {
            System.out.println("No se pudo obtener la lista de usuarios.");
        }
         
        /*DUser dUser = new DUser(); // Crear una instancia de la clase DUser

        // Configurar los datos del nuevo usuario utilizando los métodos setters
        dUser.setCi("1122334");
        dUser.setName("NURSE");
        dUser.setLastname("Usuario");
        dUser.setBirthDate("1990-01-01");
        dUser.setGender("M");
        dUser.setNumberPhone("65654212");
        dUser.setMaritalStatus("Soltero");
        dUser.setCurrentResidence("Santa Cruz, bolivia");
        dUser.setType("E");
        dUser.setEmail("nuevo@gmail.com");
        dUser.setPassword("12345678");
        dUser.setRegistrationDate("2023-07-20");

        // Insertar el nuevo usuario en la base de datos
        if (dUser.create()) {
            System.out.println("Nuevo usuario insertado correctamente.");
        } else {
            System.out.println("No se pudo insertar el nuevo usuario.");
        }*/
    }

}
