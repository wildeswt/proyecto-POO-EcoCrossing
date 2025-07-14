package EcoCrossing.net;

import EcoCrossing.net.paquetes.*;
import EcoCrossing.net.paquetes.Paquete.TiposPaquete;
import static EcoCrossing.net.paquetes.Paquete.TiposPaquete.*;
import Entidades.Entidad;
import Entidades.JugadorMultijugador;
import Objeto.Electrodomestico;
import Objeto.Valla;
import com.mycompany.ecocrossing.PanelJuego;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * La clase {@code ServidorJuego} representa el servidor del juego EcoCrossing.
 * Este servidor se encarga de manejar las conexiones de los jugadores, recibir
 * y enviar datos a través de sockets y gestionar el estado del juego.
 */
public class ServidorJuego extends Thread {

    private DatagramSocket socket;
    private PanelJuego panelJuego;
    private ArrayList<JugadorMultijugador> jugadoresConectados = new ArrayList<>();
    boolean activo = true;

    /**
     * Constructor del servidor de juego.
     *
     * @param panelJuego El panel del juego asociado al servidor.
     */
    public ServidorJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        try {
            this.socket = new DatagramSocket(1331);  // Configura el socket en el puerto por defecto
        } catch (SocketException e) {
            System.err.println("Error al crear el socket del servidor en el puerto 1331");
        }
    }

    /**
     * Obtiene el socket del servidor.
     *
     * @return El socket del servidor.
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * Obtiene la dirección IP local del servidor.
     *
     * @return La dirección IP local del servidor.
     */
    public String obtenerIPLocal() {
        try {
            InetAddress direccionIP = InetAddress.getLocalHost();
            return direccionIP.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Error al obtener la dirección IP local del servidor");
            return "Desconocida";
        }
    }

    /**
     * Método principal del hilo que recibe paquetes entrantes.
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
                System.err.println("Error en la recepción del paquete: problema con el socket");
            } catch (IOException e) {
                System.err.println("Error en la recepción del paquete: problema de Entrada/Salida");
            }
        }
    }

    /**
     * Detiene el servidor de juego.
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
     * @param datos Los datos del paquete.
     * @param direccionIP La dirección IP del remitente.
     * @param puerto El puerto del remitente.
     */
    private void parsePaquete(byte[] datos, InetAddress direccionIP, int puerto) {
        String mensaje = new String(datos).trim();
        TiposPaquete tipo = Paquete.buscarPaquete(mensaje.substring(0, 2));
        switch (tipo) {
            default:
            case INVALIDO:
                break;
            case ACCEDER:
                Paquete paquete = new Paquete00Acceder(datos);
                JugadorMultijugador jugador = new JugadorMultijugador(((Paquete00Acceder) paquete).getNombreUsuario(), ((Paquete00Acceder) paquete).getNombreSkin(), panelJuego, direccionIP, puerto);
                this.agregarConexion(jugador, ((Paquete00Acceder) paquete));
                break;
            case DESCONECTAR:
                paquete = new Paquete01Desconectar(datos);
                this.quitarConexion(((Paquete01Desconectar) paquete));
                break;
            case MOVER:
                paquete = new Paquete02Mover(datos);
                this.manejarMovimiento((Paquete02Mover) paquete);
                break;
            case AGREGAR_OBJETO:
                paquete = new Paquete03AgregarObjeto(datos);
                manejarAgregarObjeto((Paquete03AgregarObjeto) paquete);
                break;
            case ELIMINAR_OBJETO:
                paquete = new Paquete04EliminarObjeto(datos);
                manejarEliminacionObjeto((Paquete04EliminarObjeto) paquete);
                break;
            case FINALIZAR_JUEGO:
                panelJuego.ui.juegoFinalizado = true;
                panelJuego.ui.dibujarPantallaGameOver("Felicidades.");
                paquete = new Paquete05FinalizarJuego();
                paquete.escribirDatos(this);
                break;
        }
    }

    /**
     * Agrega una nueva conexión de jugador al servidor.
     *
     * @param jugador El jugador que se conecta.
     * @param paquete El paquete de acceso del jugador.
     */
    public void agregarConexion(JugadorMultijugador jugador, Paquete00Acceder paquete) {
        boolean conectado = false;
        for (JugadorMultijugador j : this.jugadoresConectados) {
            if (jugador.getNombreUsuario().equalsIgnoreCase(j.getNombreUsuario())) {
                if (j.direccionIP == null) {
                    j.direccionIP = jugador.direccionIP;
                }
                if (j.puerto == -1) {
                    j.puerto = jugador.puerto;
                }
                j.setDireccion(paquete.getDireccion());
                conectado = true;
            } else {
                enviarDatos(paquete.getDatos(), j.direccionIP, j.puerto);
                Paquete00Acceder paqueteExistente = new Paquete00Acceder(j.getNombreUsuario(), j.mundoX, j.mundoY, j.direccion, j.nombreSkin);
                enviarDatos(paqueteExistente.getDatos(), jugador.direccionIP, jugador.puerto);
            }
        }
        if (!conectado) {
            jugador.setDireccion(paquete.getDireccion());
            jugador.nombreSkin = paquete.getNombreSkin();
            this.jugadoresConectados.add(jugador);

            // Recorrer el arreglo de objetos del servidor, en busca de enviarle a los demás jugadores qué objetos dibujar.
            // No enviar al administrador cuando se conecta, pues, su puerto es -1 al iniciar.
            for (int numeroMapa = 0; numeroMapa < panelJuego.obj.length; numeroMapa++) {
                for (int i = 0; i < panelJuego.obj[numeroMapa].length; i++) {
                    if (!jugador.getNombreUsuario().equalsIgnoreCase(panelJuego.jugador.getNombreUsuario())) {
                        if (panelJuego.obj[numeroMapa][i] != null) {
                            if (panelJuego.obj[numeroMapa][i] instanceof Electrodomestico elect && elect.encendido) {
                                Paquete03AgregarObjeto paqueteAgregar = new Paquete03AgregarObjeto("lamparaON", panelJuego.obj[numeroMapa][i].mundoX, panelJuego.obj[numeroMapa][i].mundoY, i, numeroMapa);
                                enviarDatos(paqueteAgregar.getDatos(), jugador.direccionIP, jugador.puerto);
                            } else if (panelJuego.obj[numeroMapa][i] instanceof Electrodomestico elect && !elect.encendido) {
                                Paquete03AgregarObjeto paqueteAgregar = new Paquete03AgregarObjeto("lamparaOFF", panelJuego.obj[numeroMapa][i].mundoX, panelJuego.obj[numeroMapa][i].mundoY, i, numeroMapa);
                                enviarDatos(paqueteAgregar.getDatos(), jugador.direccionIP, jugador.puerto);
                            } else if (panelJuego.obj[numeroMapa][i] instanceof Valla valla && valla.abierto && valla.mundoX == 2544 && valla.mundoY == 2496) {
                                Paquete03AgregarObjeto paqueteAgregar = new Paquete03AgregarObjeto("cerca_2", panelJuego.obj[numeroMapa][i].mundoX, panelJuego.obj[numeroMapa][i].mundoY, i, numeroMapa);
                                enviarDatos(paqueteAgregar.getDatos(), jugador.direccionIP, jugador.puerto);
                            } else if (panelJuego.obj[numeroMapa][i] instanceof Valla valla && valla.abierto && valla.mundoX == 2592 && valla.mundoY == 2496) {
                                Paquete03AgregarObjeto paqueteAgregar = new Paquete03AgregarObjeto("cerca_7", panelJuego.obj[numeroMapa][i].mundoX, panelJuego.obj[numeroMapa][i].mundoY, i, numeroMapa);
                                enviarDatos(paqueteAgregar.getDatos(), jugador.direccionIP, jugador.puerto);
                            } else {
                                Paquete03AgregarObjeto paqueteAgregar = new Paquete03AgregarObjeto(panelJuego.obj[numeroMapa][i].nombre, panelJuego.obj[numeroMapa][i].mundoX, panelJuego.obj[numeroMapa][i].mundoY, i, numeroMapa);
                                enviarDatos(paqueteAgregar.getDatos(), jugador.direccionIP, jugador.puerto);
                            }
                        } else {
                            Paquete04EliminarObjeto paqueteEliminar = new Paquete04EliminarObjeto(i, numeroMapa);
                            enviarDatos(paqueteEliminar.getDatos(), jugador.direccionIP, jugador.puerto);
                        }
                    }
                }
            }
        }
    }

    /**
     * Quita la conexión de un jugador del servidor.
     *
     * @param paquete El paquete de desconexión del jugador.
     */
    public void quitarConexion(Paquete01Desconectar paquete) {
        this.jugadoresConectados.remove(buscarIndiceJugadorMultijugador(paquete.getNombreUsuario()));
        paquete.escribirDatos(this);
    }

    /**
     * Maneja el movimiento de los jugadores.
     *
     * @param paquete El paquete de movimiento.
     */
    private void manejarMovimiento(Paquete02Mover paquete) {
        if (buscarJugadorMultijugador(paquete.getNombreUsuario()) != null) {
            int indice = buscarIndiceJugadorMultijugador(paquete.getNombreUsuario());
            this.jugadoresConectados.get(indice).mundoX = paquete.getX();
            this.jugadoresConectados.get(indice).mundoY = paquete.getY();
            paquete.escribirDatos(this);
        } else {
            Entidad npc = panelJuego.buscarNPC(paquete.getNombreUsuario());
            if (npc != null) {
                npc.mundoX = paquete.getX();
                npc.mundoY = paquete.getY();
                paquete.escribirDatos(this);
            }
        }
    }

    /**
     * Maneja la adición de un objeto en el juego.
     *
     * @param paquete El paquete de adición de objeto.
     */
    private void manejarAgregarObjeto(Paquete03AgregarObjeto paquete) {
        int indice = paquete.getIndice();
        String nombre = paquete.getNombre();
        int x = paquete.getX();
        int y = paquete.getY();

        // Notificar a todos los clientes conectados sobre la adición del objeto
        enviarDatos_TodoslosClientes(paquete.getDatos());

        // Actualizar el estado del juego en el servidor
        panelJuego.agregarObjeto(nombre, x, y, indice, paquete.getNumeroMapa());
    }

    /**
     * Maneja la eliminación de un objeto en el juego.
     *
     * @param paquete El paquete de eliminación de objeto.
     */
    private void manejarEliminacionObjeto(Paquete04EliminarObjeto paquete) {
        int indice = paquete.getIndice();
        int numeroMapa = paquete.getNumeroMapa();

        // Notificar a todos los clientes conectados sobre la eliminación del objeto
        enviarDatos_TodoslosClientes(paquete.getDatos());

        // Actualizar el estado del juego en el servidor
        panelJuego.eliminarObjeto(indice, numeroMapa);
    }

    /**
     * Busca el índice de un jugador en la lista de jugadores conectados.
     *
     * @param nombreUsuario El nombre de usuario del jugador.
     * @return El índice del jugador.
     */
    private int buscarIndiceJugadorMultijugador(String nombreUsuario) {
        for (int i = 0; i < this.jugadoresConectados.size(); i++) {
            if (this.jugadoresConectados.get(i).getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Busca un jugador en la lista de jugadores conectados.
     *
     * @param nombreUsuario El nombre de usuario del jugador.
     * @return El jugador encontrado o null si no existe.
     */
    public JugadorMultijugador buscarJugadorMultijugador(String nombreUsuario) {
        for (JugadorMultijugador j : this.jugadoresConectados) {
            if (j.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return j;
            }
        }
        return null;
    }

    /**
     * Envía datos a través del socket a una dirección IP y puerto específicos.
     *
     * @param datos Los datos a enviar.
     * @param direccionIP La dirección IP de destino.
     * @param puerto El puerto de destino.
     */
    public void enviarDatos(byte[] datos, InetAddress direccionIP, int puerto) {
        DatagramPacket paquete = new DatagramPacket(datos, datos.length, direccionIP, puerto);
        try {
            this.socket.send(paquete);
        } catch (IOException e) {
            System.err.println("Error al enviar datos al destino " + direccionIP + ":" + puerto);
        }
    }

    /**
     * Envía datos a todos los clientes conectados.
     *
     * @param datos Los datos a enviar.
     */
    public void enviarDatos_TodoslosClientes(byte[] datos) {
        for (JugadorMultijugador j : jugadoresConectados) {
            enviarDatos(datos, j.direccionIP, j.puerto);
        }
    }
}
