/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.grupo03sa_mail;

/**
 *
 * @author Christian
 */
public class Grupo03SA_mail {

    public static void main(String[] args) {
        Manejador mail = new Manejador();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ///leer
                    mail.leer();
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        System.out.println("error al iniciar el ciclo de escucha");
                    }
                }
            }
        }).start();

    }
}
