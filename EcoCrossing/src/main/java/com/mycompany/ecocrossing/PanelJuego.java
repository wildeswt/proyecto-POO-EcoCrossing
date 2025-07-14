package com.mycompany.ecocrossing;

import EcoCrossing.net.*;
import EcoCrossing.net.paquetes.Paquete00Acceder;
import Entidades.*;
import Objeto.Basura;
import Objeto.Electrodomestico;
import Objeto.SuperObjeto;
import Objeto.Tortuga;
import Objeto.TortugaPlastico;
import Objeto.Valla;
import Personaje.Personaje;
import Recuadros.AdministradorRecuadros;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.net.DatagramSocket;
import javax.swing.JLabel;

/**
 * Clase que se encarga del panel donde se desarrolla el juego.
 *
 * @author Ignacio Aliendres, Carlos Méndez, Samuel Gúzman y María Sandoval
 */
public class PanelJuego extends JPanel implements Runnable {

    // Atributos de la pantalla
    final int recuadros = 16;
    final int escala = 3; // Escala de los recuadros
    public final int tamannoRecuadros = recuadros * escala; // Tamaño total de los recuadros
    public final int maxColumnas = 16; // Número máximo de columnas en la pantalla
    public final int maxFilas = 12; // Número máximo de filas en la pantalla
    public final int anchoPantalla = tamannoRecuadros * maxColumnas; // Ancho total de la pantalla
    public final int largoPantalla = tamannoRecuadros * maxFilas; // Alto total de la pantalla

    // Atributos y ajustes del mundo del juego
    // ***** Ajustar según el tamaño exacto del mapa del juego *****
    public final int maxColumnasMundo = 86; // Número máximo de columnas del mundo del juego
    public final int maxFilasMundo = 80; // Número máximo de filas del mundo del juego
    public final int anchoMundo = tamannoRecuadros * maxColumnasMundo; // Ancho total del mundo del juego
    public final int alturaMundo = tamannoRecuadros * maxFilasMundo; // Alto total del mundo del juego
    public final int maximoMapas = 10; // Cantidad total de mapas que se puedan crear 
    public int mapaActual = 0;  // Mapa actual del juego

    // FPS
    int FPS = 60;

    // Atributos del juego
    public AdministradorRecuadros administradorRecuadros = new AdministradorRecuadros(this);
    public ManejoTeclas manejoTeclas = new ManejoTeclas(this);
    Sonido musica = new Sonido(); // Reproductor de música
    Sonido efectosSonido = new Sonido(); // Reproductor de efectos de sonido
    public VerificarColision verificarC = new VerificarColision(this); // Verificador de colisiones
    public AssetSetter aSetter = new AssetSetter(this); // Configurador de assets
    public UI ui = new UI(this); // Interfaz de usuario del juego
    public ManejoEventos manejoEventos = new ManejoEventos(this);
    Thread juegoThread; // Hilo principal del juego

    // Entidades y objetos del juego
    public Jugador jugador = new Jugador("", "", this, manejoTeclas); // Jugador principal
    public SuperObjeto obj[][] = new SuperObjeto[maximoMapas][50]; // Arreglo de objetos del juego ordenados en mapas
    public Entidad npc[][] = new Entidad[maximoMapas][5]; // Arreglo npc's del juego ordenados en mapas

    // Estado del juego
    public int estadoJuego;
    public final int estadoTitulo = 0;
    public final int estadoJugando = 1;
    public final int estadoPausa = 2;
    public final int estadoDialogo = 3;
    public final int estadoPersonaje = 4;
    public final int estadoTradeo = 5;
    public final int estadoJuegoTerminado = 6;

    // Atributos multijugador
    public ArrayList<JugadorMultijugador> jugadoresConectados = new ArrayList<>();
    public ClienteJuego clienteSocket;
    public ServidorJuego servidorSocket;
    public JLabel ipLabel;

