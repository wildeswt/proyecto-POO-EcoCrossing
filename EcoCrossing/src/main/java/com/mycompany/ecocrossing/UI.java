package com.mycompany.ecocrossing;

import Entidades.Entidad;
import Objeto.Basura;
import Objeto.Corazon;
import Objeto.Moneda;
import Objeto.SuperObjeto;
import Objeto.Comida;
import Objeto.TortugaPlastico;
import Personaje.CargadorImagenes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Clase UI que implementa la interfaz CargadorImagenes. Gestiona la interfaz
 * gráfica del juego.
 */
public class UI implements CargadorImagenes {

    PanelJuego panelJuego;
    Graphics2D g2;
    Font pixelSerif;
    BufferedImage imagenBasura, imagenMoneda, corazon_full, corazon_medio, corazon_blanco;
    public boolean mensajeActivo = false;
    public String mensaje = "";
    public int contadorMensaje = 0;
    public String dialogoActual = "";
    public Entidad npc;
    public int subEstado = 0;
    public boolean juegoFinalizado = false;
    double tiempoJuego;
    DecimalFormat formatoDecimal = new DecimalFormat("#0.00");
    public int comandoNumerico = 0;
    public int vestimenta = -1;
    public int estadoPantallaTitulo = 0; // El númeri indica la pantalla actual
    public int jugadorRanuraColumna = 0;
    public int jugadorRanuraFila = 0;
    public int npcRanuraColumna = 0;
    public int npcRanuraFila = 0;

