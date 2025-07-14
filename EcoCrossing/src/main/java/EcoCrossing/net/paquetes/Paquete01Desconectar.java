package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

/**
 * Paquete01Desconectar es una clase que representa el paquete de desconexión de
 * un jugador del juego.
 */
public class Paquete01Desconectar extends Paquete {

    private String nombreUsuario;

    /**
     * Constructor que inicializa el paquete a partir de los datos recibidos.
     *
     * @param datos Los datos en formato byte que contienen el nombre de usuario
     * del jugador.
     */
    public Paquete01Desconectar(byte[] datos) {
        super(01);
        this.nombreUsuario = leerDatos(datos);
    }

    /**
     * Constructor que inicializa el paquete con el nombre de usuario del
     * jugador.
     *
     * @param nombreUsuario El nombre de usuario del jugador.
     */
    public Paquete01Desconectar(String nombreUsuario) {
        super(01);
        this.nombreUsuario = nombreUsuario;
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
        return ("01" + this.nombreUsuario).getBytes();
    }

    /**
     * Obtiene el nombre de usuario del jugador que se está desconectando.
     *
     * @return El nombre de usuario del jugador.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
