package Entidades;

import EcoCrossing.net.paquetes.Paquete02Mover;
import EcoCrossing.net.paquetes.Paquete03AgregarObjeto;
import EcoCrossing.net.paquetes.Paquete04EliminarObjeto;
import Objeto.Basura;
import Objeto.Comida;
import Objeto.Electrodomestico;
import Objeto.SuperObjeto;
import Objeto.Valla;
import Personaje.Personaje;
import com.mycompany.ecocrossing.ManejoTeclas;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Clase que representa al jugador en el juego EcoCrossing.
 */
public class Jugador extends Entidad {

    ManejoTeclas manejoTeclas;
    public final int pantallaX, pantallaY;
    public int cntBasura = 0;
    public int cntMonedas = 20;
    public int cntComida = 0;
    public int cntElect = 0;
    private final String nombreUsuario;
    public String direccionAnterior, nombreSkin;
    public Personaje personaje;

    /**
     * Constructor de la clase Jugador.
     *
     * @param nombreUsuario Nombre de usuario del jugador.
     * @param nombreSkin Nombre de la skin del jugador.
     * @param panelJuego Panel de juego al que pertenece el jugador.
     * @param manejoTeclas Manejador de teclas para controlar el movimiento del
     * jugador.
     */
    public Jugador(String nombreUsuario, String nombreSkin, PanelJuego panelJuego, ManejoTeclas manejoTeclas) {
        super(panelJuego);
        this.manejoTeclas = manejoTeclas;
        this.nombreUsuario = nombreUsuario;
        this.nombreSkin = nombreSkin;
        this.pantallaX = panelJuego.anchoPantalla / 2 - (panelJuego.tamannoRecuadros / 2);
        this.pantallaY = panelJuego.largoPantalla / 2 - (panelJuego.tamannoRecuadros / 2);
        areaSolida = new Rectangle();
        areaSolida.x = 8;
        areaSolida.y = 16;
        areaSolidaDefaultX = areaSolida.x;
        areaSolidaDefaultY = areaSolida.y;
        areaSolida.width = 32;
        areaSolida.height = 32;
        setValoresporDefecto();
        setObjetos();
        // Inicializar la dirección anterior con la misma dirección inicial
        direccionAnterior = direccion;
    }

    /**
     * Establece los valores por defecto del jugador.
     */
    public void setValoresporDefecto() {
        mundoX = panelJuego.tamannoRecuadros * 50;
        mundoY = panelJuego.tamannoRecuadros * 30;
        velocidad = 4;
        direccion = "frente";

        // Estado del Jugador
        vidaMaxima = 6;
        vida = vidaMaxima;
    }

    /**
     * Reinicia los valores del jugador a los por defecto.
     */
    public void reiniciarJugador() {
        setValoresporDefecto();
        cntBasura = 0;
        cntMonedas = 20;
        inventario.clear();
    }

    /**
     * Configura los objetos iniciales del jugador.
     */
    public void setObjetos() {
        // Inicializar Objetos...
    }

    /**
     * Carga una skin específica para el jugador.
     *
     * @param nombre Nombre de la skin a cargar.
     */
    public void cargarSkin(String nombre) {
        this.nombreSkin = nombre;
        this.personaje = new Personaje(nombre);
        this.personaje.cargarVestimenta(this);
    }

