package Entidades;

import com.mycompany.ecocrossing.PanelJuego;

/**
 * Clase NPC_4 representa un NPC (Personaje No Jugador) en el juego.
 * Este NPC ofrece una llave para comerciar con el jugador.
 */
public class NPC_4 extends Entidad {
    
    /**
     * Constructor para inicializar un objeto NPC_4.
     *
     * @param panelJuego El panel de juego asociado al NPC.
     */
    public NPC_4(PanelJuego panelJuego) {
        super(panelJuego);
        direccion = "espalda";
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
            espalda = configurar("/NPC/npc4/npc4_espalda");
            arriba1 = configurar("/NPC/npc4/npc4_espalda_caminando1");
            arriba2 = configurar("/NPC/npc4/npc4_espalda_caminando2");
            frente = configurar("/NPC/npc4/npc4_frente");
            abajo1 = configurar("/NPC/npc4/npc4_caminando1");
            abajo2 = configurar("/NPC/npc4/npc4_caminando2");
            lado1 = configurar("/NPC/npc4/npc4_Derecha");
            der1 = configurar("/NPC/npc4/npc4_Derecha_caminando1");
            der2 = configurar("/NPC/npc4/npc4_Derecha_caminando2");
            lado2 = configurar("/NPC/npc4/npc4_Izquierda");
            izq1 = configurar("/NPC/npc4/npc4_Izquierda_caminando1");
            izq2 = configurar("/NPC/npc4/npc4_Izquierda_caminando2");
        } catch (Exception e) {
            System.err.println("Error al configurar las imágenes: " + e.getMessage());
        }
    }

    /**
     * Método para configurar los diálogos del NPC.
     */
    public void setDialogos() {
        dialogos[0] = "Para ir al pueblo primero necesitas una\nllave!";
        dialogos[1] = "Solo cuesta 10 monedas!!";
    }

    /**
     * Método para agregar los objetos que el NPC ofrece para comerciar.
     */
    public void setObjetos() {
        try {
            agregarLlave();
        } catch (Exception e) {
            System.err.println("Error al agregar objetos: " + e.getMessage());
        }
    }

    /**
     * Método para gestionar el diálogo del NPC.
     * Cambia el estado del juego a estadoTradeo y asigna este NPC al UI.
     */
    @Override
    public void Hablar() {
        super.Hablar();
        panelJuego.estadoJuego = panelJuego.estadoTradeo;
        panelJuego.ui.npc = this;
    }
}
