package com.mycompany.ecocrossing;

import EcoCrossing.net.paquetes.Paquete03AgregarObjeto;
import EcoCrossing.net.paquetes.Paquete05FinalizarJuego;
import Entidades.Entidad;
import Objeto.Llave;
import Objeto.SuperObjeto;
import Objeto.Tortuga;
import Objeto.TortugaPlastico;

/**
 * Clase responsable de manejar los eventos dentro del juego.
 */
public class ManejoEventos {

    PanelJuego panelJuego;
    RectanguloEvento rectanguloEvento[][][];
    int eventoPrevioX, eventoPrevioY, contador = 0;
    boolean permitirTocarEvento = false;
    public boolean misionInicial = false;
    public boolean mision2 = false;
    public boolean mision3 = false;
    public boolean misionFlyer = false;

    /**
     * Constructor de la clase ManejoEventos.
     *
     * @param panelJuego El panel del juego.
     */
    public ManejoEventos(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;

        rectanguloEvento = new RectanguloEvento[panelJuego.maximoMapas][panelJuego.maxColumnasMundo][panelJuego.maxFilasMundo];

        int mapa = 0;
        int columna = 0;
        int fila = 0;
        while (mapa < panelJuego.maximoMapas && columna < panelJuego.maxColumnasMundo && fila < panelJuego.maxFilasMundo) {
            rectanguloEvento[mapa][columna][fila] = new RectanguloEvento();
            rectanguloEvento[mapa][columna][fila].x = 23;
            rectanguloEvento[mapa][columna][fila].y = 23;
            rectanguloEvento[mapa][columna][fila].width = 2;
            rectanguloEvento[mapa][columna][fila].height = 2;
            rectanguloEvento[mapa][columna][fila].rectEventoPredeterminadoX = rectanguloEvento[mapa][columna][fila].x;
            rectanguloEvento[mapa][columna][fila].rectEventoPredeterminadoY = rectanguloEvento[mapa][columna][fila].y;
            columna++;
            if (columna == panelJuego.maxColumnasMundo) {
                columna = 0;
                fila++;

                if (fila == panelJuego.maxFilasMundo) {
                    fila = 0;
                    mapa++;
                }
            }
        }
    }

    /**
     * Verifica los eventos y sus efectos en el jugador.
     */
    public void verificarEvento() {
        // Verificar si el jugador esta a mas de un recuadro de distancia del evento anterior
        int distanciaX = Math.abs(panelJuego.jugador.mundoX - eventoPrevioX);
        int distanciaY = Math.abs(panelJuego.jugador.mundoY - eventoPrevioY);
        int distancia = Math.max(distanciaX, distanciaY);
        if (distancia > panelJuego.tamannoRecuadros) {
            permitirTocarEvento = true;
        }

        if (permitirTocarEvento) {
            //Cambio de mapa
            if (intersecta(0, 22, 10, "cualquiera")) {
                teletransportar(1, 10, 13);
            } else if (intersecta(1, 9, 14, "cualquiera") || intersecta(1, 10, 14, "cualquiera") || intersecta(1, 8, 14, "cualquiera") || intersecta(1, 11, 14, "cualquiera")) {
                teletransportar(0, 22, 10);
            } else if (intersecta(0, 70, 13, "cualquiera") || intersecta(0, 69, 13, "cualquiera")) {
                teletransportar(2, 9, 14);
            } else if (intersecta(2, 9, 14, "cualquiera") || intersecta(2, 10, 14, "cualquiera")) {
                teletransportar(0, 70, 14);
            } //Salvar a la tortuga
            else if (intersecta(0, 5, 14, "izquierda") && panelJuego.jugador.inventario.contains(TortugaPlastico.getInstance())) {
                salvarTortuga();
            } //Mision inicial
            else if (intersecta(0, 50, 34, "cualquiera")) {
                botarBasuraFuente();
            } else if (intersecta(1, 9, 11, "cualquiera")) {
                Hablar(panelJuego.npc[1][1]);
            } else if (intersecta(0, 57, 58, "arriba")) {
                misionEntregarFlyers();
                misionFlyer = true;
            } //Leer Carteles
            else if (intersecta(0, 55, 37, "derecha")) {
                Leer("ODS 15: Vidas de Ecosistemas Terrestres.\n Conservemos la naturaleza: suelos, \nbosques y vida silvertres.");
            } else if (intersecta(0, 58, 58, "derecha")) {
                Leer("ODS 11: Ciudades y Comunidades Sostenibles.\n Construyendo ciudades inclusivas \ny resilientes para todos.");
            } else if (intersecta(0, 74, 18, "derecha")) {
                Leer("ODS 13: Acción por el Clima.\n Juntos contra el cambio climatico:\n           ¡Actua ahora!");
            } else if (intersecta(0, 26, 24, "derecha")) {
                Leer("ODS 14: Vida Submarina.\n Protejamos nuestros oceanos \n     y su biodiversidad.");
            } //NPC de la playa
            else if (intersecta(0, (int) (panelJuego.npc[0][0].mundoX - 23) / 48, (int) (panelJuego.npc[0][0].mundoY - 23) / 48, "cualquiera") && panelJuego.administradorRecuadros.hayBasura()) {
                botarBasuraPlaya();
            } else if (intersecta(0, 21, 11, "cualquiera") && !mision2 && !panelJuego.administradorRecuadros.hayBasura()) {
                if (panelJuego.jugador.vida > 0) {
                    panelJuego.ui.mostrarMensaje("Mision cumplida! (+5 Monedas \n-1 Hambre)");
                }
                panelJuego.jugador.cntMonedas += 5;
                panelJuego.jugador.disminuirVida();
                mision2 = true;
            }

        }
    }

