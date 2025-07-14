package com.mycompany.ecocrossing;

import EcoCrossing.net.paquetes.Paquete03AgregarObjeto;
import Objeto.Basura;
import Entidades.NPC_1;
import Entidades.NPC_2;
import Entidades.NPC_3;
import Entidades.NPC_4;
import Entidades.NPC_5;
import Objeto.Cartel;
import Objeto.Electrodomestico;
import Objeto.Mesa;
import Objeto.Papelera;
import Objeto.TortugaPlastico;
import Objeto.Valla;
import java.util.Random;

/**
 * Clase que se encarga de inicializar y configurar los objetos y NPCs en el juego EcoCrossing.
 */
public class AssetSetter {

    PanelJuego panelJuego;

    //contador del indice 
    static int indiceObjeto = 0;

    /**
     * Constructor de la clase AssetSetter.
     * 
     * @param panelJuego El panel del juego donde se colocarán los objetos y NPCs.
     */
    public AssetSetter(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    /**
     * Configura y coloca los objetos en el juego, como basura, papeleras, tortugas, carteles, mesas y electrodomésticos.
     */
    public void setObjeto() {
        int numeroMapa = 0;
        // Ensuciar el mapa con basura
        String[] lista = {"papel", "lata", "carton", "banana"};
        ensuciar(lista, 20);

        // Cargar una papelera 
        panelJuego.obj[numeroMapa][21] = new Papelera();
        ((Papelera) panelJuego.obj[numeroMapa][21]).cargar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][21].ubicar(21, 21, panelJuego.tamannoRecuadros);

        // Cargar una tortuga
        panelJuego.obj[numeroMapa][22] = new TortugaPlastico();
        ((TortugaPlastico) panelJuego.obj[numeroMapa][22]).cargar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][22].ubicar(10, 12, panelJuego.tamannoRecuadros);
        
