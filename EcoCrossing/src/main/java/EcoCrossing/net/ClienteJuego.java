package EcoCrossing.net;

import EcoCrossing.net.paquetes.*;
import EcoCrossing.net.paquetes.Paquete.TiposPaquete;
import static EcoCrossing.net.paquetes.Paquete.TiposPaquete.*;
import Entidades.Entidad;
import Entidades.JugadorMultijugador;
import com.mycompany.ecocrossing.PanelJuego;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * ClienteJuego es una clase que maneja la comunicación del cliente con el
 * servidor del juego. Se encarga de enviar y recibir paquetes de datos para la
 * sincronización del estado del juego.
 */
public class ClienteJuego extends Thread {

    private InetAddress direccionIP;
    private DatagramSocket socket;
    private PanelJuego panelJuego;
    private boolean activo = true;

    /**
     * Constructor del cliente de juego.
     *
     * @param panelJuego la instancia de PanelJuego asociada.
     * @param direccionIP la dirección IP del servidor del juego.
     */
    public ClienteJuego(PanelJuego panelJuego, String direccionIP) {
        this.panelJuego = panelJuego;
        try {
            this.socket = new DatagramSocket();
            this.direccionIP = InetAddress.getByName(direccionIP);
        } catch (UnknownHostException | SocketException ex) {
            System.err.println("Error al inicializar el cliente de juego: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene el socket Datagram usado por el cliente.
     *
     * @return el socket Datagram.
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * Método principal del hilo que escucha por paquetes entrantes.
     */
    @Override
    public void run() {
        while (activo) {
            try {
                byte[] datos = new byte[1024];
                DatagramPacket paquete = new DatagramPacket(datos, datos.length);
                socket.receive(paquete);

                parsePaquete(paquete.getData(), paquete.getAddress(), paquete.getPort());

            } catch (SocketException e) {
                if (!activo) {
                    break;
                }
                System.err.println("Error de SocketException durante la recepción de paquetes: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error de IOException durante la recepción de paquetes: " + e.getMessage());
            }
        }
    }

    /**
     * Detiene el cliente de juego.
     */
    public void detener() {
        activo = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    /**
     * Analiza los datos del paquete recibido y maneja según el tipo de paquete.
     *
     * @param datos los datos del paquete recibido.
     * @param direccionIP la dirección IP del remitente del paquete.
     * @param puerto el puerto del remitente del paquete.
     */
    private void parsePaquete(byte[] datos, InetAddress direccionIP, int puerto) {
        String mensaje = new String(datos).trim();
        TiposPaquete tipo = Paquete.buscarPaquete(mensaje.substring(0, 2));
        Paquete paquete;
        switch (tipo) {
            default:
            case INVALIDO:
                break;
            case ACCEDER:
                paquete = new Paquete00Acceder(datos);
                manejarAcceso((Paquete00Acceder) paquete, direccionIP, puerto);
                break;
            case DESCONECTAR:
                paquete = new Paquete01Desconectar(datos);
                panelJuego.eliminarJugador(((Paquete01Desconectar) paquete).getNombreUsuario());
                break;
            case MOVER:
                paquete = new Paquete02Mover(datos);
                manejarMovimiento((Paquete02Mover) paquete);
                break;
            case AGREGAR_OBJETO:
                paquete = new Paquete03AgregarObjeto(datos);
                panelJuego.agregarObjeto(((Paquete03AgregarObjeto) paquete).getNombre(), ((Paquete03AgregarObjeto) paquete).getX(), ((Paquete03AgregarObjeto) paquete).getY(), ((Paquete03AgregarObjeto) paquete).getIndice(), ((Paquete03AgregarObjeto) paquete).getNumeroMapa());
                break;
            case ELIMINAR_OBJETO:
                paquete = new Paquete04EliminarObjeto(datos);
                manejarEliminacionObjeto((Paquete04EliminarObjeto) paquete);
                break;
            case FINALIZAR_JUEGO:   
                panelJuego.estadoJuego = panelJuego.estadoJuegoTerminado;
                panelJuego.ui.juegoFinalizado = true;
                panelJuego.ui.dibujarPantallaGameOver("Felicidades.");
                break;
        }
    }

    /**
     * Maneja el acceso de un nuevo jugador al juego.
     *
     * @param paquete el paquete de acceso.
     * @param direccionIP la dirección IP del jugador que accede.
     * @param puerto el puerto del jugador que accede.
     */
    private void manejarAcceso(Paquete00Acceder paquete, InetAddress direccionIP, int puerto) {
        JugadorMultijugador jugador = new JugadorMultijugador(paquete.getNombreUsuario(), paquete.getNombreSkin(), panelJuego, direccionIP, puerto);
        jugador.mundoX = paquete.getX();
        jugador.mundoY = paquete.getY();
        jugador.cargarSkin(paquete.getNombreSkin());
        jugador.setDireccion(paquete.getDireccion());
        panelJuego.agregarJugador(jugador);
    }

    /**
     * Maneja el movimiento de los jugadores.
     *
     * @param paquete el paquete de movimiento.
     */
    private void manejarMovimiento(Paquete02Mover paquete) {
        String nombreUsuario = paquete.getNombreUsuario();
        JugadorMultijugador jugador = obtenerJugadorPorNombre(nombreUsuario);
        if (jugador != null) {
            jugador.setMundoX(paquete.getX());
            jugador.setMundoY(paquete.getY());
            jugador.setSpriteNum(paquete.getSpriteNum());
            jugador.setDireccion(paquete.getDireccion());
            jugador.actualizarAnimacion();
        } else {
            Entidad npc = panelJuego.buscarNPC(nombreUsuario);
            if (npc != null) {
                npc.mundoX = paquete.getX();
                npc.mundoY = paquete.getY();
                npc.spriteNum = paquete.getSpriteNum();
                npc.direccion = paquete.getDireccion();
                npc.Actualizar();
            }
        }
    }

    /**
     * Maneja la eliminación de objetos.
     *
     * @param paquete el paquete de eliminación de objetos.
     */
    private void manejarEliminacionObjeto(Paquete04EliminarObjeto paquete) {
        int indiceEliminado = paquete.getIndice();
        int mapa = paquete.getNumeroMapa();
        panelJuego.eliminarObjeto(indiceEliminado, mapa);
    }

    /**
     * Obtiene un jugador por su nombre de usuario.
     *
     * @param nombreUsuario el nombre de usuario del jugador.
     * @return el jugador con el nombre de usuario dado, o null si no se
     * encuentra.
     */
    private JugadorMultijugador obtenerJugadorPorNombre(String nombreUsuario) {
        for (JugadorMultijugador jugador : panelJuego.getJugadoresConectados()) {
            if (jugador.getNombreUsuario().equals(nombreUsuario)) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Envía datos al servidor.
     *
     * @param datos los datos a enviar.
     */
    public void enviarDatos(byte[] datos) {
        DatagramPacket packet = new DatagramPacket(datos, datos.length, direccionIP, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Error al enviar datos al servidor: " + e.getMessage());
        }
    }
}
