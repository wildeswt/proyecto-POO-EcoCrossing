package com.mycompany.ecocrossing;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EcoCrossing {

    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setTitle("ECO Crossing: Aventuras Verdes");

        ManejadorIcono.setWindowIcon(ventana); // Llama al método aquí

        PanelJuego panelJuego = new PanelJuego();
        ventana.add(panelJuego);
        ventana.pack();

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ManejoVentanas manejoVentanas = new ManejoVentanas(panelJuego, ventana);
        ventana.addWindowListener(manejoVentanas);

        panelJuego.setupJuego();
        panelJuego.preInicioJuego();
        
    }
}
