package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

/**
 * Paquete es una clase abstracta que representa un paquete de datos enviado
 * entre el cliente y el servidor del juego. Define métodos abstractos para
 * escribir datos y obtener los datos en formato byte.
 */
public abstract class Paquete {

    /**
     * TiposPaquete enum define los diferentes tipos de paquetes utilizados en
     * el juego.
     */
    public static enum TiposPaquete {
        INVALIDO(-1), ACCEDER(00), DESCONECTAR(01), MOVER(02), AGREGAR_OBJETO(03), ELIMINAR_OBJETO(04), FINALIZAR_JUEGO(05);

        private int paqueteID;

        private TiposPaquete(int paqueteID) {
            this.paqueteID = paqueteID;
        }

        public int getID() {
            return paqueteID;
        }
    }

    public byte paqueteID;

    /**
     * Constructor de la clase Paquete.
     *
     * @param paqueteID El ID del paquete.
     */
    public Paquete(int paqueteID) {
        this.paqueteID = (byte) paqueteID;
    }

    /**
     * Método abstracto para escribir datos en el cliente.
     *
     * @param cliente El cliente del juego.
     */
    public abstract void escribirDatos(ClienteJuego cliente);

    /**
     * Método abstracto para escribir datos en el servidor.
     *
     * @param servidor El servidor del juego.
     */
    public abstract void escribirDatos(ServidorJuego servidor);

    /**
     * Lee los datos del paquete.
     *
     * @param datos Los datos en formato byte.
     * @return Los datos leídos en formato String.
     */
    public String leerDatos(byte[] datos) {
        String mensaje = new String(datos).trim();
        return mensaje.substring(2);
    }

    /**
     * Método abstracto para obtener los datos del paquete en formato byte.
     *
     * @return Los datos del paquete en formato byte.
     */
    public abstract byte[] getDatos();

    /**
     * Busca el tipo de paquete por su ID.
     *
     * @param paqueteID El ID del paquete en formato String.
     * @return El tipo de paquete.
     */
    public static TiposPaquete buscarPaquete(String paqueteID) {
        try {
            return buscarPaquete(Integer.parseInt(paqueteID));
        } catch (NumberFormatException e) {
            return TiposPaquete.INVALIDO;
        }
    }

    /**
     * Busca el tipo de paquete por su ID.
     *
     * @param ID El ID del paquete en formato int.
     * @return El tipo de paquete.
     */
    public static TiposPaquete buscarPaquete(int ID) {
        for (TiposPaquete tp : TiposPaquete.values()) {
            if (tp.getID() == ID) {
                return tp;
            }
        }
        return TiposPaquete.INVALIDO;
    }
}