    /**
     * Constructor del panel donde se desarrollará el juego.
     */
    public PanelJuego() {
        this.setPreferredSize(new Dimension(anchoPantalla, largoPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(manejoTeclas); // Agregar el manejador de teclas al panel
        this.setFocusable(true);
        this.setLayout(null);

        // Inicializar la etiqueta para mostrar la IP
        ipLabel = new JLabel();
        ipLabel.setForeground(Color.WHITE);
        ipLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ipLabel.setBounds(10, 10, 300, 20); // Posicionar ipLabel en la esquina superior izquierda
        this.add(ipLabel); // Añadir ipLabel al PanelJuego
    }

    /**
     * Obtiene de manera segura la lista de jugadores conectados (Evitar un
     * ConcurrentModificationException).
     *
     * @return una lista de jugadores conectados
     */
    public synchronized ArrayList<JugadorMultijugador> getJugadoresConectados() {
        return this.jugadoresConectados;
    }

    /**
     * Cambia la dirección ip del panel.
     *
     * @param direccionIP dirección ip de servidor
     */
    public void setIPText(String direccionIP) {
        ipLabel.setText(direccionIP); // Forzar la actualización del panel
    }

    /**
     * Configura y prepara el juego para comenzar.
     */
    public void setupJuego() {
        aSetter.setObjeto();
        aSetter.setNPC();
        // reproducirMusica(0); // Reproducir el tema principal
        estadoJuego = estadoTitulo;
    }

    /**
     * Agrega un jugador a la lista de jugadores conectados.
     *
     * @param jugador jugador a agregar
     */
    public void agregarJugador(JugadorMultijugador jugador) {
        getJugadoresConectados().add(jugador);
        repaint(); // Actualizar el panel
    }

    /**
     * Elimina un jugador de la lista de jugadores conectados.
     *
     * @param nombreUsuario
     */
    public void eliminarJugador(String nombreUsuario) {
        for (JugadorMultijugador j : this.getJugadoresConectados()) {
            if (j.getNombreUsuario().equals(nombreUsuario)) {
                this.getJugadoresConectados().remove(j);
                break;
            }
        }
    }

    /**
     * Regenera al jugador actual.
     */
    public void regenerar() {
        jugador.reiniciarJugador();
    }

    /**
     * Reinicia el estado del juego y sus conexiones.
     */
    public void reiniciar() {
        if (servidorSocket != null) {
            servidorSocket.detener();
            DatagramSocket serverSocket = servidorSocket.getSocket();
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }

        if (clienteSocket != null) {
            clienteSocket.detener();
            DatagramSocket clientSocket = clienteSocket.getSocket();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }

        detenerJuego();

        servidorSocket = null;
        clienteSocket = null;

        AssetSetter.indiceObjeto = 0; // Reiniciar índice de objetos
        setupJuego(); // Configurar el juego nuevamente
        regenerar(); // Regenerar objetos del juego
        ui = new UI(this);
        manejoEventos = new ManejoEventos(this);
        jugadoresConectados.clear();

        preInicioJuego(); // Iniciar el hilo principal del juego
    }

    /**
     * Agrega un objeto basura en una ubicación específica del mapa.
     *
     * @param nombre El nombre del objeto basura a agregar.
     * @param x La coordenada x del objeto en el mundo.
     * @param y La coordenada y del objeto en el mundo.
     * @param indice El índice del objeto en la matriz del mapa.
     * @param mapa El índice del mapa donde se agrega el objeto.
     */
    public void agregarObjeto(String nombre, int x, int y, int indice, int mapa) {
        switch (nombre) {
            case "banana", "carton", "lata", "papel", "papelera" -> {
                obj[mapa][indice] = new Basura(nombre);
                obj[mapa][indice].mundoX = x;
                obj[mapa][indice].mundoY = y;
                // Cargar la imagen del objeto
                ((Basura) obj[mapa][indice]).cargar(escala);
            }
            case "tortuga" -> {
                obj[mapa][indice] = new Tortuga();
                obj[mapa][indice].mundoX = x;
                obj[mapa][indice].mundoY = y;
                // Cargar la imagen del objeto
                ((Tortuga) obj[mapa][indice]).cargar(escala);
            }
            case "Tortuga con Plastico" -> {
                obj[mapa][indice] = new TortugaPlastico();
                obj[mapa][indice].mundoX = x;
                obj[mapa][indice].mundoY = y;
                // Cargar la imagen del objeto
                ((TortugaPlastico) obj[mapa][indice]).cargar(escala);
            }
            case "lamparaON" -> {
                // Cargar la imagen del objeto
                ((Electrodomestico) obj[mapa][indice]).encender(tamannoRecuadros);
            }
            case "lamparaOFF" -> {
                // Cargar la imagen del objeto
                ((Electrodomestico) obj[mapa][indice]).apagar(tamannoRecuadros);
            }
            case "cerca_2" -> {
                ((Valla) obj[mapa][indice]).abrir(tamannoRecuadros, nombre);
            }
            case "cerca_7" -> {
                ((Valla) obj[mapa][indice]).abrir(tamannoRecuadros, nombre);
            }
            default -> {
            }
        }
        repaint();
    }

    /**
     * Maneja la eliminación de un objeto del juego.
     *
     * @param indice El índice del objeto.
     * @param mapa El índice que representa al mapa.
     */
    public void eliminarObjeto(int indice, int mapa) {
        obj[mapa][indice] = null;
        repaint();
    }

    /**
     * Antes de ejecutar iniciarjuegoThread().
     */
    public void preInicioJuego() {
        juegoThread = new Thread(this);
        juegoThread.start();
    }

    /**
     * Inicia el hilo principal del juego y maneja la conexión del servidor y el
     * cliente.
     */
    public void iniciarjuegoThread() {
        String servidorIP = "";
        if (JOptionPane.showConfirmDialog(this, "Desea iniciar el servidor?") == 0) {
            // Iniciar servidor de juego
            servidorSocket = new ServidorJuego(this);
            servidorSocket.start();
            servidorIP = servidorSocket.obtenerIPLocal();
            JOptionPane.showMessageDialog(this, "Servidor iniciado. IP del servidor: " + servidorIP);
            setIPText("IP del servidor: " + servidorIP); // Actualizar el texto con la IP del servidor
        } else {
            // Conectar a un servidor existente
            servidorIP = JOptionPane.showInputDialog(this, "Ingrese la IP del servidor:", "localhost");
            setIPText("IP del servidor: " + servidorIP); // Actualizar el texto con la IP del servidor
        }

        // Crear jugador multijugador y agregarlo
        //Guardamos la configuración de los personajes 
        Personaje personaje = jugador.personaje;

        jugador = new JugadorMultijugador(JOptionPane.showInputDialog(this, "Nombre de usuario: "), jugador.nombreSkin, this, manejoTeclas, null, -1);
        agregarJugador((JugadorMultijugador) jugador);

        //restablece los datos de los personajes 
        jugador.cargarSkin(personaje.getNombre());

        Paquete00Acceder accederPaquete = new Paquete00Acceder(jugador.getNombreUsuario(), jugador.mundoX, jugador.mundoY, jugador.direccion, jugador.nombreSkin);

        // Agregar conexión al servidor si está disponible
        if (servidorSocket != null) {
            servidorSocket.agregarConexion((JugadorMultijugador) jugador, accederPaquete);
        }

        // Iniciar cliente para conectar al servidor
        clienteSocket = new ClienteJuego(this, servidorIP);
        clienteSocket.start();
        accederPaquete.escribirDatos(clienteSocket);
    }

    /**
     * Maneja la actualización del panel.
     */
    @Override
    public void run() {
        // Intervalo de tiempo para el dibujado
        double intervaloDibujado = 1000000000 / FPS;
        double delta = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempoActual;

        while (juegoThread != null && !juegoThread.isInterrupted()) {
            tiempoActual = System.nanoTime();
            delta += (tiempoActual - ultimoTiempo) / intervaloDibujado;
            ultimoTiempo = tiempoActual;

            if (delta >= 1) {
                // Actualizar la información del juego
                actualizar();
                // Redibujar la pantalla
                repaint();
                delta--;
            }
        }
    }

    /**
     * Detiene el hilo principal del juego.
     */
    public void detenerJuego() {
        if (juegoThread != null) {
            juegoThread.interrupt(); // Interrumpir el hilo principal del juego
            juegoThread = null; // Establecer juegoThread a null para indicar que ha terminado
        }
    }

    /**
     * Actualizar el estado de los jugadores y NPCs según el estado del juego.
     */
    public void actualizar() {
        if (estadoJuego == estadoJugando) {
            // Actualizar jugadores conectados
            for (JugadorMultijugador jugador : getJugadoresConectados()) {
                jugador.actualizar();
            }

            // Actualizar NPCs
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[mapaActual][i] != null) {
                    npc[mapaActual][i].Actualizar(); // Asumiendo que 'Actualizar' está correctamente implementado en 'Entidad'
                }
            }
        } else if (estadoJuego == estadoPausa) {
            // No hacer nada si el juego está en pausa
        }

        repaint(); // Actualizar el panel después de procesar las actualizaciones
    }