    /**
     * Actualiza la posición y el estado del jugador en el juego.
     */
    public void actualizar() {
        if (manejoTeclas != null) {
            boolean moviendose = false;

            if (manejoTeclas.arribaPresionado || manejoTeclas.abajoPresionado || manejoTeclas.derechaPresionado || manejoTeclas.izquierdaPresionado) {
                moviendose = true;
                if (manejoTeclas.arribaPresionado) {
                    direccion = "arriba";
                } else if (manejoTeclas.izquierdaPresionado) {
                    direccion = "izquierda";
                } else if (manejoTeclas.abajoPresionado) {
                    direccion = "abajo";
                } else if (manejoTeclas.derechaPresionado) {
                    direccion = "derecha";
                }
                colisionActivada = false;

                // Colision de Objetos
                panelJuego.verificarC.VerificarRecuadro(this);
                int indiceObjeto = panelJuego.verificarC.VerificarObjeto(this, true);
                interactuarObjeto(indiceObjeto);

                // Colision de NPC'S
                int indiceNPC = panelJuego.verificarC.VerificarEntidad(this, panelJuego.npc);
                interactuarNPC(indiceNPC);

                // Verificar eventos
                panelJuego.manejoEventos.verificarEvento();

                if (!colisionActivada) {
                    switch (direccion) {
                        case "arriba":
                            mundoY -= velocidad;
                            break;
                        case "abajo":
                            mundoY += velocidad;
                            break;
                        case "derecha":
                            mundoX += velocidad;
                            break;
                        case "izquierda":
                            mundoX -= velocidad;
                            break;
                    }
                    Paquete02Mover paquete = new Paquete02Mover(this.getNombreUsuario(), mundoX, mundoY, spriteNum, direccion);
                    paquete.escribirDatos(this.panelJuego.clienteSocket);
                }
                actualizarAnimacion();
            }

            if (!moviendose) {
                if ("arriba".equals(direccion)) {
                    direccion = "espalda";
                } else if ("abajo".equals(direccion)) {
                    direccion = "frente";
                } else if ("derecha".equals(direccion)) {
                    direccion = "lado1";
                } else if ("izquierda".equals(direccion)) {
                    direccion = "lado2";
                }
            }

            // Verificar si la dirección ha cambiado antes de enviar el paquete
            if (!direccion.equals(direccionAnterior) && panelJuego.clienteSocket != null) {
                Paquete02Mover paquete = new Paquete02Mover(this.getNombreUsuario(), mundoX, mundoY, spriteNum, direccion);
                paquete.escribirDatos(this.panelJuego.clienteSocket);
                direccionAnterior = direccion;
            }
        }
    }