    /**
     * Verifica si el jugador intersecta con un evento específico.
     *
     * @param mapa El mapa donde ocurre el evento.
     * @param columnaEvento La columna del evento.
     * @param filaEvento La fila del evento.
     * @param direccionRequerida La dirección requerida para que ocurra el
     * evento.
     * @return true si el jugador intersecta con el evento, false en caso
     * contrario.
     */
    public boolean intersecta(int mapa, int columnaEvento, int filaEvento, String direccionRequerida) {

        boolean intersecta = false;

        if (mapa == panelJuego.mapaActual) {
            panelJuego.jugador.areaSolida.x = panelJuego.jugador.mundoX + panelJuego.jugador.areaSolida.x;
            panelJuego.jugador.areaSolida.y = panelJuego.jugador.mundoY + panelJuego.jugador.areaSolida.y;
            rectanguloEvento[mapa][columnaEvento][filaEvento].x = columnaEvento * panelJuego.tamannoRecuadros + rectanguloEvento[mapa][columnaEvento][filaEvento].x;
            rectanguloEvento[mapa][columnaEvento][filaEvento].y = filaEvento * panelJuego.tamannoRecuadros + rectanguloEvento[mapa][columnaEvento][filaEvento].y;

            if (panelJuego.jugador.areaSolida.intersects(rectanguloEvento[mapa][columnaEvento][filaEvento]) && !rectanguloEvento[mapa][columnaEvento][filaEvento].eventoRealizado) {
                if (panelJuego.jugador.direccion.contentEquals(direccionRequerida) || direccionRequerida.contentEquals("cualquiera")) {
                    intersecta = true;
                    eventoPrevioX = panelJuego.jugador.mundoX;
                    eventoPrevioY = panelJuego.jugador.mundoY;
                }
            }

            panelJuego.jugador.areaSolida.x = panelJuego.jugador.areaSolidaDefaultX;
            panelJuego.jugador.areaSolida.y = panelJuego.jugador.areaSolidaDefaultY;
            rectanguloEvento[mapa][columnaEvento][filaEvento].x = rectanguloEvento[mapa][columnaEvento][filaEvento].rectEventoPredeterminadoX;
            rectanguloEvento[mapa][columnaEvento][filaEvento].y = rectanguloEvento[mapa][columnaEvento][filaEvento].rectEventoPredeterminadoY;
        }

        return intersecta;
    }