    /**
     * Dibuja los elementos del juego en el panel.
     *
     * @param g El contexto gráfico utilizado para dibujar.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Titulo del Juego:
        if (estadoJuego == estadoTitulo) {
            ui.dibujar(g2);
        } // Otros: (Ojo estar pendiente con lo que va a ir aquí)
        else {
            // Dibujar el mapa, objetos y npc's del juego
            administradorRecuadros.dibujar(g2);

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapaActual][i] != null) {
                    obj[mapaActual][i].dibujar(g2, this);
                }
            }

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[mapaActual][i] != null) {
                    npc[mapaActual][i].dibujar(g2);
                }
            }

            // Dibujar jugadores conectados
            for (JugadorMultijugador j : getJugadoresConectados()) {
                j.dibujar(g2);
            }

            // Mostrar la etiqueta de IP en la pantalla
            ipLabel.paint(g2);

            // Dibujar la interfaz de usuario
            ui.dibujar(g2);
        }

        g2.dispose();
    }

    /**
     * Reproduce una pista de música especificada.
     *
     * @param i El índice de la pista de música a reproducir.
     */
    public void reproducirMusica(int i) {
        musica.colocarArchivo(i);
        musica.reproducir();
        musica.bucle();
    }

    /**
     * Detiene la música.
     */
    public void pararMusica() {
        musica.parar();
    }