        // Cargar las vallas de la entrada del pueblo
        panelJuego.obj[numeroMapa][23] = new Valla("cerca_4");
        ((Valla) panelJuego.obj[numeroMapa][23]).cerrar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][23].ubicar(53, 52, panelJuego.tamannoRecuadros);
        
        panelJuego.obj[numeroMapa][24] = new Valla("cerca_4");
        ((Valla) panelJuego.obj[numeroMapa][24]).cerrar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][24].ubicar(54, 52, panelJuego.tamannoRecuadros);
        
    // Colocar carteles en el mapa
        colocarCarteles();

        // Objetos específicos para el mapa 2 (Casa)
        corpoelec();
        numeroMapa++;
        panelJuego.obj[numeroMapa][25] = new Mesa();
        ((Mesa) panelJuego.obj[numeroMapa][25]).cargar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][25].ubicar(12, 12, panelJuego.tamannoRecuadros);
    }

    /**
     * Configura y coloca los NPCs en el juego.
     */
    public void setNPC() {
        int numeroMapa = 0;
        panelJuego.npc[numeroMapa][0] = new NPC_1(panelJuego);
        panelJuego.npc[numeroMapa][0].mundoX = panelJuego.tamannoRecuadros * 25;
        panelJuego.npc[numeroMapa][0].mundoY = panelJuego.tamannoRecuadros * 10;

        panelJuego.npc[numeroMapa][2] = new NPC_2(panelJuego);
        panelJuego.npc[numeroMapa][2].mundoX = panelJuego.tamannoRecuadros * 50;
        panelJuego.npc[numeroMapa][2].mundoY = panelJuego.tamannoRecuadros * 35;

        panelJuego.npc[numeroMapa][3] = new NPC_4(panelJuego);
        panelJuego.npc[numeroMapa][3].mundoX = panelJuego.tamannoRecuadros * 52;
        panelJuego.npc[numeroMapa][3].mundoY = panelJuego.tamannoRecuadros * 51;

        panelJuego.npc[numeroMapa][4] = new NPC_5(panelJuego);
        panelJuego.npc[numeroMapa][4].mundoX = panelJuego.tamannoRecuadros * 57;
        panelJuego.npc[numeroMapa][4].mundoY = panelJuego.tamannoRecuadros * 57;

        numeroMapa++;

        panelJuego.npc[numeroMapa][1] = new NPC_3(panelJuego);
        panelJuego.npc[numeroMapa][1].mundoX = panelJuego.tamannoRecuadros * 9;
        panelJuego.npc[numeroMapa][1].mundoY = panelJuego.tamannoRecuadros * 12;
    }

    /**
     * Coloca carteles en posiciones específicas del mapa.
     */
    public void colocarCarteles() {
        int numeroMapa = 0;
        panelJuego.obj[numeroMapa][33] = new Cartel("cartel");
        ((Cartel) panelJuego.obj[numeroMapa][33]).cargar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][33].ubicar(55, 37, panelJuego.tamannoRecuadros);

        panelJuego.obj[numeroMapa][34] = new Cartel("cartel");
        ((Cartel) panelJuego.obj[numeroMapa][34]).cargar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][34].ubicar(58, 58, panelJuego.tamannoRecuadros);

        panelJuego.obj[numeroMapa][35] = new Cartel("cartel");
        ((Cartel) panelJuego.obj[numeroMapa][35]).cargar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][35].ubicar(74, 18, panelJuego.tamannoRecuadros);

        panelJuego.obj[numeroMapa][36] = new Cartel("cartel");
        ((Cartel) panelJuego.obj[numeroMapa][36]).cargar(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][36].ubicar(26, 24, panelJuego.tamannoRecuadros);
    }

    /**
     * Coloca electrodomésticos en posiciones específicas del mapa.
     */
    public void corpoelec() {
        int numeroMapa = 2;
        panelJuego.obj[numeroMapa][0] = new Electrodomestico("lampara");
        ((Electrodomestico) panelJuego.obj[numeroMapa][0]).encender(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][0].ubicar(7, 9, panelJuego.tamannoRecuadros);

        panelJuego.obj[numeroMapa][1] = new Electrodomestico("lampara");
        ((Electrodomestico) panelJuego.obj[numeroMapa][1]).encender(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][1].ubicar(9, 9, panelJuego.tamannoRecuadros);

        panelJuego.obj[numeroMapa][2] = new Electrodomestico("microondas");
        ((Electrodomestico) panelJuego.obj[numeroMapa][2]).encender(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][2].ubicar(12, 9, panelJuego.tamannoRecuadros);

        panelJuego.obj[numeroMapa][3] = new Electrodomestico("lampara");
        ((Electrodomestico) panelJuego.obj[numeroMapa][3]).encender(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][3].ubicar(8, 13, panelJuego.tamannoRecuadros);

        panelJuego.obj[numeroMapa][4] = new Electrodomestico("lampara");
        ((Electrodomestico) panelJuego.obj[numeroMapa][4]).encender(panelJuego.tamannoRecuadros);
        panelJuego.obj[numeroMapa][4].ubicar(11, 13, panelJuego.tamannoRecuadros);
    }

    /**
     * Coloca basura en posiciones específicas del mapa y envía un paquete de datos para informar a los jugadores.
     * 
     * @param lista Lista de tipos de basura disponibles.
     * @param cantidad Cantidad de basura a colocar en el mapa.
     */
    public void ensuciar(String[] lista, int cantidad) {
        int numeroMapa = 0;
        int posicionesX[] = {52, 52, 52, 56, 56, 56, 53, 54, 55, 53, 54, 55};
        int posicionesY[] = {32, 33, 34, 33, 34, 32, 31, 31, 31, 35, 35, 35};
        Random random = new Random();

        // Cargar basura alrededor de la fuente 
        for (int i = 0; i < posicionesX.length; i++) {
            int j = random.nextInt(lista.length);
            panelJuego.obj[numeroMapa][indiceObjeto] = new Basura(lista[j]);
            ((Basura) panelJuego.obj[numeroMapa][indiceObjeto]).cargar(panelJuego.tamannoRecuadros);
            panelJuego.obj[numeroMapa][indiceObjeto].ubicar(posicionesX[i], posicionesY[i], panelJuego.tamannoRecuadros);
            indiceObjeto++;
        }

        // Colocar basura en posiciones aleatorias del mapa
        for (int i = 0; i < cantidad; i++) {
            int randomX, randomY;
            boolean colision;

            // Asegurarse de que la posición generada no tenga colisión
            do {
                randomX = random.nextInt(15, 40);
                randomY = random.nextInt(7, 26);
                colision = panelJuego.administradorRecuadros.tieneColision(randomX, randomY, numeroMapa);
            } while (colision);

            int j = random.nextInt(lista.length);

            // Cargar el objeto de basura aleatorio 
            panelJuego.obj[numeroMapa][indiceObjeto] = new Basura(lista[j]);
            ((Basura) panelJuego.obj[numeroMapa][indiceObjeto]).cargar(panelJuego.tamannoRecuadros);
            panelJuego.obj[numeroMapa][indiceObjeto].ubicar(randomX, randomY, panelJuego.tamannoRecuadros);

            // Enviar paquete a los jugadores para agregar el objeto en particular.
            Paquete03AgregarObjeto paquete = new Paquete03AgregarObjeto(lista[j], randomX, randomY, indiceObjeto, numeroMapa);
            if (panelJuego.clienteSocket != null) {
                paquete.escribirDatos(panelJuego.clienteSocket);
            }

            indiceObjeto++;
        }
    }
}
