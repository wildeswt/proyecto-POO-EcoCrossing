package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

/**
 * Paquete05FinalizarJuego representa un paquete utilizado para finalizar el juego.
 * Contiene información básica necesaria para indicar a los clientes y al servidor
 * que el juego debe finalizarse.
 */
public class Paquete05FinalizarJuego extends Paquete {

    /**
     * Constructor de la clase Paquete05FinalizarJuego.
     * 
     */
    public Paquete05FinalizarJuego() {
        super(05); // Llama al constructor de la clase base con el tipo de paquete 05
    }

    /**
     * Obtiene los datos del paquete en formato de bytes para enviarlos.
     * En este caso, el paquete de finalización simplemente retorna el identificador "05".
     * 
     * @return Los datos del paquete en formato de bytes.
     */
    @Override
    public byte[] getDatos() {
        return ("05").getBytes(); // Retorna el identificador del tipo de paquete como bytes
    }

    /**
     * Escribe los datos del paquete para ser enviados al cliente especificado.
     * En este caso, simplemente envía los datos del paquete al cliente.
     * 
     * @param cliente El cliente del juego al cual enviar los datos.
     */
    @Override
    public void escribirDatos(ClienteJuego cliente) {
        cliente.enviarDatos(getDatos()); // Envía los datos del paquete al cliente especificado
    }

    /**
     * Escribe los datos del paquete para ser enviados a todos los clientes
     * conectados al servidor.
     * En este caso, envía los datos del paquete a todos los clientes conectados al servidor.
     * 
     * @param servidor El servidor del juego al cual enviar los datos.
     */
    @Override
    public void escribirDatos(ServidorJuego servidor) {
        servidor.enviarDatos_TodoslosClientes(getDatos()); // Envía los datos del paquete a todos los clientes conectados al servidor
    }

}
