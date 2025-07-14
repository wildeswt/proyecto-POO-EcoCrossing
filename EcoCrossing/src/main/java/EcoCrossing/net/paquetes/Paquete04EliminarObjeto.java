package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

/**
 * Paquete04EliminarObjeto representa un paquete utilizado para eliminar un
 * objeto del juego. Contiene información sobre el índice del objeto y el número
 * de mapa.
 */
public class Paquete04EliminarObjeto extends Paquete {

    private int indice;
    private int numeroMapa;

    /**
     * Constructor que inicializa el paquete a partir de los datos recibidos en
     * formato byte.
     *
     * @param datos Los datos en formato byte que contienen la información del
     * paquete.
     */
    public Paquete04EliminarObjeto(byte[] datos) {
        super(04);
        String[] data = leerDatos(datos).split(",");
        this.indice = Integer.parseInt(data[0]);
        this.numeroMapa = Integer.parseInt(data[1]);
    }

    /**
     * Constructor que inicializa el paquete con los parámetros específicos del
     * objeto a eliminar.
     *
     * @param indice El índice del objeto a eliminar.
     * @param numeroMapa El número de mapa donde se encuentra el objeto a
     * eliminar.
     */
    public Paquete04EliminarObjeto(int indice, int numeroMapa) {
        super(04);
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
        return ("04" + indice + "," + numeroMapa).getBytes();
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
     * Obtiene el índice del objeto que se va a eliminar.
     *
     * @return El índice del objeto a eliminar.
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Obtiene el número de mapa donde se encuentra el objeto que se va a
     * eliminar.
     *
     * @return El número de mapa donde se encuentra el objeto a eliminar.
     */
    public int getNumeroMapa() {
        return numeroMapa;
    }
}