    /**
     * Teletransporta al jugador a una ubicación específica.
     *
     * @param mapa El mapa destino.
     * @param columna La columna destino.
     * @param fila La fila destino.
     */
    public void teletransportar(int mapa, int columna, int fila) {
        panelJuego.mapaActual = mapa;
        panelJuego.jugador.mundoX = panelJuego.tamannoRecuadros * columna;
        panelJuego.jugador.mundoY = panelJuego.tamannoRecuadros * fila;
        eventoPrevioX = panelJuego.jugador.mundoX;
        eventoPrevioY = panelJuego.jugador.mundoY;
        permitirTocarEvento = false;
    }

    /**
     * Misión para salvar a la tortuga.
     */
    public void salvarTortuga() {
        panelJuego.ui.mostrarMensaje("Salva a la tortuga!");
        if (panelJuego.manejoTeclas.interacturaObjetoPresionado) {
            // Quitar a la tortuga del inventario del jugador
            panelJuego.jugador.inventario.remove(TortugaPlastico.getInstance());

            //Posicionar a la tortuga en el mar
            panelJuego.obj[0][22] = new Tortuga();
            ((Tortuga) panelJuego.obj[0][22]).cargar(panelJuego.tamannoRecuadros);
            panelJuego.obj[0][22].ubicar(4, 12, panelJuego.tamannoRecuadros);
            if (panelJuego.jugador.vida > 0) {
                panelJuego.ui.mostrarMensaje("Salvaste a la tortuga! (+5 Monedas \n-1 Hambre)");
            }
            panelJuego.jugador.cntMonedas += 5;
            panelJuego.jugador.disminuirVida();

            // Enviar paquete a los jugadores, para que agreguen el objeto en particular. 
            Paquete03AgregarObjeto paquete = new Paquete03AgregarObjeto(panelJuego.obj[0][22].nombre, 192, 576, 22, 0);
            if (panelJuego.clienteSocket != null) {
                paquete.escribirDatos(panelJuego.clienteSocket);
            }
            permitirTocarEvento = false;
        }
    }

    /**
     * Activar el evente para leer.
     *
     * @param texto texto a leer
     */
    public void Leer(String texto) {
        panelJuego.estadoJuego = panelJuego.estadoDialogo;
        panelJuego.ui.dialogoActual = texto;
        panelJuego.ui.dibujarPantallaDialogo();
        panelJuego.jugador.direccion = "frente";

    }

    /**
     * Método para hablar con NPC a través de un evento.
     *
     * @param entidad
     */
    public void Hablar(Entidad entidad) {
        if (panelJuego.manejoTeclas.enterPresionado == true) {
            panelJuego.estadoJuego = panelJuego.estadoDialogo;
            entidad.Hablar();
        }
    }

    /**
     * Misión de botar la basura alrededor de la fuente.
     */
    public void botarBasuraFuente() {
        // Que no repita sus dialogos
        if (panelJuego.npc[0][2].indiceDialogo == 4 && panelJuego.administradorRecuadros.hayBasuraAlrededorFuente()) {
            panelJuego.npc[0][2].indiceDialogo = 3;
        } // En caso de que ya haya terminado la mision
        else if (!panelJuego.administradorRecuadros.hayBasuraAlrededorFuente()) {
            if (panelJuego.npc[0][2].indiceDialogo == 3) {
                panelJuego.npc[0][2].indiceDialogo = 4;
            } else if (panelJuego.npc[0][2].indiceDialogo - 1 == 4) {
                panelJuego.npc[0][2].indiceDialogo = 5;
            }
            if (!misionInicial) {
                if (panelJuego.jugador.vida > 0) {
                    panelJuego.ui.mostrarMensaje("Mision cumplida! (+5 Monedas \n-1 Hambre)");
                }
                panelJuego.jugador.cntMonedas += 5;
                panelJuego.jugador.disminuirVida();
                misionInicial = true;
            }
            return;
        }

        // En caso de estar en proceso
        if (panelJuego.npc[0][2].indiceDialogo < 4 && contador < 4) {
            panelJuego.ui.mostrarMensaje("Habla con el npc.");
            if (panelJuego.manejoTeclas.hablarNPCPresionado) {
                contador++;
            }
        } else if (panelJuego.administradorRecuadros.hayBasuraAlrededorFuente() && contador >= 4) {
            panelJuego.ui.mostrarMensaje("Bota la basura y habla con el npc.");
        }
    }

