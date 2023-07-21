/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo03sa_mail;

/**
 *
 * @author WorkcorpDev
 */
public class Utils {
   
    
    public static String mensajeError(String p_parametro) {
        return  "<div class='error'><strong>ERROR!!! </strong><p class='texto-error'>"+p_parametro+"</p></div>";
    }

    public static String mensajeCorrecto(String p_parametro) {
        return  "<div class='correcto'><strong>SUCCEFULL!!! </strong><p class='texto-error'>"+p_parametro+"</p></div>";
    }
     
}
