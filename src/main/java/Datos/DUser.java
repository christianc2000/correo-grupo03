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
        Statement st;
        try {

            st = m_con.createStatement();
            String s_sql = "SELECT id,ci,name,lastname,birth_date,gender,number_phone,marital_status,current_residence,type,email,password,registration_date,created_at,updated_at FROM users;";
            ResultSet rs = st.executeQuery(s_sql);
            while (rs.next()) {
                DUser user = new DUser();
                user.setId(rs.getInt("id"));
                user.setCi(rs.getString("ci"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setBirthDate(rs.getDate("birth_date"));
                user.setGender(rs.getString("gender"));
                user.setNumberPhone(rs.getString("number_phone"));
                user.setMaritalStatus(rs.getString("marital_status"));
                user.setCurrentResidence(rs.getString("current_residence"));
                user.setPhoto(rs.getString("photo"));
                user.setType(rs.getString("type"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRegistrationDate(rs.getDate("registration_date"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));

                userList.add(user);
            }
            return userList;
        } catch (SQLException ex) {

            Logger.getLogger(DUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return l_usuario;
        }

    }

    public void create() {

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

    public Connection getM_con() {
        return m_con;
    }

    public void setM_con(Connection m_con) {
        this.m_con = m_con;
    }

}