    /**
     * Reproduce un efecto de sonido especificado.
     *
     * @param i El índice del efecto de sonido a reproducir.
     */
    public void reproducirEfectosSonido(int i) {
        efectosSonido.colocarArchivo(i);
        efectosSonido.reproducir();
    }

    /**
     * Busca el índice de un jugador multijugador por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario del jugador multijugador.
     * @return El índice del jugador multijugador en la lista de jugadores
     * conectados.
     */
    private int buscarIndiceJugadorMultijugador(String nombreUsuario) {
        int indice = 0;
        for (JugadorMultijugador j : getJugadoresConectados()) {
            if (j.getNombreUsuario().equals(nombreUsuario)) {
                break;
            }
            indice++;
        }
        return indice;
    }

    /**
     * Busca un NPC por su nombre.
     *
     * @param nombre El nombre del NPC a buscar.
     * @return El NPC encontrado, o null si no se encuentra.
     */
    public Entidad buscarNPC(String nombre) {
        for (int i = 0; i < npc[1].length; i++) {
            if (npc[mapaActual][i] != null && npc[mapaActual][i].getClass().getName().equals(nombre)) {
                return npc[mapaActual][i];
            }
        }
        return null;
    }

    /**
     * Mueve un jugador multijugador a las coordenadas especificadas.
     *
     * @param nombreUsuario El nombre de usuario del jugador multijugador.
     * @param x La nueva coordenada x del jugador en el mundo.
     * @param y La nueva coordenada y del jugador en el mundo.
     */
    public void moverJugador(String nombreUsuario, int x, int y) {
        int indice = buscarIndiceJugadorMultijugador(nombreUsuario);
        this.getJugadoresConectados().get(indice).mundoX = x;
        this.getJugadoresConectados().get(indice).mundoY = y;
    }
}
