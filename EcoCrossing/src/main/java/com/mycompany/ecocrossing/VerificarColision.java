package com.mycompany.ecocrossing;

import Entidades.Entidad;

public class VerificarColision {

    PanelJuego panelJuego;

    /**
     * Constructor de la clase VerificarColision.
     *
     * @param panelJuego Panel de juego asociado para la gestión de colisiones.
     */
    public VerificarColision(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    /**
     * Verifica la colisión de una entidad con los recuadros del mapa.
     *
     * @param entidad Entidad cuya colisión se va a verificar.
     */
    public void VerificarRecuadro(Entidad entidad) {
        // Calcular posiciones de la entidad respecto al mundo y sus recuadros
        int entidadIzqMundoX = entidad.mundoX + entidad.areaSolida.x;
        int entidadDerMundoX = entidad.mundoX + entidad.areaSolida.x + entidad.areaSolida.width;
        int entidadArribaMundoY = entidad.mundoY + entidad.areaSolida.y;
        int entidadAbajoMundoY = entidad.mundoY + entidad.areaSolida.y + entidad.areaSolida.height;

        // Calcular los índices de las columnas y filas de los recuadros en el mapa
        int entidadIzqColumna = entidadIzqMundoX / panelJuego.tamannoRecuadros;
        int entidadDerColumna = entidadDerMundoX / panelJuego.tamannoRecuadros;
        int entidadArribaFila = entidadArribaMundoY / panelJuego.tamannoRecuadros;
        int entidadAbajoFila = entidadAbajoMundoY / panelJuego.tamannoRecuadros;
        int recuadro1, recuadro2;

        // Determinar el tipo de colisión según la dirección de la entidad
        switch (entidad.direccion) {
            case "arriba":
                entidadArribaFila = (entidadArribaMundoY - entidad.velocidad) / panelJuego.tamannoRecuadros;
                recuadro1 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadIzqColumna][entidadArribaFila];
                recuadro2 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadDerColumna][entidadArribaFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision) {
                    entidad.colisionActivada = true;
                }
                break;
            case "abajo":
                entidadAbajoFila = (entidadAbajoMundoY + entidad.velocidad) / panelJuego.tamannoRecuadros;
                recuadro1 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadIzqColumna][entidadAbajoFila];
                recuadro2 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadDerColumna][entidadAbajoFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision) {
                    entidad.colisionActivada = true;
                }
                break;
            case "derecha":
                entidadDerColumna = (entidadDerMundoX + entidad.velocidad) / panelJuego.tamannoRecuadros;
                recuadro1 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadDerColumna][entidadArribaFila];
                recuadro2 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadDerColumna][entidadAbajoFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision) {
                    entidad.colisionActivada = true;
                }
                break;
            case "izquierda":
                entidadIzqColumna = (entidadIzqMundoX - entidad.velocidad) / panelJuego.tamannoRecuadros;
                recuadro1 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadIzqColumna][entidadArribaFila];
                recuadro2 = panelJuego.administradorRecuadros.numeroRecuadroMapa[panelJuego.mapaActual][entidadIzqColumna][entidadAbajoFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision) {
                    entidad.colisionActivada = true;
                }
                break;
        }

    }

    /**
     * Verifica la colisión de una entidad con otros objetos en el juego.
     *
     * @param entidad Entidad cuya colisión se va a verificar.
     * @param jugador Indica si la entidad es el jugador principal.
     * @return Índice del objeto con el que colisiona la entidad, o 999 si no
     * hay colisión.
     */
    public int VerificarObjeto(Entidad entidad, boolean jugador) {
        int indice = 999;

        // Recorrer todos los objetos en el juego
        for (int i = 0; i < panelJuego.obj[1].length; i++) {
            if (panelJuego.obj[panelJuego.mapaActual][i] != null) {
                // Obtener la posición del área sólida de la entidad y del objeto
                entidad.areaSolida.x = entidad.mundoX + entidad.areaSolida.x;
                entidad.areaSolida.y = entidad.mundoY + entidad.areaSolida.y;
                panelJuego.obj[panelJuego.mapaActual][i].areaSolida.x = panelJuego.obj[panelJuego.mapaActual][i].mundoX + panelJuego.obj[panelJuego.mapaActual][i].areaSolida.x;
                panelJuego.obj[panelJuego.mapaActual][i].areaSolida.y = panelJuego.obj[panelJuego.mapaActual][i].mundoY + panelJuego.obj[panelJuego.mapaActual][i].areaSolida.y;

                // Verificar colisión según la dirección de la entidad
                switch (entidad.direccion) {
                    case "arriba":
                        entidad.areaSolida.y -= entidad.velocidad;
                        if (entidad.areaSolida.intersects(panelJuego.obj[panelJuego.mapaActual][i].areaSolida)) {
                            if (panelJuego.obj[panelJuego.mapaActual][i].colision) {
                                entidad.colisionActivada = true;
                            }
                            if (jugador) {
                                indice = i;
                            }
                        }
                        break;
                    case "abajo":
                        entidad.areaSolida.y += entidad.velocidad;
                        if (entidad.areaSolida.intersects(panelJuego.obj[panelJuego.mapaActual][i].areaSolida)) {
                            if (panelJuego.obj[panelJuego.mapaActual][i].colision) {
                                entidad.colisionActivada = true;
                            }
                            if (jugador) {
                                indice = i;
                            }
                        }
                        break;
                    case "izquierda":
                        entidad.areaSolida.x -= entidad.velocidad;
                        if (entidad.areaSolida.intersects(panelJuego.obj[panelJuego.mapaActual][i].areaSolida)) {
                            if (panelJuego.obj[panelJuego.mapaActual][i].colision) {
                                entidad.colisionActivada = true;
                            }
                            if (jugador) {
                                indice = i;
                            }
                        }
                        break;
                    case "derecha":
                        entidad.areaSolida.x += entidad.velocidad;
                        if (entidad.areaSolida.intersects(panelJuego.obj[panelJuego.mapaActual][i].areaSolida)) {
                            if (panelJuego.obj[panelJuego.mapaActual][i].colision) {
                                entidad.colisionActivada = true;
                            }
                            if (jugador) {
                                indice = i;
                            }
                        }
                        break;
                }
                // Restaurar las posiciones originales del área sólida de la entidad y del objeto
                entidad.areaSolida.x = entidad.areaSolidaDefaultX;
                entidad.areaSolida.y = entidad.areaSolidaDefaultY;
                panelJuego.obj[panelJuego.mapaActual][i].areaSolida.x = panelJuego.obj[panelJuego.mapaActual][i].areaSolidaDefaultX;
                panelJuego.obj[panelJuego.mapaActual][i].areaSolida.y = panelJuego.obj[panelJuego.mapaActual][i].areaSolidaDefaultY;
            }
        }
        return indice;
    }

    /**
     * Verifica la colisión de una entidad con otras entidades en el juego.
     *
     * @param entidad Entidad cuya colisión se va a verificar.
     * @param entidades Matriz de entidades en el juego.
     * @return Índice de la entidad con la que colisiona la entidad dada, o 999
     * si no hay colisión.
     */
    public int VerificarEntidad(Entidad entidad, Entidad[][] entidades) {
        int indice = 999;
        // Recorrer todos los objetos en el juego
        for (int i = 0; i < entidades[1].length; i++) {
            if (entidades[panelJuego.mapaActual][i] != null) {
                entidad.areaSolida.x = entidad.mundoX + entidad.areaSolida.x;
                entidad.areaSolida.y = entidad.mundoY + entidad.areaSolida.y;
                entidades[panelJuego.mapaActual][i].areaSolida.x = entidades[panelJuego.mapaActual][i].mundoX + entidades[panelJuego.mapaActual][i].areaSolida.x;
                entidades[panelJuego.mapaActual][i].areaSolida.y = entidades[panelJuego.mapaActual][i].mundoY + entidades[panelJuego.mapaActual][i].areaSolida.y;

                switch (entidad.direccion) {
                    case "arriba":
                        entidad.areaSolida.y -= entidad.velocidad;
                        if (entidad.areaSolida.intersects(entidades[panelJuego.mapaActual][i].areaSolida)) {
                            entidad.colisionActivada = true;
                            indice = i;
                        }
                        break;
                    case "abajo":
                        entidad.areaSolida.y += entidad.velocidad;
                        if (entidad.areaSolida.intersects(entidades[panelJuego.mapaActual][i].areaSolida)) {
                            entidad.colisionActivada = true;
                            indice = i;
                        }
                        break;
                    case "izquierda":
                        entidad.areaSolida.x -= entidad.velocidad;
                        if (entidad.areaSolida.intersects(entidades[panelJuego.mapaActual][i].areaSolida)) {
                            entidad.colisionActivada = true;
                            indice = i;
                        }
                        break;
                    case "derecha":
                        entidad.areaSolida.x += entidad.velocidad;
                        if (entidad.areaSolida.intersects(entidades[panelJuego.mapaActual][i].areaSolida)) {
                            entidad.colisionActivada = true;
                            indice = i;
                        }
                        break;
                }
                entidad.areaSolida.x = entidad.areaSolidaDefaultX;
                entidad.areaSolida.y = entidad.areaSolidaDefaultY;
                entidades[panelJuego.mapaActual][i].areaSolida.x = entidades[panelJuego.mapaActual][i].areaSolidaDefaultX;
                entidades[panelJuego.mapaActual][i].areaSolida.y = entidades[panelJuego.mapaActual][i].areaSolidaDefaultY;
            }
        }
        return indice;
    }

    /**
     * Verifica la colisión de una entidad con el jugador en el juego.
     *
     * @param entidad Entidad cuya colisión con el jugador se va a verificar.
     */
    public void VerificarJugador(Entidad entidad) {
        entidad.areaSolida.x = entidad.mundoX + entidad.areaSolida.x;
        entidad.areaSolida.y = entidad.mundoY + entidad.areaSolida.y;
        panelJuego.jugador.areaSolida.x = panelJuego.jugador.mundoX + panelJuego.jugador.areaSolida.x;
        panelJuego.jugador.areaSolida.y = panelJuego.jugador.mundoY + panelJuego.jugador.areaSolida.y;

        switch (entidad.direccion) {
            case "arriba":
                entidad.areaSolida.y -= entidad.velocidad;
                if (entidad.areaSolida.intersects(panelJuego.jugador.areaSolida)) {
                    entidad.colisionActivada = true;
                }
                break;
            case "abajo":
                entidad.areaSolida.y += entidad.velocidad;
                if (entidad.areaSolida.intersects(panelJuego.jugador.areaSolida)) {
                    entidad.colisionActivada = true;
                }
                break;
            case "izquierda":
                entidad.areaSolida.x -= entidad.velocidad;
                if (entidad.areaSolida.intersects(panelJuego.jugador.areaSolida)) {
                    entidad.colisionActivada = true;
                }
                break;
            case "derecha":
                entidad.areaSolida.x += entidad.velocidad;
                if (entidad.areaSolida.intersects(panelJuego.jugador.areaSolida)) {
                    entidad.colisionActivada = true;
                }
                break;
        }
        entidad.areaSolida.x = entidad.areaSolidaDefaultX;
        entidad.areaSolida.y = entidad.areaSolidaDefaultY;
        panelJuego.jugador.areaSolida.x = panelJuego.jugador.areaSolidaDefaultX;
        panelJuego.jugador.areaSolida.y = panelJuego.jugador.areaSolidaDefaultY;
    }
}
