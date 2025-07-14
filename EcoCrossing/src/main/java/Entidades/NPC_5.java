package Entidades;

import com.mycompany.ecocrossing.PanelJuego;

/**
 * Clase NPC_5 representa un NPC (Personaje No Jugador) en el juego.
 * Este NPC tiene un diálogo sencillo y no ofrece objetos.
 */
public class NPC_5 extends Entidad {

    /**
     * Constructor para inicializar un objeto NPC_5.
     *
     * @param panelJuego El panel de juego asociado al NPC.
     */
    public NPC_5(PanelJuego panelJuego) {
        super(panelJuego);
        direccion = "frente";
        velocidad = 1;
        ObtenerImagen();
        setDialogos();
        setObjetos();
    }

    /**
     * Método para obtener las imágenes del NPC y configurarlas.
     */
    public void ObtenerImagen() {
        try {
            espalda = configurar("/NPC/npc5/npc5_espalda");
            arriba1 = configurar("/NPC/npc5/npc5_espalda_caminando1");
            arriba2 = configurar("/NPC/npc5/npc5_espalda_caminando2");
            frente = configurar("/NPC/npc5/npc5_frente");
            abajo1 = configurar("/NPC/npc5/npc5_caminando1");
            abajo2 = configurar("/NPC/npc5/npc5_caminando2");
            lado1 = configurar("/NPC/npc5/npc5_Derecha");
            der1 = configurar("/NPC/npc5/npc5_Derecha_caminando1");
            der2 = configurar("/NPC/npc5/npc5_Derecha_caminando2");
            lado2 = configurar("/NPC/npc5/npc5_Izquierda");
            izq1 = configurar("/NPC/npc5/npc5_Izquierda_caminando1");
            izq2 = configurar("/NPC/npc5/npc5_Izquierda_caminando2");
        } catch (Exception e) {
            System.err.println("Error al configurar las imágenes: " + e.getMessage());
        }
    }

    /**
     * Método para configurar los diálogos del NPC.
     */
    public void setDialogos() {
        dialogos[0] = "Hace un buen día no?";
    }

    /**
     * Método para agregar los objetos que el NPC ofrece para comerciar.
     * En este caso, el NPC no ofrece objetos.
     */
    public void setObjetos() {
        // No se agregan objetos
    }
}
