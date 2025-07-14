package Objeto;

import com.mycompany.ecocrossing.EscaladorImagen;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * La clase SuperObjeto proporciona atributos y métodos comunes para objetos en
 * el juego.
 */
public class SuperObjeto {

    public BufferedImage imagen, imagen2, imagen3;
    public String nombre;
    public boolean colision = false;
    public int mundoX, mundoY;
    public Rectangle areaSolida = new Rectangle(0, 0, 48, 48);
    public int areaSolidaDefaultX = 0;
    public int areaSolidaDefaultY = 0;
    EscaladorImagen herramientaUtil = new EscaladorImagen();
    // Atributos de los objetos:
    public String descripcion = "";
    public int precio;

    /**
     * Dibuja el objeto en la pantalla del juego.
     *
     * @param g2 El contexto gráfico 2D donde se dibuja el objeto.
     * @param panelJuego El panel de juego donde se realiza el dibujo.
     */
    public void dibujar(Graphics2D g2, PanelJuego panelJuego) {
        int pantallaX = mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
        int pantallaY = mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;

        if (mundoX + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoX - panelJuego.jugador.pantallaX
                && mundoX - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX
                && mundoY + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoY - panelJuego.jugador.pantallaY
                && mundoY - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY) {
            g2.drawImage(imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
        }
    }

    /**
     * Ubica el objeto en las coordenadas especificadas, escaladas según el
     * tamaño indicado.
     *
     * @param x La coordenada x en el mundo del juego.
     * @param y La coordenada y en el mundo del juego.
     * @param escala El factor de escala para las coordenadas.
     */
    public void ubicar(int x, int y, int escala) {
        mundoX = x * escala;
        mundoY = y * escala;
    }
}
