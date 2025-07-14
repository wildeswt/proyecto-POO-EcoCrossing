package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

/**
 * Paquete02Mover es una clase que representa el paquete de movimiento de un
 * jugador o entidad en el juego.
 */
public class Paquete02Mover extends Paquete {

    private String nombreUsuario;
    private int x, y;
    private int spriteNum;
    private String direccion;

    /**
     * Constructor que inicializa el paquete a partir de los datos recibidos.
     *
     * @param datos Los datos en formato byte que contienen la información del
     * movimiento.
     */
    public Paquete02Mover(byte[] datos) {
        super(02);
        String[] arregloDatos = leerDatos(datos).split(",");
        this.nombreUsuario = arregloDatos[0];
        this.x = Integer.parseInt(arregloDatos[1]);
        this.y = Integer.parseInt(arregloDatos[2]);
        this.spriteNum = Integer.parseInt(arregloDatos[3]);
        this.direccion = arregloDatos[4];
    }

    /**
     * Constructor que inicializa el paquete con los datos del jugador o
     * entidad.
     *
     * @param nombreUsuario El nombre de usuario del jugador o entidad.
     * @param x La posición x.
     * @param y La posición y.
     * @param spriteNum El número de sprite.
     * @param direccion La dirección.
     */
    public Paquete02Mover(String nombreUsuario, int x, int y, int spriteNum, String direccion) {
        super(02);
        this.nombreUsuario = nombreUsuario;
        this.x = x;
        this.y = y;
        this.spriteNum = spriteNum;
        this.direccion = direccion;
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
        return ("02" + this.nombreUsuario + "," + this.x + "," + this.y + "," + this.spriteNum + "," + this.direccion).getBytes();
    }

    /**
     * Obtiene el nombre de usuario del jugador o entidad que se está moviendo.
     *
     * @return El nombre de usuario del jugador o entidad.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Obtiene la posición x del jugador o entidad.
     *
     * @return La posición x del jugador o entidad.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la posición y del jugador o entidad.
     *
     * @return La posición y del jugador o entidad.
     */
    public int getY() {
        return y;
    }

    /**
     * Obtiene el número de sprite del jugador o entidad.
     *
     * @return El número de sprite del jugador o entidad.
     */
    public int getSpriteNum() {
        return spriteNum;
    }

    /**
     * Obtiene la dirección del movimiento del jugador o entidad.
     *
     * @return La dirección del movimiento del jugador o entidad.
     */
    public String getDireccion() {
        return direccion;
    }
}
