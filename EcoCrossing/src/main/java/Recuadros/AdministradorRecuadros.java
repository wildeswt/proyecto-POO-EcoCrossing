package Recuadros;

import Objeto.Basura;
import Objeto.Electrodomestico;
import Objeto.SuperObjeto;
import com.mycompany.ecocrossing.EscaladorImagen;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

/**
 * Clase que administra los recuadros del juego, incluyendo la carga de mapas y
 * la configuración de imágenes.
 */
public final class AdministradorRecuadros {

    PanelJuego panelJuego;
    public Recuadro[] recuadros;
    public int numeroRecuadroMapa[][][];

    /**
     * Constructor de la clase AdministradorRecuadros.
     *
     * @param panelJuego Panel de juego al que pertenecen los recuadros.
     */
    public AdministradorRecuadros(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        recuadros = new Recuadro[300];
        numeroRecuadroMapa = new int[panelJuego.maximoMapas][panelJuego.maxColumnasMundo][panelJuego.maxFilasMundo];
        obtenerImagenRecuadro();
        cargarMapa("/Mapas/mapa00.txt", 0);
        cargarMapa("/Mapas/mapa01.txt", 1);
        cargarMapa("/Mapas/mapa02.txt", 2);
    }

    /**
     * Configura las imágenes para los recuadros del juego.
     */
    public void obtenerImagenRecuadro() {
        configuracion(0, "tierra1", false);
        configuracion(1, "aguaArena", false);
        configuracion(2, "agua", true);
        configuracion(3, "tierra0", false);
        configuracion(4, "tierra2", false);
        configuracion(5, "flor", false);
        configuracion(6, "flor2", false);
        configuracion(7, "arbol1_1", true);
        configuracion(8, "arbol1_2", true);
        configuracion(9, "arbol1_3", true);
        configuracion(10, "arbol1_4", true);
        configuracion(11, "arbol2_1", true);
        configuracion(12, "arbol2_2", true);
        configuracion(13, "casa3_1", true);
        configuracion(14, "casa3_2", true);
        configuracion(15, "casa3_3", true);
        configuracion(16, "casa3_4", true);
        configuracion(17, "casa3_5", true);
        configuracion(18, "casa3_6", true);
        configuracion(19, "casa3_7", true);
        configuracion(20, "casa3_8", true);
        configuracion(21, "casa3_9", true);
        configuracion(22, "casa3_10", true);
        configuracion(23, "casa3_11", true);
        configuracion(24, "casa3_12", true);
        configuracion(25, "casa3_13", true);
        configuracion(26, "casa3_14", true);
        configuracion(27, "casa3_15", true);
        configuracion(28, "casa3_16", true);
        configuracion(29, "casa3_17", true);
        configuracion(30, "casa3_18", true);
        configuracion(31, "casa3_19", true);
        configuracion(32, "casa3_20", true);

        //Entrada al otro mapa  
        configuracion(33, "casa3_21", false);
        configuracion(34, "casa3_22", false);

        configuracion(35, "casa3_23", true);
        configuracion(36, "casa3_24", true);
        configuracion(37, "fuente_1", true);
        configuracion(38, "fuente_2", true);
        configuracion(39, "fuente_3", true);
        configuracion(40, "fuente_4", true);
        configuracion(41, "fuente_5", true);
        configuracion(42, "fuente_6", true);
        configuracion(43, "fuente_7", true);
        configuracion(44, "fuente_8", true);
        configuracion(45, "fuente_9", true);
        configuracion(46, "palmera_1", true);
        configuracion(47, "palmera_2", true);
        configuracion(48, "palmera_3", true);
        configuracion(49, "palmera_4", true);
        configuracion(50, "jarron2", true);
        configuracion(51, "farol_1", true);
        configuracion(52, "farol_2", true);
        configuracion(53, "casaPlaya_1", true);
        configuracion(54, "casaPlaya_2", true);
        configuracion(55, "casaPlaya_3", true);
        configuracion(56, "casaPlaya_4", true);
        configuracion(57, "casaPlaya_5", true);
        configuracion(58, "casaPlaya_6", true);
        configuracion(59, "casaPlaya_7", true);
        configuracion(60, "casaPlaya_8", true);
        configuracion(61, "casaPlaya_9", true);
        configuracion(62, "casaPlaya_10", true);
        configuracion(63, "casaPlaya_11", true);
        configuracion(64, "casaPlaya_12", true);
        configuracion(65, "casaPlaya_13", false);
        configuracion(66, "casaPlaya_14", true);
        configuracion(67, "casaPlaya_15", true);
        configuracion(68, "casaPlaya_16", true);
        configuracion(69, "casaPlaya_17", true);

        //Entradas a la tienda
        configuracion(70, "casaPlaya_18", false);
        configuracion(71, "casaPlaya_19", false);

        configuracion(72, "casaPlaya_20", true);
        configuracion(73, "aguaTierra1", true);
        configuracion(74, "aguaTierra2", true);
        configuracion(75, "tierraAlta_1", true);
        configuracion(76, "tierraAlta_2", true);
        configuracion(77, "tierraAlta_3", true);
        configuracion(78, "tierraAlta_4", true);
        configuracion(79, "negro", true);
        configuracion(80, "tienda_1", true);
        configuracion(81, "tienda_2", true);
        configuracion(82, "tienda_3", true);
        configuracion(83, "tienda_4", true);
        configuracion(84, "tienda_5", true);
        configuracion(85, "tienda_6", true);
        configuracion(86, "tienda_7", true);
        configuracion(87, "tienda_8", true);
        configuracion(88, "tienda_9", true);
        configuracion(89, "tienda_10", true);
        configuracion(90, "tienda_11", true);
        configuracion(91, "tienda_12", true);
        configuracion(92, "tienda_13", true);
        configuracion(93, "tienda_14", true);
        configuracion(94, "tienda_15", true);
        configuracion(95, "tienda_16", true);
        configuracion(96, "tienda_17", true);
        configuracion(97, "tienda_18", true);
        configuracion(98, "tienda_19", true);
        configuracion(99, "tienda_20", true);
        configuracion(100, "tienda_21", true);
        configuracion(101, "tienda_22", true);
        configuracion(102, "tienda_23", false);
        configuracion(103, "tienda_24", false);
        configuracion(104, "tienda_25", false);
        configuracion(105, "tienda_26", true);
        configuracion(106, "tienda_27", false);
        configuracion(107, "tienda_28", false);
        configuracion(108, "tienda_29", true);
        configuracion(109, "tienda_30", true);
        configuracion(110, "tienda_31", true);
        configuracion(111, "tienda_32", true);
        configuracion(112, "tienda_33", true);
        configuracion(113, "tienda_34", true);
        configuracion(114, "tienda_35", true);
        configuracion(115, "tienda_36", true);
        configuracion(116, "tienda_37", true);
        configuracion(117, "tienda_38", true);
        configuracion(118, "tienda_39", true);
        configuracion(119, "tienda_40", true);
        configuracion(120, "tienda_41", true);
        configuracion(121, "tienda_42", false);
        configuracion(122, "tienda_43", false);
        configuracion(123, "tienda_44", false);
        configuracion(124, "tienda_45", false);
        configuracion(125, "tienda_46", false);
        configuracion(126, "tienda_47", true);
        configuracion(127, "tienda_48", true);
        configuracion(128, "puente_1", true);
        configuracion(129, "puente_2", true);
        configuracion(130, "puente_3", true);
        configuracion(131, "puente_4", false);
        configuracion(132, "puente_5", false);
        configuracion(133, "puente_6", false);
        configuracion(134, "puente_7", true);
        configuracion(135, "puente_8", true);
        configuracion(136, "puente_9", true);
        configuracion(137, "piedra_1", false);
        configuracion(138, "cerca_1", true);
        configuracion(139, "cerca_2", true);
        configuracion(140, "cerca_3", true);
        configuracion(141, "cerca_4", true);
        configuracion(142, "cerca_5", true);
        configuracion(143, "cerca_6", true);
        configuracion(144, "cerca_7", true);
        configuracion(145, "jarronFlores", true);
        configuracion(146, "cosas", true);
        configuracion(147, "interiorC_1", true);
        configuracion(148, "interiorC_2", true);
        configuracion(149, "interiorC_3", true);
        configuracion(150, "interiorC_4", true);
        configuracion(151, "interiorC_5", true);
        configuracion(152, "interiorC_6", true);
        configuracion(153, "interiorC_7", true);
        configuracion(154, "interiorC_8", true);
        configuracion(155, "interiorC_9", true);
        configuracion(156, "interiorC_10", true);
        configuracion(157, "interiorC_11", true);
        configuracion(158, "interiorC_12", true);
        configuracion(159, "interiorC_13", false);
        configuracion(160, "interiorC_14", true);
        configuracion(161, "interiorC_15", true);
        configuracion(162, "interiorC_16", true);
        configuracion(163, "interiorC_17", true);
        configuracion(164, "interiorC_18", true);
        configuracion(165, "interiorC_19", true);
        configuracion(166, "interiorC_20", true);
        configuracion(167, "interiorC_21", true);
        configuracion(168, "interiorC_22", true);
        configuracion(169, "interiorC_23", true);
        configuracion(170, "interiorC_24", false);
        configuracion(171, "interiorC_25", false);
        configuracion(172, "interiorC_26", false);
        configuracion(173, "interiorC_27", false);
        configuracion(174, "interiorC_28", true);
        configuracion(175, "interiorC_29", true);
        configuracion(176, "interiorC_30", true);
        configuracion(177, "interiorC_31", false);
        configuracion(178, "interiorC_32", false);
        configuracion(179, "interiorC_33", false);
        configuracion(180, "interiorC_34", false);
        configuracion(181, "interiorC_35", false);
        configuracion(182, "interiorC_36", false);
        configuracion(183, "interiorC_37", true);
        configuracion(184, "interiorC_38", true);
        configuracion(185, "interiorC_39", true);
        configuracion(186, "interiorC_40", true);
        configuracion(187, "interiorC_41", false);
        configuracion(188, "interiorC_42", false);
        configuracion(189, "interiorC_43", false);
        configuracion(190, "interiorC_44", true);
        configuracion(191, "interiorC_45", true);
        configuracion(192, "interiorC_46", true);
        configuracion(193, "interiorC_47", true);
        configuracion(194, "interiorC_48", true);
        configuracion(195, "interiorC_49", true);
        configuracion(196, "casa1_1", true);
        configuracion(197, "casa1_2", true);
        configuracion(198, "casa1_3", true);
        configuracion(199, "casa1_4", true);
        configuracion(200, "casa1_5", true);
        configuracion(201, "casa1_6", true);
        configuracion(202, "casa1_7", true);
        configuracion(203, "casa1_8", true);
        configuracion(204, "casa1_9", true);
        configuracion(205, "casa1_10", true);
        configuracion(206, "casa1_11", true);
        configuracion(207, "casa1_12", true);
        configuracion(208, "casa1_13", true);
        configuracion(209, "casa1_14", true);
        configuracion(210, "casa1_15", true);
        configuracion(211, "casa1_16", true);
        configuracion(212, "casa2_1", true);
        configuracion(213, "casa2_2", true);
        configuracion(214, "casa2_3", true);
        configuracion(215, "casa2_4", true);
        configuracion(216, "casa2_5", true);
        configuracion(217, "casa2_6", true);
        configuracion(218, "casa2_7", true);
        configuracion(219, "casa2_8", true);
        configuracion(220, "casa2_9", true);
        configuracion(221, "casa2_10", true);
        configuracion(222, "casa2_11", true);
        configuracion(223, "casa2_12", true);
        configuracion(224, "casa2_13", true);
        configuracion(225, "casa2_14", true);
        configuracion(226, "casa2_15", true);
        configuracion(227, "casa2_16", true);
        configuracion(228, "floristeria_1", true);
        configuracion(229, "floristeria_2", true);
        configuracion(230, "floristeria_3", true);
        configuracion(231, "floristeria_4", true);
        configuracion(232, "floristeria_5", true);
        configuracion(233, "floristeria_6", true);
        configuracion(234, "floristeria_7", true);
        configuracion(235, "floristeria_8", true);
        configuracion(236, "floristeria_9", true);
        configuracion(237, "floristeria_10", true);
        configuracion(238, "floristeria_11", true);
        configuracion(239, "floristeria_12", true);
        configuracion(240, "floristeria_13", true);
        configuracion(241, "floristeria_14", true);
        configuracion(242, "floristeria_15", true);
        configuracion(243, "floristeria_16", true);
        configuracion(244, "floristeria_17", true);
        configuracion(245, "floristeria_18", true);
        configuracion(246, "floristeria_19", true);
        configuracion(247, "floristeria_20", true);
        configuracion(248, "floristeria_21", true);
        configuracion(249, "floristeria_22", true);
        configuracion(250, "floristeria_23", true);
        configuracion(251, "floristeria_24", true);
        configuracion(252, "muchasFlores_1", true);
        configuracion(253, "muchasFlores_2", true);
        configuracion(254, "muchasFlores_3", true);
        configuracion(255, "muchasFlores_4", true);
        configuracion(256, "muchasFlores_5", true);
        configuracion(257, "muchasFlores_6", true);
        configuracion(258, "muchasFlores_7", true);
        configuracion(259, "muchasFlores_8", true);
        configuracion(260, "muchasFlores_9", true);
        configuracion(261, "charco_1", true);
        configuracion(262, "charco_2", true);
        configuracion(263, "charco_3", true);
        configuracion(264, "charco_4", true);
        configuracion(265, "charco_5", true);
        configuracion(266, "charco_6", true);
        configuracion(267, "charco_7", true);
        configuracion(268, "charco_8", true);
        configuracion(269, "charco_9", true);
        configuracion(270, "cercaColision_4", true);
    }