    /**
     * Verifica si el usuario tiene la llave antes de entrar al pueblo.
     *
     * @return devuelve si tiene o no tiene la llave
     */
    public boolean verificacionLlave() {
        boolean tengoLlave = false;
        for (SuperObjeto so : panelJuego.jugador.inventario) {
            if (so instanceof Llave l) {
                if (l.nombre.equals("llave")) {
                    tengoLlave = true;
                }
            }
        }
        if (tengoLlave == false) {
            panelJuego.estadoJuego = panelJuego.estadoDialogo;
            panelJuego.ui.dialogoActual = "Guardian: No puedes\npasar no tienes\nllave";
            panelJuego.ui.dibujarPantallaDialogo();
            panelJuego.jugador.direccion = "arriba";
            panelJuego.jugador.areaSolida.y--;
            return tengoLlave;
        } else if (tengoLlave == true) {
            // Pasar con normalidad...
            return tengoLlave;
        }
        return tengoLlave;
    }

    /**
     * Activa la misión de entregar los Flyer.
     */
    public void misionEntregarFlyers() {
        if (misionFlyer == false) {
            panelJuego.estadoJuego = panelJuego.estadoDialogo;
            panelJuego.ui.dialogoActual = "Hey oye tú, ayudarías a una\nsamaritana vendiendo esto\ny regresando con las ganancias?";
            panelJuego.ui.dibujarPantallaDialogo();
            panelJuego.jugador.agregarFlyer("flyerAmarillo");
            panelJuego.jugador.agregarFlyer("flyerAzul");
            panelJuego.jugador.agregarFlyer("flyerNaranja");
            panelJuego.jugador.agregarFlyer("flyerRosado");
        }
        if (misionFlyer) {
            if (panelJuego.jugador.cntMonedas < 50) {
                panelJuego.estadoJuego = panelJuego.estadoDialogo;
                panelJuego.ui.dialogoActual = "Hey vuelve cuando tengas\n el dinero suficiente";
                panelJuego.ui.dibujarPantallaDialogo();
            }
            if (panelJuego.jugador.cntMonedas >= 50) {
                if (misionInicial && mision2 && misionFlyer && panelJuego.administradorRecuadros.todasLamparasApagadas()) {
                    panelJuego.ui.juegoFinalizado = true;
                    panelJuego.ui.dibujarPantallaGameOver("Felicidades.");
                    Paquete05FinalizarJuego paquete = new Paquete05FinalizarJuego();
                    if (panelJuego.clienteSocket != null) {
                        paquete.escribirDatos(panelJuego.clienteSocket);
                    }
                }
            }
        }

    }

    /**
     * Activa la misión de botar la basura de la playa.
     */
    public void botarBasuraPlaya() {
        panelJuego.ui.mostrarMensaje("Habla con el npc.");
        // Que no repita sus dialogos
        if (panelJuego.npc[0][0].indiceDialogo == 3 && panelJuego.administradorRecuadros.hayBasura()) {
            panelJuego.npc[0][0].indiceDialogo = 2;
        }

        //En caso de haber terminado la mision 
        if (!panelJuego.administradorRecuadros.hayBasura()) {
            if (panelJuego.jugador.vida > 0) {
                panelJuego.ui.mostrarMensaje("Mision cumplida! (+5 Monedas \n-1 Hambre)");
            }
            panelJuego.jugador.cntMonedas += 5;
            panelJuego.jugador.disminuirVida();
            misionInicial = true;
        }

    }

}