    /**
     * Constructor de la clase UI. Inicializa los componentes gráficos y carga
     * las imágenes.
     *
     * @param panelJuego El panel del juego
     */
    public UI(PanelJuego panelJuego) {
        try {
            this.panelJuego = panelJuego;
            InputStream is = getClass().getResourceAsStream("/Fuentes/PixelSerif_16px_v02.otf");
            try {
                pixelSerif = Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            Basura basura = new Basura("papelera");
            basura.cargar(panelJuego.tamannoRecuadros);
            imagenBasura = basura.imagen;
            Moneda moneda = new Moneda();
            moneda.cargar(panelJuego.tamannoRecuadros);
            imagenMoneda = moneda.imagen;
            //Corazones
            SuperObjeto corazon = new Corazon(panelJuego);
            corazon_full = corazon.imagen;
            corazon_medio = corazon.imagen2;
            corazon_blanco = corazon.imagen3;
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Muestra un mensaje en la pantalla.
     *
     * @param texto El mensaje a mostrar
     */
    public void mostrarMensaje(String texto) {
        mensaje = texto;
        mensajeActivo = true;
    }

    /**
     * Dibuja los elementos gráficos en la pantalla.
     *
     * @param g2 El contexto gráfico
     */
    public void dibujar(Graphics2D g2) {
        this.g2 = g2;

        if (juegoFinalizado == true) {
            panelJuego.estadoJuego = panelJuego.estadoJuegoTerminado;
        } // Verificar más adelante si esto esta del todo bien...
        else if (panelJuego.estadoJuego == panelJuego.estadoJugando) {
            g2.setFont(pixelSerif);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
            g2.setColor(Color.white);
            g2.drawImage(imagenBasura, panelJuego.tamannoRecuadros / 2, panelJuego.tamannoRecuadros / 2, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
            g2.drawString("x " + panelJuego.jugador.cntBasura, 74, 65);
            g2.drawImage(imagenMoneda, panelJuego.tamannoRecuadros / 2, (panelJuego.tamannoRecuadros / 2) + panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
            g2.drawString("x " + panelJuego.jugador.cntMonedas, 74, 65 + panelJuego.tamannoRecuadros);

            // TIEMPO
            /*tiempoJuego += (double)1/60;
            g2.drawString("Tiempo:"+formatoDecimal.format(tiempoJuego), panelJuego.tamannoRecuadros*10.5f, 65);*/
            // MENSAJES
            if (mensajeActivo) {
                // Configurar la fuente y métricas
                Font font = g2.getFont().deriveFont(30f);
                g2.setFont(font);
                FontMetrics metrics = g2.getFontMetrics(font);

                // Calcular dimensiones del mensaje
                int mensajeWidth = metrics.stringWidth(mensaje);
                int mensajeHeight = metrics.getHeight();

                // Calcular posición para el mensaje
                int mensajeX = (panelJuego.getWidth() - mensajeWidth) / 2;
                int mensajeY = panelJuego.getHeight() - mensajeHeight - 20; // Ajuste para posición en el centro inferior

                // Dibujar rectángulo sombreado translúcido detrás del mensaje
                g2.setColor(new Color(0, 0, 0, 150)); // Color negro con transparencia
                g2.fillRect(mensajeX - 2, mensajeY - metrics.getAscent() - 2, mensajeWidth + 4, mensajeHeight + 4);

                // Dibujar texto principal del mensaje
                g2.setColor(Color.WHITE);
                g2.drawString(mensaje, mensajeX, mensajeY);

                // Actualizar contador de mensaje
                contadorMensaje++;
                if (contadorMensaje > 120) {
                    contadorMensaje = 0;
                    mensajeActivo = false;
                }
            }
        }

        g2.setFont(pixelSerif);
        g2.setColor(Color.white);

        // Pantalla titulo:
        if (panelJuego.estadoJuego == panelJuego.estadoTitulo) {
            dibujarPantallaTitulo();
        } else if (panelJuego.estadoJuego == panelJuego.estadoJugando) {
            dibujarVidaJugador();
        } // Pausa
        else if (panelJuego.estadoJuego == panelJuego.estadoPausa) {
            dibujarVidaJugador();
            dibujarPantallaPausa();
        } // Diálogo:
        else if (panelJuego.estadoJuego == panelJuego.estadoDialogo) {
            dibujarVidaJugador();
            dibujarPantallaDialogo();
        } else if (panelJuego.estadoJuego == panelJuego.estadoPersonaje) {
            dibujarInventario(panelJuego.jugador, true);
        } else if (panelJuego.estadoJuego == panelJuego.estadoTradeo) {
            dibujarPantallaTradeo();
        } // Game over
        else if (panelJuego.estadoJuego == panelJuego.estadoJuegoTerminado) {
            if (juegoFinalizado == false) {
                dibujarPantallaGameOver("Has muerto.");
            } else {
                dibujarPantallaGameOver("Felicidades.");
            }
        }

    }

    /**
     * Dibuja la pantalla de GameOver del juego.
     *
     * @param texto texto a mostrar según la manera en que se finalizo
     */
    public void dibujarPantallaGameOver(String texto) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, panelJuego.anchoPantalla, panelJuego.largoPantalla);

        int x, y;
        //String texto;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        //texto = "Has Muerto.";
        // Sombra
        g2.setColor(Color.black);
        x = obtenerXParaTextoCentrado(texto);
        y = panelJuego.tamannoRecuadros * 4;
        g2.drawString(texto, x, y);

        // Texto principal
        g2.setColor(Color.white);
        g2.drawString(texto, x - 4, y - 4);

        // Volver al juego
        g2.setFont(g2.getFont().deriveFont(50f));
        texto = "Regenerar.";
        x = obtenerXParaTextoCentrado(texto);
        y += panelJuego.tamannoRecuadros * 4;
        g2.drawString(texto, x, y);
        if (comandoNumerico == 0) {
            g2.drawString(">", x - 40, y);
        }

        // Volver a la pantalla principal
        texto = "Volver a la pantalla principal.";
        x = obtenerXParaTextoCentrado(texto);
        y += 55;
        g2.drawString(texto, x, y);
        if (comandoNumerico == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    /**
     * Dibuja las vidas del jugador.
     */
    public void dibujarVidaJugador() {
        int x = panelJuego.getWidth() - panelJuego.tamannoRecuadros / 2; // Posición inicial en la esquina derecha
        int y = panelJuego.tamannoRecuadros / 2; // Posición vertical constante en la parte superior

        // Dibujar todos los corazones blancos (vida máxima)
        for (int i = 0; i < panelJuego.jugador.vidaMaxima / 2; i++) {
            x -= panelJuego.tamannoRecuadros; // Ajustar x hacia la izquierda para cada corazón
            g2.drawImage(corazon_blanco, x, y, null);
        }

        // Dibujar corazones llenos y medios para la vida actual
        x = panelJuego.getWidth() - panelJuego.tamannoRecuadros / 2; // Reiniciar x

        // Dibujar corazones llenos hasta la cantidad de vida actual
        for (int i = 0; i < panelJuego.jugador.vida / 2; i++) {
            x -= panelJuego.tamannoRecuadros; // Ajustar x hacia la izquierda para cada corazón
            g2.drawImage(corazon_full, x, y, null);
        }

        // Si la vida actual es impar, dibujar un corazón medio adicional
        if (panelJuego.jugador.vida % 2 == 1) {
            x -= panelJuego.tamannoRecuadros; // Ajustar x hacia la izquierda para el corazón medio
            g2.drawImage(corazon_medio, x, y, null);
        }
    }

    /**
     * Dibuja la pantalla de título del juego.
     */
    public void dibujarPantallaTitulo() {

        if (estadoPantallaTitulo == 0) {

            //Pintamos al jugador
            panelJuego.jugador.cargarSkin("Samuel");
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, panelJuego.anchoPantalla, panelJuego.largoPantalla);

            //Pintamos el fondo 
            pintarImagen("/Fondos/Fondo Principal", panelJuego.anchoPantalla, panelJuego.largoPantalla, 0, 0);

            // Nombre del título:
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60f));
            String texto = "EcoCrossing Adventure";
            int x = obtenerXParaTextoCentrado(texto);
            int y = panelJuego.tamannoRecuadros * 2;
            // Sombra:
            sombra(texto, Color.GRAY, x, y, 5);
            g2.drawString(texto, x, y);

            // Color del Menú:
            g2.setColor(Color.white);
            g2.drawString(texto, x, y);

            // Personaje Principal Imagen:
            x = panelJuego.anchoPantalla / 2 - (panelJuego.tamannoRecuadros);
            y += panelJuego.tamannoRecuadros * 2;
            g2.drawImage(panelJuego.jugador.abajo1, x, y, panelJuego.tamannoRecuadros * 2, panelJuego.tamannoRecuadros * 2, null);

            // Menú_
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));

            texto = "NUEVO JUEGO";
            x = obtenerXParaTextoCentrado(texto);
            y += panelJuego.tamannoRecuadros * 3;
            sombra(texto, Color.black, x, y, 3);
            g2.drawString(texto, x, y);

            if (comandoNumerico == 0) {
                g2.drawString(">", x - panelJuego.tamannoRecuadros, y);
            }

            texto = "ACERCA DE";
            x = obtenerXParaTextoCentrado(texto);
            y += panelJuego.tamannoRecuadros;
            sombra(texto, Color.black, x, y, 3);
            g2.drawString(texto, x, y);

            if (comandoNumerico == 1) {
                g2.drawString(">", x - panelJuego.tamannoRecuadros, y);
            }

            texto = "SALIR";
            x = obtenerXParaTextoCentrado(texto);
            y += panelJuego.tamannoRecuadros;
            sombra(texto, Color.black, x, y, 3);
            g2.drawString(texto, x, y);

            if (comandoNumerico == 2) {
                g2.drawString(">", x - panelJuego.tamannoRecuadros, y);
            }

        } else if (estadoPantallaTitulo == 1) {
            // Selección de Personalización de Personaje:
            pintarImagen("/Fondos/Seleccion de Personaje", panelJuego.anchoPantalla, panelJuego.largoPantalla, 0, 0);

            int opcion1 = comandoNumerico;
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42f));

