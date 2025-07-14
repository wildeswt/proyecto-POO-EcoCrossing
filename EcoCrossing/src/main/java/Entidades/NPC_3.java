package Entidades;

import com.mycompany.ecocrossing.PanelJuego;

/**
 * Clase NPC_3 representa un NPC (Personaje No Jugador) en el juego.
 * Este NPC ofrece objetos para comerciar con el jugador.
 * 
 * @autor Maria Sandoval
 */
public class NPC_3 extends Entidad {

    /**
     * Constructor para inicializar un objeto NPC_3.
     *
     * @param panelJuego El panel de juego asociado al NPC.
     */
    public NPC_3(PanelJuego panelJuego) {
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
        espalda = configurar("/NPC/npc3/npc3_espalda");
        arriba1 = configurar("/NPC/npc3/npc3_espalda_caminando1");
        arriba2 = configurar("/NPC/npc3/npc3_espalda_caminando2");
        frente = configurar("/NPC/npc3/npc3_frente");
        abajo1 = configurar("/NPC/npc3/npc3_caminando1");
        abajo2 = configurar("/NPC/npc3/npc3_caminando2");
        lado1 = configurar("/NPC/npc3/npc3_Derecha");
        der1 = configurar("/NPC/npc3/npc3_Derecha_caminando1");
        der2 = configurar("/NPC/npc3/npc3_Derecha_caminando2");
        lado2 = configurar("/NPC/npc3/npc3_Izquierda");
        izq1 = configurar("/NPC/npc3/npc3_Izquierda_caminando1");
        izq2 = configurar("/NPC/npc3/npc3_Izquierda_caminando2");
    }

    /**
     * Método para configurar los diálogos del NPC.
     */
    public void setDialogos() {
        dialogos[0] = "Vamos a tradear...";
    }

    /**
     * Método para agregar los objetos que el NPC ofrece para comerciar.
     */
    public void setObjetos() {
        try {
            agregarComida("helado");
            agregarComida("pizza");
            agregarComida("ponque");
            agregarComida("galleta");
            agregarComida("salchicha");
            agregarComida("maiz");
            agregarComida("leche");
            agregarComida("queso");
            agregarComida("dona");
            agregarComida("cafe");
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