    /**
     * Gestiona la interacción del jugador con objetos en el juego.
     *
     * @param indice Índice del objeto con el que el jugador está interactuando.
     */
    public void interactuarObjeto(int indice) {
        if (indice != 999 && panelJuego.obj[panelJuego.mapaActual][indice] != null) {

            switch (panelJuego.obj[panelJuego.mapaActual][indice].nombre) {
                case "papelera":
                    if (manejoTeclas.interacturaObjetoPresionado && cntBasura > 0) {
                        panelJuego.reproducirEfectosSonido(1);
                        cntBasura--;
                        for (SuperObjeto so : inventario) {
                            if (so instanceof Basura) {
                                inventario.remove(so);
                                break;
                            }
                        }
                        panelJuego.ui.mostrarMensaje("Botaste la basura!");
                    }
                    break;
                case "cerca_4":
                    if (manejoTeclas.interacturaObjetoPresionado && panelJuego.obj[panelJuego.mapaActual][indice] instanceof Valla valla && panelJuego.manejoEventos.verificacionLlave()) {
                        panelJuego.reproducirEfectosSonido(1);
                        if (!valla.abierto && valla.mundoX == 2544 && valla.mundoY == 2496) {
                            valla.abrir(panelJuego.tamannoRecuadros, "cerca_2");
                            panelJuego.estadoJuego = panelJuego.estadoDialogo;
                            panelJuego.ui.dialogoActual = "\n               La valla se ha abierto.";
                            panelJuego.ui.dibujarPantallaDialogo();
                            Paquete03AgregarObjeto paquete = new Paquete03AgregarObjeto("cerca_2", valla.mundoX, valla.mundoY, indice, 0);
                            if (panelJuego.clienteSocket != null) {
                                paquete.escribirDatos(panelJuego.clienteSocket);
                            }
                        }
                        if (!valla.abierto && valla.mundoX == 2592 && valla.mundoY == 2496) {
                            valla.abrir(panelJuego.tamannoRecuadros, "cerca_7");
                            panelJuego.estadoJuego = panelJuego.estadoDialogo;
                            panelJuego.ui.dialogoActual = "\n               La valla se ha abierto.";
                            panelJuego.ui.dibujarPantallaDialogo();
                            Paquete03AgregarObjeto paquete = new Paquete03AgregarObjeto("cerca_7", valla.mundoX, valla.mundoY, indice, 0);
                            if (panelJuego.clienteSocket != null) {
                                paquete.escribirDatos(panelJuego.clienteSocket);
                            }
                        } else {
                            panelJuego.estadoJuego = panelJuego.estadoDialogo;
                            panelJuego.ui.dialogoActual = "\n               Ya está abierta.";
                        }
                    }
                    break;
                case "mesa":
                    if (manejoTeclas.interacturaObjetoPresionado) {
                        if (panelJuego.jugador.vida < 6 && cntComida > 0) {
                            panelJuego.reproducirEfectosSonido(1);
                            panelJuego.ui.mostrarMensaje("Buen Provecho!!!");
                            for (SuperObjeto so : inventario) {
                                if (so instanceof Comida) {
                                    panelJuego.jugador.vida++;
                                    inventario.remove(so);
                                    break;
                                }
                            }
                        } else if (panelJuego.jugador.vida == 6) {
                            panelJuego.ui.mostrarMensaje("Estoy lleno en estos momentos...");
                        } else if (cntComida <= 0) {
                            panelJuego.ui.mostrarMensaje("No tengo comida...");
                        }
                    }
                    break;
                case "banana":
                    if (manejoTeclas.interacturaObjetoPresionado) {
                        if (inventario.size() < tamannoMaxInventario) {
                            agregarBasura("banana");
                            panelJuego.obj[panelJuego.mapaActual][indice] = null;
                            panelJuego.reproducirEfectosSonido(1);
                            cntBasura++;
                            panelJuego.ui.mostrarMensaje("Conseguiste banana!");
                            // Crear y enviar el paquete para eliminar el objeto en todos los clientes
                            Paquete04EliminarObjeto paqueteEliminar = new Paquete04EliminarObjeto(indice, panelJuego.mapaActual);
                            paqueteEliminar.escribirDatos(this.panelJuego.clienteSocket);
                        } else {
                            panelJuego.ui.mostrarMensaje("Inventario lleno...");
                        }
                    }
                    break;
                case "carton":
                    if (manejoTeclas.interacturaObjetoPresionado) {
                        if (inventario.size() < tamannoMaxInventario) {
                            agregarBasura("carton");
                            panelJuego.obj[panelJuego.mapaActual][indice] = null;
                            panelJuego.reproducirEfectosSonido(1);
                            cntBasura++;
                            panelJuego.ui.mostrarMensaje("Conseguiste carton!");
                            // Crear y enviar el paquete para eliminar el objeto en todos los clientes
                            Paquete04EliminarObjeto paqueteEliminar = new Paquete04EliminarObjeto(indice, panelJuego.mapaActual);
                            paqueteEliminar.escribirDatos(this.panelJuego.clienteSocket);
                        } else {
                            panelJuego.ui.mostrarMensaje("Inventario lleno...");
                        }

                    }
                    break;
                case "lata":
                    if (manejoTeclas.interacturaObjetoPresionado) {
                        if (inventario.size() < tamannoMaxInventario) {
                            agregarBasura("lata");
                            panelJuego.obj[panelJuego.mapaActual][indice] = null;
                            panelJuego.reproducirEfectosSonido(1);
                            cntBasura++;
                            panelJuego.ui.mostrarMensaje("Conseguiste lata!");
                            // Crear y enviar el paquete para eliminar el objeto en todos los clientes
                            Paquete04EliminarObjeto paqueteEliminar = new Paquete04EliminarObjeto(indice, panelJuego.mapaActual);
                            paqueteEliminar.escribirDatos(this.panelJuego.clienteSocket);
                        } else {
                            panelJuego.ui.mostrarMensaje("Inventario lleno...");
                        }
                    }
                    break;
                case "papel":
                    if (manejoTeclas.interacturaObjetoPresionado) {
                        if (inventario.size() < tamannoMaxInventario) {
                            agregarBasura("papel");
                            panelJuego.obj[panelJuego.mapaActual][indice] = null;
                            panelJuego.reproducirEfectosSonido(1);
                            cntBasura++;
                            panelJuego.ui.mostrarMensaje("Conseguiste papel!");
                            // Crear y enviar el paquete para eliminar el objeto en todos los clientes
                            Paquete04EliminarObjeto paqueteEliminar = new Paquete04EliminarObjeto(indice, panelJuego.mapaActual);
                            paqueteEliminar.escribirDatos(this.panelJuego.clienteSocket);
                        } else {
                            panelJuego.ui.mostrarMensaje("Inventario lleno...");
                        }
                    }
                    break;
                case "Tortuga con Plastico":
                    if (manejoTeclas.interacturaObjetoPresionado) {
                        if (inventario.size() < tamannoMaxInventario) {
                            agregarTortuga();
                            panelJuego.obj[panelJuego.mapaActual][indice] = null;
                            panelJuego.reproducirEfectosSonido(1);
                            panelJuego.ui.mostrarMensaje("Recogiste a la tortuga!");
                            // Crear y enviar el paquete para eliminar el objeto en todos los clientes
                            Paquete04EliminarObjeto paqueteEliminar = new Paquete04EliminarObjeto(indice, panelJuego.mapaActual);
                            paqueteEliminar.escribirDatos(this.panelJuego.clienteSocket);
                        } else {
                            panelJuego.ui.mostrarMensaje("Inventario lleno...");
                        }
                    }
                    break;
                case "lampara", "microondas":
                    panelJuego.reproducirEfectosSonido(1);
                    if (cntElect < 5 && panelJuego.obj[panelJuego.mapaActual][indice] instanceof Electrodomestico elect) {
                        if (elect.encendido) {
                            elect.apagar(panelJuego.tamannoRecuadros);
                            panelJuego.estadoJuego = panelJuego.estadoDialogo;
                            panelJuego.ui.dialogoActual = "\n          " + elect.nombre + " se ha apagado.";
                        } else {
                            panelJuego.estadoJuego = panelJuego.estadoDialogo;
                            panelJuego.ui.dialogoActual = "\n               Ya está apagado.";
                        }
                        panelJuego.ui.dibujarPantallaDialogo();
                        if (panelJuego.administradorRecuadros.todasLamparasApagadas() && !panelJuego.manejoEventos.mision3) {
                            if (panelJuego.jugador.vida > 0) {
                                panelJuego.ui.mostrarMensaje("Has apagado todas las lamparas! (+5 Monedas -1 Hambre)");
                            }
                            panelJuego.jugador.cntMonedas += 5;
                            panelJuego.jugador.disminuirVida();
                            panelJuego.manejoEventos.mision3 = true;
                        }
                        Paquete03AgregarObjeto paquete = new Paquete03AgregarObjeto(elect.nombre + "OFF", elect.mundoX, elect.mundoY, indice, 2);
                        if (panelJuego.clienteSocket != null) {
                            paquete.escribirDatos(panelJuego.clienteSocket);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Gestiona la interacción del jugador con NPCs en el juego.
     *
     * @param indice Índice del NPC con el que el jugador está interactuando.
     */
    public void interactuarNPC(int indice) {
        if (indice != 999) {
            if (manejoTeclas.hablarNPCPresionado) {
                panelJuego.estadoJuego = panelJuego.estadoDialogo;
                panelJuego.npc[panelJuego.mapaActual][indice].Hablar();

                // Enviar la direccion actualizada del npc al servidor
                Paquete02Mover paqueteMovimientoNPC = new Paquete02Mover(panelJuego.npc[panelJuego.mapaActual][indice].getClass().getName(), panelJuego.npc[panelJuego.mapaActual][indice].mundoX,
                        panelJuego.npc[panelJuego.mapaActual][indice].mundoY, panelJuego.npc[panelJuego.mapaActual][indice].spriteNum, panelJuego.npc[panelJuego.mapaActual][indice].direccion);
                if (panelJuego.clienteSocket != null) {
                    paqueteMovimientoNPC.escribirDatos(panelJuego.clienteSocket);
                }
            }
        }
    }

    /**
     * Actualiza la animación del jugador según su dirección y estado.
     */
    public void actualizarAnimacion() {
        spriteCont++;
        if (spriteCont > 12) {
            if (spriteNum == 2) {
                spriteNum = 1;
            } else if (spriteNum == 1) {
                spriteNum = 2;
            }
            spriteCont = 0;
        }
    }

    /**
     * Devuelve el nombre de usuario del jugador.
     *
     * @return Nombre de usuario del jugador.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Dibuja al jugador en la pantalla de juego.
     *
     * @param g2 Objeto Graphics2D para dibujar.
     */
    @Override
    public void dibujar(Graphics2D g2) {
        BufferedImage imagen = frente;
        switch (direccion) {
            case "arriba":
                if (spriteNum == 1) {
                    imagen = arriba1;
                } else if (spriteNum == 2) {
                    imagen = arriba2;
                }
                break;
            case "abajo":
                if (spriteNum == 1) {
                    imagen = abajo1;
                } else if (spriteNum == 2) {
                    imagen = abajo2;
                }
                break;
            case "derecha":
                if (spriteNum == 1) {
                    imagen = der1;
                } else if (spriteNum == 2) {
                    imagen = der2;
                }
                break;
            case "izquierda":
                if (spriteNum == 1) {
                    imagen = izq1;
                } else if (spriteNum == 2) {
                    imagen = izq2;
                }
                break;
            case "frente":
                imagen = frente;
                break;
            case "espalda":
                imagen = espalda;
                break;
            case "lado1":
                imagen = lado1;
                break;
            case "lado2":
                imagen = lado2;
                break;
        }

        int pantallaX = this.mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
        int pantallaY = this.mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;

        g2.drawImage(imagen, pantallaX, pantallaY, null);

        if (nombreUsuario != null) {
            // Obtener dimensiones del texto
            g2.setFont(new Font("Courier New", Font.BOLD, 18));
            FontMetrics metrics = g2.getFontMetrics();
            int nombreWidth = metrics.stringWidth(nombreUsuario);
            int nombreHeight = metrics.getHeight();

            // Calcular posición para el rectángulo sombreado
            int nombreX = pantallaX + (panelJuego.tamannoRecuadros / 2) - (nombreWidth / 2);
            int nombreY = pantallaY - 15; // Ajuste para posicionar más cerca de la cabeza

            // Dibujar rectángulo sombreado translúcido
            g2.setColor(new Color(0, 0, 0, 150)); // Color negro con transparencia
            g2.fillRect(nombreX - 2, nombreY - metrics.getAscent() - 2, nombreWidth + 4, nombreHeight + 4);

            // Dibujar texto principal
            g2.setColor(Color.WHITE);
            g2.drawString(nombreUsuario, nombreX, nombreY);
        }
    }

    /**
     * Disminuye la vida del jugador y gestiona acciones cuando la vida llega a
     * cero.
     */
    public void disminuirVida() {
        vida--;
        if (vida <= 0) {
            panelJuego.estadoJuego = panelJuego.estadoJuegoTerminado;
        }
    }
}
