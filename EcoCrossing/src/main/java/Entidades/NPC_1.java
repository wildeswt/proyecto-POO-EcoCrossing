package Entidades;

import EcoCrossing.net.paquetes.Paquete02Mover;
import com.mycompany.ecocrossing.PanelJuego;
import java.util.Random;

/**
 * Clase NPC_1 representa un NPC (Personaje No Jugador) en el juego.
 * Este NPC tiene diálogos predefinidos y se mueve de forma aleatoria.
 */
public class NPC_1 extends Entidad {
    
    /**
     * Constructor para inicializar un objeto NPC_1.
     *
     * @param panelJuego El panel de juego asociado al NPC.
     */
    public NPC_1(PanelJuego panelJuego) {
        super(panelJuego);
        direccion = "frente";
        velocidad = 1;
        ObtenerImagen();
        setDialogos();
    }

    /**
     * Método para obtener las imágenes del NPC y configurarlas.
     */
    public void ObtenerImagen() {
        espalda = configurar("/NPC/npc1_espalda");
        arriba1 = configurar("/NPC/npc1_espalda_caminando1");
        arriba2 = configurar("/NPC/npc1_espalda_caminando2");
        frente = configurar("/NPC/npc1_frente");
        abajo1 = configurar("/NPC/npc1_caminando1");
        abajo2 = configurar("/NPC/npc1_caminando2");
        lado1 = configurar("/NPC/npc1_Derecha");
        der1 = configurar("/NPC/npc1_Derecha_caminando1");  
        der2 = configurar("/NPC/npc1_Derecha_caminando2");
        lado2 = configurar("/NPC/npc1_Izquierda");
        izq1 = configurar("/NPC/npc1_Izquierda_caminando1");
        izq2 = configurar("/NPC/npc1_Izquierda_caminando2"); 
    }

    /**
     * Método para configurar los diálogos del NPC.
     */
    public void setDialogos() {
        dialogos[0] = "Bienvenido a la playa!";
        dialogos[1] = "Vaya cantidad de basura hay por \nestos lares...";
        dialogos[2] = "Puedes recogerla por mi?";
        dialogos[3] = "Muchas gracias! Recuerda usar la papelera y \nvisitar la tienda.";
        dialogos[4] = "Venden comida deliciosa...";
    }

    /**
     * Método para establecer la acción del NPC.
     * El NPC se mueve en una dirección aleatoria cada cierto tiempo.
     */
    @Override
    public void setAccion() {
        contAccion++;
        if (contAccion == 120) {
            Random random = new Random();
            int numR = random.nextInt(100) + 1;  // Genera un número del 1 al 100
            if (numR <= 25)
                direccion = "arriba";
            else if (numR <= 50)
                direccion = "abajo";
            else if (numR <= 75)
                direccion = "derecha";
            else
                direccion = "izquierda";
            contAccion = 0;

            // Enviar el movimiento del NPC a todos los clientes conectados
            Paquete02Mover paqueteMovimientoNPC = new Paquete02Mover("Entidades.NPC_1", this.mundoX, this.mundoY, this.spriteNum, this.direccion);
            if(panelJuego.clienteSocket != null) {
                paqueteMovimientoNPC.escribirDatos(panelJuego.clienteSocket);
            }
        }
    }
}