            String texto = "Selecciona tu personaje!";
            int x = obtenerXParaTextoCentrado(texto);
            int y = panelJuego.tamannoRecuadros;
            sombra(texto, Color.black, x - 60, y, 3);
            g2.drawString(texto, x - 60, y);

            String[] personajes = {"Samuel", "Maria", "Ignacio", "Carlos"};

            //aumentamos y para escribir mas abajo 
            y += panelJuego.tamannoRecuadros * 3;

            pintarLista(personajes, x - 100, y, comandoNumerico);

            //dibujamos la vestimenta
            if (vestimenta >= 0 && vestimenta <= 3) {
                //cargar ropa
                String[] closet = cargarCloset(opcion1);

                //vestimentas para cada personaje 
                pintarLista(closet, x + 150, y, vestimenta);

                //colocamos el boton salir 
                texto = "Volver";
                x = obtenerXParaTextoCentrado(texto);
                y += panelJuego.tamannoRecuadros * 3;
                sombra(texto, Color.black, x, y, 3);
                g2.drawString(texto, x, y);
                if (vestimenta == 3) {
                    g2.drawString(">", x - 20, y);
                }

                //pintar personaje 
                if (opcion1 >= 0 && opcion1 <= 3) {
                    if (vestimenta >= 0 && vestimenta <= 2) {
                        String nombre = cargarNombre(opcion1);
                        String estilo = cargarRopa(nombre, vestimenta);
                        pintarPersonaje(estilo);
                    }
                }

            } else {
                if (opcion1 >= 0 && opcion1 <= 3) {
                    String nombre = cargarNombre(opcion1);
                    pintarPersonaje(nombre);
                }
            }

