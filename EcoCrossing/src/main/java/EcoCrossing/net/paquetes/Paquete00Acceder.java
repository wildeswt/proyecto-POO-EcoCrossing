package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

/**
 * Paquete00Acceder es una clase que representa el paquete de acceso al juego.
 * Contiene información sobre el jugador que accede, como su nombre de usuario,
 * posición y skin.
 */
public class Paquete00Acceder extends Paquete {

    private String nombreUsuario, direccion, nombreSkin;
    private int x, y;

    /**
     * Constructor que inicializa el paquete a partir de los datos recibidos.
     *
     * @param datos Los datos en formato byte que contienen la información de
     * acceso.
     */
    public Paquete00Acceder(byte[] datos) {
        super(00);
        String[] arregloDatos = leerDatos(datos).split(",");
        this.nombreUsuario = arregloDatos[0];
        this.x = Integer.parseInt(arregloDatos[1]);
        this.y = Integer.parseInt(arregloDatos[2]);
        this.direccion = arregloDatos[3]; // Agregar dirección desde los datos
        this.nombreSkin = arregloDatos[4];
    }

    /**
     * Constructor que inicializa el paquete con los datos del jugador.
     *
     * @param nombreUsuario El nombre de usuario del jugador.
     * @param x La posición x del jugador.
     * @param y La posición y del jugador.
     * @param direccion La dirección del jugador.
     * @param nombreSkin El nombre del skin del jugador.
     */
    public Paquete00Acceder(String nombreUsuario, int x, int y, String direccion, String nombreSkin) {
        super(00);
        this.nombreUsuario = nombreUsuario;
        this.x = x;
        this.y = y;
        this.direccion = direccion;
        this.nombreSkin = nombreSkin;
    }

    /**
     * Escribe los datos del paquete para ser enviados al cliente especificado.
     *
     * @param cliente El cliente del juego al cual enviar los datos.
     */
    @Override
    public void escribirDatos(ClienteJuego cliente) {
        cliente.enviarDatos(getDatos());
    }

    /**
     * Escribe los datos del paquete para ser enviados a todos los clientes
     * conectados al servidor.
     *
     * @param servidor El servidor del juego al cual enviar los datos.
     */
    @Override
    public void escribirDatos(ServidorJuego servidor) {
        servidor.enviarDatos_TodoslosClientes(getDatos());
    }

    /**
     * Obtiene los datos del paquete en formato de bytes para enviarlos.
     *
     * @return Los datos del paquete en formato de bytes.
     */
    @Override
    public byte[] getDatos() {
        return ("00" + this.nombreUsuario + "," + this.x + "," + this.y + "," + this.direccion + "," + this.nombreSkin).getBytes();
    }

    /**
     * Obtiene el nombre de usuario del jugador que está accediendo.
     *
     * @return El nombre de usuario del jugador.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Obtiene la posición x del jugador que está accediendo.
     *
     * @return La posición x del jugador.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la posición y del jugador que está accediendo.
     *
     * @return La posición y del jugador.
     */
    public int getY() {
        return y;
    }

    /**
     * Obtiene el nombre del skin del jugador que está accediendo.
     *
     * @return El nombre del skin del jugador.
     */
    public String getNombreSkin() {
        return nombreSkin;
    }

    /**
     * Obtiene la dirección del jugador que está accediendo.
     *
     * @return La dirección del jugador.
     */
    public String getDireccion() {
        return direccion;
    }
}
