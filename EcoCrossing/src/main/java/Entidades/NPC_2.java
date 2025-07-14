package Entidades;

import com.mycompany.ecocrossing.PanelJuego;

/**
 * Clase NPC_2 representa un NPC (Personaje No Jugador) en el juego.
 * Este NPC tiene diálogos predefinidos y proporciona misiones al jugador.
 */
public class NPC_2 extends Entidad {
    
    /**
     * Constructor para inicializar un objeto NPC_2.
     *
     * @param panelJuego El panel de juego asociado al NPC.
     */
    public NPC_2(PanelJuego panelJuego) {
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
        espalda = configurar("/NPC/npc2/npc2_espalda");
        arriba1 = configurar("/NPC/npc2/npc2_espalda_caminando1");
        arriba2 = configurar("/NPC/npc2/npc2_espalda_caminando2");
        frente = configurar("/NPC/npc2/npc2_frente");
        abajo1 = configurar("/NPC/npc2/npc2_caminando1");
        abajo2 = configurar("/NPC/npc2/npc2_caminando2");
        lado1 = configurar("/NPC/npc2/npc2_Derecha");
        der1 = configurar("/NPC/npc2/npc2_Derecha_caminando1");  
        der2 = configurar("/NPC/npc2/npc2_Derecha_caminando2");
        lado2 = configurar("/NPC/npc2/npc2_Izquierda");
        izq1 = configurar("/NPC/npc2/npc2_Izquierda_caminando1");
        izq2 = configurar("/NPC/npc2/npc2_Izquierda_caminando2"); 
    }

    /**
     * Método para configurar los diálogos del NPC.
     */
    public void setDialogos() {
        dialogos[0] = "Hola bachiller! \nBienvenido a esta aventura.";
        dialogos[1] = "Cuidar el planeta es nuestra prioridad.";
        dialogos[2] = "Por lo tanto, debes realizar \nuna serie de misiones";
        dialogos[3] = "Empieza por botar la basura alrededor de \nla fuente. Usa 'R' para recogerla.";
        dialogos[4] = "Felicitaciones! Puedes ir a la playa \na desecharla";
        dialogos[5] = "Camina al noroeste y la encontraras.";
    }
}