            texto = "Volver";
            x = obtenerXParaTextoCentrado(texto);
            y = panelJuego.tamannoRecuadros * 11;
            sombra(texto, Color.black, x, y, 3);
            g2.drawString(texto, x, y);
            if (comandoNumerico == 4 && vestimenta == -1) {
                g2.drawString(">", x - 20, y);
            }
        }
    }

    /**
     * Dibuja la pantalla de pausa del juego y el menu de opciones.
     */
    public void dibujarPantallaPausa() {
        g2.setColor(Color.white);
        //Sub ventana
        int x= panelJuego.tamannoRecuadros*4;
        int y=panelJuego.tamannoRecuadros;
        int ancho=panelJuego.tamannoRecuadros*8;
        int largo=panelJuego.tamannoRecuadros*8;
        dibujarSubVentana(x,y,ancho,largo);
        
        switch(subEstado){
            case 0:
                TextoOpciones(panelJuego.tamannoRecuadros*5,y);
                break;
            case 1:
                ConfirmarSalir(panelJuego.tamannoRecuadros*5,y);
                break;
        }
        panelJuego.manejoTeclas.enterPresionado= false;
    }
    
    /**
     * Dibujar el texto del menu de opciones en pantalla.
     * @param x Coordenada x de la posicion del texto y rectangulo.
     * @param y Coordenada y de la posicion del textoy rectangulo.
     */
    public void TextoOpciones(int x, int y){
        g2.setFont(g2.getFont().deriveFont(50F));
        
        int aux= obtenerXParaTextoCentrado("PAUSA");
        y= panelJuego.largoPantalla/5;
        g2.drawString("PAUSA", aux, y);
        
        g2.setFont(g2.getFont().deriveFont(28F));
        
        y += panelJuego.tamannoRecuadros*2;
        g2.drawString("Musica", x, y);
        if(comandoNumerico == 0)
           g2.drawString(">", x-25, y);
        
        y += panelJuego.tamannoRecuadros;
        g2.drawString("Efectos Sonido", x, y);
        if(comandoNumerico == 1)
           g2.drawString(">", x-25, y);
        
        y+= panelJuego.tamannoRecuadros;
        g2.drawString("Salir del Juego", x, y);
        if(comandoNumerico == 2){
           g2.drawString(">", x-25, y);
           if(panelJuego.manejoTeclas.enterPresionado){
               subEstado=1;
               comandoNumerico=0;
           }
        }
        
        y+= panelJuego.tamannoRecuadros*2;
        g2.drawString("Volver", x, y);
        if(comandoNumerico == 3){
           g2.drawString(">", x-25, y);
           if(panelJuego.manejoTeclas.enterPresionado){
               panelJuego.estadoJuego=panelJuego.estadoJugando;
               comandoNumerico=0;
           }
        }
        
        //Volumen musica
        x= panelJuego.tamannoRecuadros*9;
        y= panelJuego.tamannoRecuadros + panelJuego.tamannoRecuadros*2 + 48;
        g2.drawRect(x, y, 120, 24);
        int volumen= 24 * panelJuego.musica.escalaVolumen;
        g2.fillRect(x, y, volumen, 24);
        
        //Volumen Efectos de Sonido
        y += panelJuego.tamannoRecuadros;
        g2.drawRect(x, y, 120, 24);
        volumen= 24 * panelJuego.efectosSonido.escalaVolumen;
        g2.fillRect(x, y, volumen, 24);
    }
    
    /**
     * Dibujar el texto del menu de opciones en pantalla.
     * @param x Coordenada x de la posicion del texto.
     * @param y Coordenada y de la posicion del texto.
     */
    public void ConfirmarSalir(int x, int y){
        g2.setFont(g2.getFont().deriveFont(28F));
        x += panelJuego.tamannoRecuadros/7;
        y += panelJuego.tamannoRecuadros*2;
        
        dialogoActual="Desea cerrar el juego y \n  volver a la pantalla \n          de inicio?";
        
        for(String linea: dialogoActual.split("\n")){
            g2.drawString(linea, x, y);
            y += 40;
        }
        
        x=obtenerXParaTextoCentrado("Si");
        y += panelJuego.tamannoRecuadros;
        g2.drawString("Si", x, y);
        if(comandoNumerico==0){
            g2.drawString(">", x-25, y);
            if(panelJuego.manejoTeclas.enterPresionado){
                subEstado=0;
                panelJuego.reiniciar();
            }
        }
        
        x=obtenerXParaTextoCentrado("No");
        y += panelJuego.tamannoRecuadros;
        g2.drawString("No", x, y);
        if(comandoNumerico==1){
            g2.drawString(">", x-25, y);
            if(panelJuego.manejoTeclas.enterPresionado){
                subEstado=0;
                comandoNumerico=2;
            }
        }
    }
    
    /**
     * Dibuja la pantalla de diálogo del juego.
     */
    public void dibujarPantallaDialogo() {
        //Ventana
        int x = panelJuego.tamannoRecuadros * 2;
        int y = panelJuego.tamannoRecuadros / 2;
        int ancho = panelJuego.anchoPantalla - (panelJuego.tamannoRecuadros * 4);
        int largo = panelJuego.tamannoRecuadros * 4;

        dibujarSubVentana(x, y, ancho, largo);

        x += panelJuego.tamannoRecuadros;
        y += panelJuego.tamannoRecuadros;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));

        for (String linea : dialogoActual.split("\n")) {
            g2.drawString(linea, x, y);
            y += 40;
        }
    }

    /**
     * Dibuja el inventario de la entidad especificada.
     *
     * @param entidad La entidad cuyo inventario se va a dibujar.
     * @param cursor Indica si se debe dibujar el cursor del inventario.
     */
    public void dibujarInventario(Entidad entidad, boolean cursor) {
        // Marcos del inventario: 
        int marcoX = 0;
        int marcoY = 0;
        int anchoMarco = 0;
        int altoMarco = 0;
        int ranuraColumna = 0;
        int ranuraFila = 0;

        if (entidad == panelJuego.jugador) {
            marcoX = panelJuego.tamannoRecuadros * 9;
            marcoY = panelJuego.tamannoRecuadros;
            anchoMarco = panelJuego.tamannoRecuadros * 6;
            altoMarco = panelJuego.tamannoRecuadros * 5;
            ranuraColumna = jugadorRanuraColumna;
            ranuraFila = jugadorRanuraFila;
        } else if (entidad == npc) {
            marcoX = panelJuego.tamannoRecuadros * 2;
            marcoY = panelJuego.tamannoRecuadros;
            anchoMarco = panelJuego.tamannoRecuadros * 6;
            altoMarco = panelJuego.tamannoRecuadros * 5;
            ranuraColumna = npcRanuraColumna;
            ranuraFila = npcRanuraFila;
        }
        dibujarSubVentana(marcoX, marcoY, anchoMarco, altoMarco);

        // Ranuras del inventario:
        final int inicioRanuraX = marcoX + 20;
        final int inicioRanuraY = marcoY + 20;
        int ranuraX = inicioRanuraX;
        int ranuraY = inicioRanuraY;
        int tamannoRanura = panelJuego.tamannoRecuadros + 3;

        // Dibujar items del inventario:
        for (int i = 0; i < entidad.inventario.size(); i++) {
            g2.drawImage(entidad.inventario.get(i).imagen, ranuraX, ranuraY, null);
            ranuraX += tamannoRanura;

            if (i == 4 || i == 9 || i == 14) {
                ranuraX = inicioRanuraX;
                ranuraY += tamannoRanura;
            }
        }

        // Cursor:
        if (cursor == true) {
            int cursorX = inicioRanuraX + (tamannoRanura * ranuraColumna);
            int cursorY = inicioRanuraY + (tamannoRanura * ranuraFila);
            int anchoCursor = panelJuego.tamannoRecuadros;
            int altoCursor = panelJuego.tamannoRecuadros;
            // Dibujar cursor:
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, anchoCursor, altoCursor, 10, 10);
            // Cuadro de Descripcion:
            int descripcionCuadroX = marcoX;
            int descripcionCuadroY = marcoY + altoMarco;
            int descripcionAnchoCuadro = anchoMarco;
            int descripcionAlturaCuadro = panelJuego.tamannoRecuadros * 3;
            //Dibujar Descripcion:
            int textoX = descripcionCuadroX + 20;
            int textoY = descripcionCuadroY + panelJuego.tamannoRecuadros;
            g2.setFont(g2.getFont().deriveFont(28f));
            int indiceObjeto = obtenerObjetoIndiceEnRanura(ranuraColumna, ranuraFila);

            if (indiceObjeto < entidad.inventario.size()) {
                dibujarSubVentana(descripcionCuadroX, descripcionCuadroY, descripcionAnchoCuadro, descripcionAlturaCuadro);
                for (String linea : entidad.inventario.get(indiceObjeto).descripcion.split("\n")) {
                    g2.drawString(linea, textoX, textoY);
                    textoY += 32;
                }
            }
        }
    }

    /**
     * Dibuja la pantalla de tradeo del juego.
     */
    public void dibujarPantallaTradeo() {
        if (subEstado == 0) {
            tradeoSeleccionar();
        } else if (subEstado == 1) {
            tradeoComprar();
        } else if (subEstado == 2) {
            tradeoVender();
        }
        panelJuego.manejoTeclas.enterPresionado = false;
    }

    /**
     * Dibuja la pantalla prara las opciones de un tradeo.
     */
    public void tradeoSeleccionar() {
        dibujarPantallaDialogo();

        // Dibujar Ventana:
        int x = panelJuego.tamannoRecuadros * 12;
        int y = panelJuego.tamannoRecuadros * 4;
        int ancho = panelJuego.tamannoRecuadros * 4;
        int alto = (int) (panelJuego.tamannoRecuadros * 3.5);
        dibujarSubVentana(x, y, ancho, alto);

        // Dibujar Textos:
        x += panelJuego.tamannoRecuadros;
        y += panelJuego.tamannoRecuadros;
        g2.drawString("Comprar", x, y);
        if (comandoNumerico == 0) {
            g2.drawString(">", x - 24, y);
            if (panelJuego.manejoTeclas.enterPresionado == true) {
                subEstado = 1;
            }
        }
        y += panelJuego.tamannoRecuadros;

        g2.drawString("Vender", x, y);
        if (comandoNumerico == 1) {
            g2.drawString(">", x - 24, y);
            if (panelJuego.manejoTeclas.enterPresionado == true) {
                subEstado = 2;
            }
        }

        y += panelJuego.tamannoRecuadros;

        g2.drawString("Salir", x, y);
        if (comandoNumerico == 2) {
            g2.drawString(">", x - 24, y);
            if (panelJuego.manejoTeclas.enterPresionado == true) {
                comandoNumerico = 0;
                panelJuego.estadoJuego = panelJuego.estadoDialogo;
                dialogoActual = "Vuelve pronto...";
            }
        }
    }

    /**
     * Dibuja la pantalla de compra en caso de haber seleccionado comprar en el
     * tradeo.
     */
    public void tradeoComprar() {
        // Dibujar inventario del jugador:
        dibujarInventario(panelJuego.jugador, false);
        // Dibujar inventario del npc:
        dibujarInventario(npc, true);
        // DIBUJAR VENTANA DE PISTA
        int x = panelJuego.tamannoRecuadros * 2;
        int y = panelJuego.tamannoRecuadros * 9;
        int ancho = panelJuego.tamannoRecuadros * 6;
        int alto = panelJuego.tamannoRecuadros * 2;
        dibujarSubVentana(x, y, ancho, alto);
        g2.drawString("[ESC] Volver", x + 24, y + 60);

        // DIBUJAR VENTANA DE MONEDAS DEL JUGADOR
        x = panelJuego.tamannoRecuadros * 9;
        y = panelJuego.tamannoRecuadros * 9;
        ancho = panelJuego.tamannoRecuadros * 6;
        alto = panelJuego.tamannoRecuadros * 2;
        dibujarSubVentana(x, y, ancho, alto);
        g2.drawString("Tus Monedas: " + panelJuego.jugador.cntMonedas, x + 24, y + 60);

        // Dibujar ventana de precio:
        int objetoIndice = obtenerObjetoIndiceEnRanura(npcRanuraColumna, npcRanuraFila);
        if (objetoIndice < npc.inventario.size()) {
            x = (int) (panelJuego.tamannoRecuadros * 5.5);
            y = (int) (panelJuego.tamannoRecuadros * 5.5);
            ancho = (int) (panelJuego.tamannoRecuadros * 2.5);
            alto = panelJuego.tamannoRecuadros;
            dibujarSubVentana(x, y, ancho, alto);
            g2.drawImage(imagenMoneda, x + 10, y + 6, 32, 32, null);
            int precio = npc.inventario.get(objetoIndice).precio;
            String texto = "" + precio;
            x = obtenerXParaTextoCentrado(texto);
            g2.drawString(texto, x - 25, y + 32);

            if (panelJuego.manejoTeclas.enterPresionado == true) {
                if (npc.inventario.get(objetoIndice).precio > panelJuego.jugador.cntMonedas) {
                    subEstado = 0;
                    panelJuego.estadoJuego = panelJuego.estadoDialogo;
                    dialogoActual = "Tu necesitas mas monedas...";
                    dibujarPantallaDialogo();
                } else if (panelJuego.jugador.inventario.size() == panelJuego.jugador.tamannoMaxInventario) {
                    subEstado = 0;
                    panelJuego.estadoJuego = panelJuego.estadoDialogo;
                    dialogoActual = "Tu no puedes llevar mas equipo!";
                } else {
                    if (npc.inventario.get(objetoIndice) instanceof Basura) {
                        panelJuego.jugador.cntBasura++;
                    }
                    if (npc.inventario.get(objetoIndice) instanceof Comida) {
                        panelJuego.jugador.cntComida++;
                    }
                    panelJuego.jugador.cntMonedas -= npc.inventario.get(objetoIndice).precio;
                    panelJuego.jugador.inventario.add(npc.inventario.get(objetoIndice));
                }
            }
        }
    }

    /**
     * Dibuja la pantalla de venta en caso de haber seleccionado vender en el
     * tradeo.
     */
    public void tradeoVender() {
        // Dibujar inventario del jugador:
        dibujarInventario(panelJuego.jugador, true);
        int x;
        int y;
        int ancho;
        int alto;

        // DIBUJAR VENTANA DE PISTA
        x = panelJuego.tamannoRecuadros * 2;
        y = panelJuego.tamannoRecuadros * 9;
        ancho = panelJuego.tamannoRecuadros * 6;
        alto = panelJuego.tamannoRecuadros * 2;
        dibujarSubVentana(x, y, ancho, alto);
        g2.drawString("[ESC] Volver", x + 24, y + 60);

        // DIBUJAR VENTANA DE MONEDAS DEL JUGADOR
        x = panelJuego.tamannoRecuadros * 9;
        y = panelJuego.tamannoRecuadros * 9;
        ancho = panelJuego.tamannoRecuadros * 6;
        alto = panelJuego.tamannoRecuadros * 2;
        dibujarSubVentana(x, y, ancho, alto);
        g2.drawString("Tus Monedas: " + panelJuego.jugador.cntMonedas, x + 24, y + 60);

        // Dibujar ventana de precio:
        int objetoIndice = obtenerObjetoIndiceEnRanura(jugadorRanuraColumna, jugadorRanuraFila);
        if (objetoIndice < panelJuego.jugador.inventario.size()) {
            x = (int) (panelJuego.tamannoRecuadros * 5.5);
            y = (int) (panelJuego.tamannoRecuadros * 5.5);
            ancho = (int) (panelJuego.tamannoRecuadros * 2.5);
            alto = panelJuego.tamannoRecuadros;
            dibujarSubVentana(x, y, ancho, alto);
            g2.drawImage(imagenMoneda, x + 10, y + 6, 32, 32, null);
            int precio = panelJuego.jugador.inventario.get(objetoIndice).precio / 2; // La inflación
            String texto = "" + precio;
            x = obtenerXParaTextoCentrado(texto);
            g2.drawString(texto, x - 25, y + 32);

            // Vender un objeto
            if (panelJuego.manejoTeclas.enterPresionado == true) {
                // Si es un objeto de uso del personaje no se debería vender...
                // Por ejemplo podríamos validar no vender tortugas...
                // Usariamo un if y luego un else
                if (panelJuego.jugador.inventario.get(objetoIndice) instanceof Basura) {
                    panelJuego.jugador.cntBasura--;
                }
                if (panelJuego.jugador.inventario.get(objetoIndice) instanceof Comida) {
                    panelJuego.jugador.cntComida--;
                }
                if (panelJuego.jugador.inventario.get(objetoIndice) instanceof TortugaPlastico) {
                    subEstado = 0;
                    panelJuego.estadoJuego = panelJuego.estadoDialogo;
                    dialogoActual = "No puedes vender animales!";
                } else {
                    panelJuego.jugador.inventario.remove(objetoIndice);
                    panelJuego.jugador.cntMonedas += precio;
                }
            }
        }
    }

    /**
     * Obtiene el índice del objeto en la ranura especificada.
     *
     * @param ranuraColumna La columna de la ranura.
     * @param ranuraFila La fila de la ranura.
     * @return El índice del objeto en la ranura especificada.
     */
    public int obtenerObjetoIndiceEnRanura(int ranuraColumna, int ranuraFila) {
        int indiceObjeto = ranuraColumna + (ranuraFila * 5);
        return indiceObjeto;
    }

    /**
     * Dibuja una subventana en las coordenadas especificadas con el ancho y
     * altura dados.
     *
     * @param x La coordenada X de la subventana.
     * @param y La coordenada Y de la subventana.
     * @param ancho El ancho de la subventana.
     * @param altura La altura de la subventana.
     */
    public void dibujarSubVentana(int x, int y, int ancho, int altura) {
        Color color = new Color(0, 0, 0);
        g2.setColor(color);
        g2.fillRoundRect(x, y, ancho, altura, 35, 35);

        color = new Color(255, 255, 255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke());
        g2.drawRoundRect(x + 5, y + 5, ancho - 10, altura - 10, 25, 25);
    }

    /**
     * Obtiene la coordenada X para centrar el texto en la pantalla.
     *
     * @param texto El texto que se va a centrar.
     * @return La coordenada X para centrar el texto.
     */
    public int obtenerXParaTextoCentrado(String texto) {
        int longitud = (int) g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
        int x = panelJuego.anchoPantalla / 2 - longitud / 2;
        return x;
    }

    /**
     * Calcula la coordenada X para centrar un elemento en la pantalla.
     *
     * @return La coordenada X para centrar un elemento en la pantalla.
     */
    public int centrar() {
        return panelJuego.anchoPantalla / 2 - panelJuego.tamannoRecuadros;
    }

    /**
     * Carga una imagen desde el archivo especificado y la escala al tamaño
     * dado.
     *
     * @param ruta Ruta del archivo de imagen.
     * @param ancho Ancho al que se escalará la imagen.
     * @param largo Largo al que se escalará la imagen.
     * @return La imagen cargada y escalada.
     */
    @Override
    public BufferedImage cargarImagen(String ruta, int ancho, int largo) {
        EscaladorImagen herramientaUtil = new EscaladorImagen();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("" + ruta + ".png"));
            imagen = herramientaUtil.escalarImagen(imagen, ancho, largo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imagen;
    }

    /**
     * Pinta una imagen en las coordenadas especificadas con el tamaño dado.
     *
     * @param ruta Ruta del archivo de imagen.
     * @param ancho Ancho de la imagen a pintar.
     * @param largo Largo de la imagen a pintar.
     * @param x Coordenada X de la posición de la imagen.
     * @param y Coordenada Y de la posición de la imagen.
     */
    public void pintarImagen(String ruta, int ancho, int largo, int x, int y) {
        BufferedImage imagen = cargarImagen(ruta, ancho, largo);
        g2.drawImage(imagen, x, y, ancho, largo, null);
    }

    /**
     * Pinta un personaje en la pantalla basado en el nombre proporcionado.
     *
     * @param nombre Nombre del personaje a pintar.
     */
    public void pintarPersonaje(String nombre) {
        BufferedImage imagen = null;
        int xImg, yImg;
        xImg = centrar() + 230;
        yImg = centrar() + 25;
        imagen = cargarImagen("/Jugador/" + nombre + "/frente", panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);

        g2.drawImage(imagen, xImg, yImg, panelJuego.tamannoRecuadros * 4, panelJuego.tamannoRecuadros * 4, null);
    }

    /**
     * Pinta una palabra en las coordenadas especificadas.
     *
     * @param palabra Palabra a pintar.
     * @param x Coordenada X de la posición de la palabra.
     * @param y Coordenada Y de la posición de la palabra.
     */
    public void pintarPalabra(String palabra, int x, int y) {
        g2.drawString(palabra, x, y);
    }

    /**
     * Pinta una lista de palabras en la pantalla, resaltando la opción
     * seleccionada.
     *
     * @param palabras Lista de palabras a pintar.
     * @param x Coordenada X inicial de la lista.
     * @param y Coordenada Y inicial de la lista.
     * @param opcion Índice de la palabra seleccionada.
     */
    public void pintarLista(String[] palabras, int x, int y, int opcion) {
        for (int i = 0; i < palabras.length; i++) {
            if (i == opcion) {
                g2.drawString(">", x - 20, y);
            }
            sombra(palabras[i], Color.black, x, y, 3);
            pintarPalabra(palabras[i], x, y);
            y += panelJuego.tamannoRecuadros;
        }
    }

    /**
     * Carga el conjunto de ropa disponible según la opción seleccionada.
     *
     * @param opcion Índice de la opción de ropa a cargar.
     * @return Array de strings con los nombres de las prendas disponibles.
     */
    public String[] cargarCloset(int opcion) {
        String[] ropa = {"-", "-", "-"};
        if (opcion == 0) {
            ropa[0] = "Samuel";
            ropa[1] = "Samuel Deportivo";
            ropa[2] = "Samuel Elegante";

        } else if (opcion == 1) {
            ropa[0] = "Maria";
            ropa[1] = "Maria Barbie";
            ropa[2] = "Maria Elegante";
        } else if (opcion == 2) {
            ropa[0] = "Ignacio";
            ropa[1] = "Ignacio Argentina";
            ropa[2] = "Ignacio Elegante";
        } else if (opcion == 3) {
            ropa[0] = "Carlos";
            ropa[1] = "Carlos Pirata";
            ropa[2] = "Carlos Elegante";
        }
        return ropa;
    }

    /**
     * Obtiene el nombre de un personaje basado en su índice.
     *
     * @param i Índice del personaje.
     * @return Nombre del personaje correspondiente al índice.
     */
    public String cargarNombre(int i) {
        if (i == 0) {
            return "Samuel";
        } else if (i == 1) {
            return "Maria";
        } else if (i == 2) {
            return "Ignacio";
        } else if (i == 3) {
            return "Carlos";
        }
        return "";
    }

    /**
     * Carga el nombre de una prenda de vestir según el nombre del personaje y
     * la opción.
     *
     * @param nombre Nombre del personaje.
     * @param opcion Índice de la prenda de vestir a cargar.
     * @return Nombre de la prenda de vestir correspondiente al personaje y
     * opción.
     */
    public String cargarRopa(String nombre, int opcion) {
        String[] closetSamuel = {"Samuel", "Samuel Deportivo", "Samuel Elegante"};
        String[] closetMaria = {"Maria", "Maria Barbie", "Maria Elegante"};
        String[] closetIgnacio = {"Ignacio", "Ignacio Argentina", "Ignacio Elegante"};
        String[] closetCarlos = {"Carlos", "Carlos Pirata", "Carlos Elegante"};

        if (nombre.equalsIgnoreCase("Samuel")) {
            return closetSamuel[opcion];
        } else if (nombre.equalsIgnoreCase("Maria")) {
            return closetMaria[opcion];
        } else if (nombre.equalsIgnoreCase("Ignacio")) {
            return closetIgnacio[opcion];
        } else if (nombre.equalsIgnoreCase("Carlos")) {
            return closetCarlos[opcion];
        }
        return "";
    }

    /**
     * Dibuja una sombra detrás de una palabra en la pantalla.
     *
     * @param palabra Palabra a la que se aplicará la sombra.
     * @param color Color de la sombra.
     * @param x Coordenada X de la posición de la palabra.
     * @param y Coordenada Y de la posición de la palabra.
     * @param grosor Grosor de la sombra.
     */
    public void sombra(String palabra, Color color, int x, int y, int grosor) {
        g2.setColor(color);
        g2.drawString(palabra, x + grosor, y + grosor);
        g2.setColor(Color.WHITE);
    }
}