    /**
     * Configura un recuadro específico con una imagen y definición de colisión.
     *
     * @param indice Índice del recuadro a configurar.
     * @param nombreImagen Nombre del archivo de imagen del recuadro.
     * @param colision Indica si el recuadro tiene colisión.
     */
    public void configuracion(int indice, String nombreImagen, boolean colision) {
        EscaladorImagen herramientaUtil = new EscaladorImagen();

        try {
            recuadros[indice] = new Recuadro();
            recuadros[indice].imagen = ImageIO.read(getClass().getResourceAsStream("/Recuadros/" + nombreImagen + ".png"));
            recuadros[indice].imagen = herramientaUtil.escalarImagen(recuadros[indice].imagen, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
            recuadros[indice].colision = colision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga un mapa desde un archivo de texto.
     *
     * @param direccionArchivo Dirección del archivo de mapa.
     * @param mapa Número de mapa a cargar.
     */
    public void cargarMapa(String direccionArchivo, int mapa) {
        try {
            InputStream is = getClass().getResourceAsStream(direccionArchivo);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int columna = 0;
            int fila = 0;

            while (fila < panelJuego.maxFilasMundo) {
                String linea = br.readLine();

                String numeros[] = linea.split(" ");

                while (columna < panelJuego.maxColumnasMundo) {
                    int num = Integer.parseInt(numeros[columna]);
                    numeroRecuadroMapa[mapa][columna][fila] = num;
                    columna++;
                }

                if (columna == panelJuego.maxColumnasMundo) {
                    columna = 0;
                    fila++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica si un recuadro específico tiene colisión en el mapa actual.
     *
     * @param x Posición X del recuadro en el mapa.
     * @param y Posición Y del recuadro en el mapa.
     * @param mapa Número de mapa en el que se verifica la colisión.
     * @return true si el recuadro tiene colisión, false de lo contrario.
     */
    public boolean tieneColision(int x, int y, int mapa) {
        int numeroRecuadro = numeroRecuadroMapa[mapa][x][y];
        return recuadros[numeroRecuadro].colision;
    }

    /**
     * Verifica si hay basura en el mapa actual.
     *
     * @return true si hay basura en el mapa, false de lo contrario.
     */
    public boolean hayBasura() {
        for (SuperObjeto objeto : panelJuego.obj[0]) {
            if (objeto != null && objeto instanceof Basura) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si hay basura alrededor de una fuente específica en el mapa.
     *
     * @return true si hay basura alrededor de la fuente, false de lo contrario.
     */
    public boolean hayBasuraAlrededorFuente() {
        int posicionesX[] = {52, 52, 52, 56, 56, 56, 53, 54, 55, 53, 54, 55};
        int posicionesY[] = {32, 33, 34, 33, 34, 32, 31, 31, 31, 35, 35, 35};
        int numeroMapa = 0;

        for (int i = 0; i < posicionesX.length; i++) {
            for (SuperObjeto objeto : panelJuego.obj[numeroMapa]) {
                if (objeto != null && objeto.mundoX == posicionesX[i] * panelJuego.tamannoRecuadros && objeto.mundoY == posicionesY[i] * panelJuego.tamannoRecuadros) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Busca un electrodoméstico específico en el tercer mapa y devuelve su
     * índice.
     *
     * @param electrodomestico Electrodoméstico a buscar.
     * @return Índice del electrodoméstico si se encuentra, -1 si no se
     * encuentra.
     */
    public int buscarElectrodomestico(Electrodomestico electrodomestico) {
        for (int i = 0; i < panelJuego.obj[2].length; i++) {
            SuperObjeto objeto = panelJuego.obj[2][i];
            if (objeto != null && objeto == electrodomestico) {
                return i; // Devuelve el índice del electrodoméstico
            }
        }
        return -1; // Devuelve -1 si no se encuentra el electrodoméstico
    }

    /**
     * Verifica si todas las lámparas del mapa 2 están apagadas.
     *
     * @return true si todas las lámparas están apagadas, false si al menos una
     * está encendida.
     */
    public boolean todasLamparasApagadas() {
        int numeroMapa = 2;

        for (SuperObjeto item : panelJuego.obj[numeroMapa]) {
            if (item instanceof Electrodomestico electrodomestico) {
                if (electrodomestico.getNombre().equals("lampara") && electrodomestico.encendido) {
                    return false; // Hay al menos una lámpara encendida
                }
            }
        }
        return true; // Todas las lámparas están apagadas
    }

    /**
     * Dibuja los recuadros visibles en el panel de juego.
     *
     * @param g2 Objeto Graphics2D para dibujar.
     */
    public void dibujar(Graphics2D g2) {
        int columnaMundo = 0;
        int filaMundo = 0;

        while (columnaMundo < panelJuego.maxColumnasMundo && filaMundo < panelJuego.maxFilasMundo) {
            int numeroRecuadro = numeroRecuadroMapa[panelJuego.mapaActual][columnaMundo][filaMundo];
            int mundoX = columnaMundo * panelJuego.tamannoRecuadros;
            int mundoY = filaMundo * panelJuego.tamannoRecuadros;
            int pantallaX = mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
            int pantallaY = mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;

            if (mundoX + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoX - panelJuego.jugador.pantallaX
                    && mundoX - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX
                    && mundoY + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoY - panelJuego.jugador.pantallaY
                    && mundoY - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY) {
                g2.drawImage(recuadros[numeroRecuadro].imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
            }
            columnaMundo++;
            if (columnaMundo == panelJuego.maxColumnasMundo) {
                columnaMundo = 0;
                filaMundo++;
            }
        }
    }

}
