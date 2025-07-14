/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.ecocrossing;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class ManejadorIcono {
    public static void setWindowIcon(JFrame ventana) {
        // Carga el ícono desde el classpath
        URL iconoURL = ManejadorIcono.class.getResource("/Icono/EcoCrossing.png");
        if (iconoURL != null) {
            ImageIcon icono = new ImageIcon(iconoURL);
            ventana.setIconImage(icono.getImage());
        } else {
            System.err.println("No se pudo cargar el ícono.");
        }
    }
}
