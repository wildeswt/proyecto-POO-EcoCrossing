package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

/**
 * Paquete03AgregarObjeto representa un paquete utilizado para agregar un objeto
 * al juego. Contiene información sobre el nombre del objeto, sus coordenadas en
 * el mapa, el índice y el número de mapa.
 */
public class Paquete03AgregarObjeto extends Paquete {

    private String nombre;
    private int x, y, indice, numeroMapa;

    /**
     * Constructor que inicializa el paquete a partir de los datos recibidos en
     * formato byte.
     *
     * @param datos Los datos en formato byte que contienen la información del
     * paquete.
     */
    public Paquete03AgregarObjeto(byte[] datos) {
        super(03);
        String[] data = leerDatos(datos).split(",");
        this.nombre = data[0];
        this.x = Integer.parseInt(data[1]);
        this.y = Integer.parseInt(data[2]);
        this.indice = Integer.parseInt(data[3]);
        this.numeroMapa = Integer.parseInt(data[4]);
    }

    /**
     * Constructor que inicializa el paquete con los parámetros específicos del
     * objeto.
     *
     * @param nombre El nombre del objeto a agregar.
     * @param x La coordenada x donde se ubicará el objeto.
     * @param y La coordenada y donde se ubicará el objeto.
     * @param indice El índice del objeto.
     * @param numeroMapa El número de mapa donde se agrega el objeto.
     */
    public Paquete03AgregarObjeto(String nombre, int x, int y, int indice, int numeroMapa) {
        super(03);
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.indice = indice;
        this.numeroMapa = numeroMapa;
    }

    /**
     * Obtiene los datos del paquete en formato de bytes para enviarlos.
     *
     * @return Los datos del paquete en formato de bytes.
     */
    @Override
    public byte[] getDatos() {
        return ("03" + nombre + "," + x + "," + y + "," + indice + "," + numeroMapa).getBytes();
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
     * Obtiene el nombre del objeto que se va a agregar.
     *
     * @return El nombre del objeto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la coordenada x donde se ubicará el objeto.
     *
     * @return La coordenada x del objeto.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada y donde se ubicará el objeto.
     *
     * @return La coordenada y del objeto.
     */
    public int getY() {
        return y;
    }

    /**
     * Obtiene el índice del objeto que se va a agregar.
     *
     * @return El índice del objeto.
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Obtiene el número de mapa donde se va a agregar el objeto.
     *
     * @return El número de mapa.
     */
    public int getNumeroMapa() {
        return numeroMapa;
    }
}
