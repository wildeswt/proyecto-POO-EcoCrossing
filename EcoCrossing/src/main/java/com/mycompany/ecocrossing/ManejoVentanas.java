package com.mycompany.ecocrossing;

import EcoCrossing.net.paquetes.Paquete01Desconectar;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * Clase que implementa la interfaz WindowListener para manejar eventos de ventana.
 */

public class ManejoVentanas implements WindowListener{
    private final PanelJuego panelJuego;
    private final JFrame ventana;
    
    /**
     * Constructor de la clase ManejoVentanas.
     * 
     * @param panelJuego El panel de juego asociado a la ventana.
     * @param ventana    La ventana JFrame a la que se aplica el manejo de eventos.
     */
    public ManejoVentanas(PanelJuego panelJuego, JFrame ventana){
        this.panelJuego = panelJuego;
        this.ventana = ventana;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void windowOpened(WindowEvent e) {
    }

    /**
     * // Código para enviar un paquete de desconexión al cerrar la ventana
     * @param e representa el evento de ventana que indica que la ventana se está cerrando
     */
    @Override
    public void windowClosing(WindowEvent e) {
        Paquete01Desconectar paquete = new Paquete01Desconectar(this.panelJuego.jugador.getNombreUsuario());
        paquete.escribirDatos(this.panelJuego.clienteSocket);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowClosed(WindowEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowIconified(WindowEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowActivated(WindowEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
