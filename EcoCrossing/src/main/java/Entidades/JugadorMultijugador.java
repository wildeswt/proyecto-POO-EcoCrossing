package Entidades;

import com.mycompany.ecocrossing.ManejoTeclas;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.net.InetAddress;

/**
 * Clase JugadorMultijugador representa un jugador en un entorno multijugador.
 * Hereda de la clase Jugador y añade información sobre la dirección IP y el puerto.
 */
public class JugadorMultijugador extends Jugador {
    public InetAddress direccionIP;
    public int puerto;

    /**
     * Constructor para inicializar un objeto JugadorMultijugador con manejo de teclas.
     *
     * @param nombreUsuario El nombre del usuario del jugador.
     * @param nombreSkin El nombre de la skin del jugador.
     * @param panelJuego El panel de juego asociado al jugador.
     * @param manejoTeclas El objeto ManejoTeclas para manejar la entrada del teclado.
     * @param direccionIP La dirección IP del jugador.
     * @param puerto El puerto de conexión del jugador.
     */
    public JugadorMultijugador(String nombreUsuario, String nombreSkin, PanelJuego panelJuego, ManejoTeclas manejoTeclas, InetAddress direccionIP, int puerto) {
        super(nombreUsuario, nombreSkin, panelJuego, manejoTeclas);
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }

    /**
     * Constructor para inicializar un objeto JugadorMultijugador sin manejo de teclas.
     *
     * @param nombreUsuario El nombre del usuario del jugador.
     * @param nombreSkin El nombre de la skin del jugador.
     * @param panelJuego El panel de juego asociado al jugador.
     * @param direccionIP La dirección IP del jugador.
     * @param puerto El puerto de conexión del jugador.
     */
    public JugadorMultijugador(String nombreUsuario, String nombreSkin, PanelJuego panelJuego, InetAddress direccionIP, int puerto) {
        super(nombreUsuario, nombreSkin, panelJuego, null);
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }

    /**
     * Actualiza el estado del jugador multijugador.
     */
    @Override
    public void actualizar() {
        super.actualizar();
    }

    /**
     * Dibuja el jugador multijugador en el contexto gráfico proporcionado.
     *
     * @param g2 El contexto gráfico.
     */
    @Override
    public void dibujar(Graphics2D g2) {
        super.dibujar(g2);
    }
}
