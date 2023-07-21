/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.DUser;
import java.util.ArrayList;

/**
 *
 * @author WorkcorpDev
 */
public class NUser {

    private DUser d_usuario;

    public NUser() {
        d_usuario = new DUser();
    }

    public String listUsuario(String[] prt_parametros) {
        ArrayList<DUser> l_usuario = new ArrayList<>();
        if (prt_parametros[0].equals("*")) {//SI MANDA PARAMETRO TODO MOSTRARÁ CON TODOS LOS DATOS
            l_usuario = d_usuario.getAllUsers();
            if (l_usuario.size() > 0) {
                String s_res = "<h2>Lista USUARIOS</h2>";
                s_res = s_res + "<table><tr>"
                        + "<td>ID</td>"
                        + "<td>CI</td>"
                        + "<td>NOMBRE</td>"
                        + "<td>APELLIDO</td>"
                        + "<td>FECHA NACIMIENTO</td>"
                        + "<td>SEXO</td>"
                        + "<td>CELULAR</td>"
                        + "<td>ESTADO CIVIL</td>"
                        + "<td>SEXO</td>"
                        + "<td>RESIDENCIA</td>"
                        + "<td>TIPO</td>"
                        + "<td>PASSWORD</td>"
                        + "<td>FECHA REGISTRO</td>"
                        + "</tr>";
                for (DUser i_Usuario : l_usuario) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td>" + i_Usuario.getId() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getCi() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getName() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getLastname() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getBirthDate() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getMaritalStatus() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getGender() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getCurrentResidence() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getType() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getPassword() + "</td>";
                    s_res = s_res + "<td>" + i_Usuario.getRegistrationDate() + "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }

        }
        return mensajeCorrecto("lista vacia");
    }

    private String mensajeCorrecto(String p_parametro) {
        return "<div class='correcto'><strong>SUCCEFULL!!! </strong><p class='texto-error'>" + p_parametro + "</p></div>";
    }

}
