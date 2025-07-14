package Entidades;

import Objeto.Basura;
import Objeto.Comida;
import Objeto.Flyer;
import Objeto.Llave;
import Objeto.SuperObjeto;
import Objeto.TortugaPlastico;
import com.mycompany.ecocrossing.EscaladorImagen;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * La clase Entidad representa entidades en el juego con capacidades de
 * movimiento, interacción y gestión de inventario.
 */
public class Entidad {

    public PanelJuego panelJuego;
    public int mundoX, mundoY, velocidad, interacciones = 0;
    public BufferedImage frente, espalda, arriba1, arriba2, abajo1, abajo2, lado1, der1, der2, lado2, izq1, izq2;
    public String direccion, mostrar;
    public int spriteCont = 0;
    public int spriteNum = 1;
    public Rectangle areaSolida = new Rectangle(0, 0, 48, 48);
    public int areaSolidaDefaultX, areaSolidaDefaultY;
    public boolean colisionActivada = false;
    public boolean enMovimiento;
    public int contAccion;
    public String dialogos[] = new String[20];
    public int indiceDialogo = 0;
    public ArrayList<SuperObjeto> inventario = new ArrayList<>();
    public final int tamannoMaxInventario = 20;

    // Estado del personaje
    public int vidaMaxima, vida;

    /**
     * Constructor de Entidad que inicializa la entidad con el panel de juego
     * asociado.
     *
     * @param panelJuego El panel de juego al que pertenece la entidad.
     */
    public Entidad(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    // Getters y setters omitidos por brevedad
    /**
     * Método para que la entidad hable, gestionando diálogos y acciones según
     * la interacción.
     */
    public void Hablar() {
        // Caso especial del primer NPC
        if (this instanceof NPC_1) {
            if (!panelJuego.administradorRecuadros.hayBasura() && dialogos[indiceDialogo] == null) {
                indiceDialogo = 3;
            } else if (panelJuego.administradorRecuadros.hayBasura() && indiceDialogo == 3) {
                indiceDialogo = 2;
            } else if (panelJuego.administradorRecuadros.hayBasura() && dialogos[indiceDialogo] == null) {
                indiceDialogo = 1;
            }
        } // Caso especial del segundo NPC
        else if (this instanceof NPC_2) {
            if (!panelJuego.administradorRecuadros.hayBasuraAlrededorFuente() && dialogos[indiceDialogo] == null) {
                indiceDialogo = 4;
            } else if (panelJuego.administradorRecuadros.hayBasuraAlrededorFuente() && indiceDialogo == 4) {
                indiceDialogo = 3;
            }
        } // General para los demás NPCs
        else if (dialogos[indiceDialogo] == null) {
            indiceDialogo = 0;
        }

        panelJuego.ui.dialogoActual = dialogos[indiceDialogo];
        indiceDialogo++;
        interacciones++;

        switch (panelJuego.jugador.direccion) {
            case "arriba":
                direccion = "frente";
                panelJuego.jugador.direccion = "espalda";
                break;
            case "abajo":
                direccion = "espalda";
                panelJuego.jugador.direccion = "frente";
                break;
            case "izquierda":
                direccion = "lado1";
                panelJuego.jugador.direccion = "lado2";
                break;
            case "derecha":
                direccion = "lado2";
                panelJuego.jugador.direccion = "lado1";
                break;
        }
    }

    /**
     * Actualiza la posición y acciones de la entidad en el juego.
     */
    public void Actualizar() {
        setAccion();
        colisionActivada = false;

        // Verificar colisiones
        panelJuego.verificarC.VerificarRecuadro(this);
        panelJuego.verificarC.VerificarObjeto(this, false);
        panelJuego.verificarC.VerificarJugador(this);

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
        }

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
     * Dibuja la entidad en el juego.
     *
     * @param g2 El contexto gráfico en el que se dibuja la entidad.
     */
    public void dibujar(Graphics2D g2) {
        BufferedImage imagen = null;
        int pantallaX = mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
        int pantallaY = mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;

        if (mundoX + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoX - panelJuego.jugador.pantallaX
                && mundoX - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX
                && mundoY + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoY - panelJuego.jugador.pantallaY
                && mundoY - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY) {

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

            g2.drawImage(imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
        }
    }

    /**
     * Redimensiona una imagen según el tamaño del objeto.
     *
     * @param original La imagen original a redimensionar.
     * @param superObjeto El objeto que determina el tamaño de la imagen
     * redimensionada.
     * @return La imagen redimensionada.
     */
    private BufferedImage redimensionarImagen(BufferedImage original, SuperObjeto superObjeto) {
        int nuevoAncho = superObjeto.imagen.getWidth(null) * 3;
        int nuevoAlto = superObjeto.imagen.getHeight(null) * 3;
        BufferedImage imagenRedimensionada = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagenRedimensionada.createGraphics();
        g2d.drawImage(original, 0, 0, nuevoAncho, nuevoAlto, null);
        g2d.dispose();
        return imagenRedimensionada;
    }

    /**
     * Agrega un objeto de tipo Basura al inventario.
     *
     * @param nombre El nombre del objeto de basura.
     */
    public void agregarBasura(String nombre) {
        Basura basura = new Basura(nombre);
        inventario.add(basura);
        basura.cargar(panelJuego.tamannoRecuadros);
        BufferedImage imagenRedimensionada = redimensionarImagen(basura.imagen, basura);
        basura.imagen = imagenRedimensionada;
    }

    /**
     * Agrega una tortuga de plástico al inventario.
     */
    public void agregarTortuga() {
        TortugaPlastico tortugaPlastico = new TortugaPlastico();
        inventario.add(tortugaPlastico);
        tortugaPlastico.cargar(panelJuego.tamannoRecuadros);
        BufferedImage imagenRedimensionada = redimensionarImagen(tortugaPlastico.imagen, tortugaPlastico);
        tortugaPlastico.imagen = imagenRedimensionada;
    }

    /**
     * Agrega un objeto de comida al inventario.
     *
     * @param nombre El nombre del objeto de comida.
     */
    public void agregarComida(String nombre) {
        Comida comida = new Comida(nombre);
        inventario.add(comida);
        comida.cargar(panelJuego.tamannoRecuadros);
        BufferedImage imagenRedimensionada = redimensionarImagen(comida.imagen, comida);
        comida.imagen = imagenRedimensionada;
    }

    /**
     * Agrega un flyer al inventario.
     *
     * @param nombre El nombre del flyer.
     */
    public void agregarFlyer(String nombre) {
        Flyer flyer = new Flyer(nombre);
        inventario.add(flyer);
        flyer.cargar(panelJuego.tamannoRecuadros);
        BufferedImage imagenRedimensionada = redimensionarImagen(flyer.imagen, flyer);
        flyer.imagen = imagenRedimensionada;
    }

    /**
     * Agrega una llave al inventario.
     */
    public void agregarLlave() {
        Llave llave = new Llave();
        inventario.add(llave);
        llave.cargar(panelJuego.tamannoRecuadros);
        BufferedImage imagenRedimensionada = redimensionarImagen(llave.imagen, llave);
        llave.imagen = imagenRedimensionada;
    }

    /**
     * Configura una imagen cargada según el nombre proporcionado.
     *
     * @param nombreImagen El nombre del archivo de imagen a cargar.
     * @return La imagen configurada según el tamaño del panel de juego.
     */
    public BufferedImage configurar(String nombreImagen) {
        EscaladorImagen herramientaUtil = new EscaladorImagen();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("" + nombreImagen + ".png"));
            imagen = herramientaUtil.escalarImagen(imagen, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen: " + nombreImagen);
            System.out.println(e.getMessage());
        }
        return imagen;
    }

    /**
     * Realiza una acción específica para la entidad.
     */
    public void setAccion() {}

    /**
     * Realiza una acción de movimiento para la entidad.
     *
     * @param enMovimiento Indica si la entidad está en movimiento.
     */
    public void setEnMovimiento(boolean enMovimiento) {
        this.enMovimiento = enMovimiento;
    }

    /**
     * Establece la posición en el eje X del mundo para la entidad.
     *
     * @param mundoX La posición en el eje X del mundo.
     */
    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }

    /**
     * Establece la posición en el eje Y del mundo para la entidad.
     *
     * @param mundoY La posición en el eje Y del mundo.
     */
    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }

    /**
     * Obtiene el número de sprites para la animación de la entidad.
     *
     * @return El número de sprites.
     */
    public int getSpriteNum() {
        return spriteNum;
    }

    /**
     * Establece el número de sprites para la animación de la entidad.
     *
     * @param spriteNum El número de sprites.
     */
    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    /**
     * Obtiene el contador de sprites para la animación de la entidad.
     *
     * @return El contador de sprites.
     */
    public int getSpriteCont() {
        return spriteCont;
    }

    /**
     * Establece el contador de sprites para la animación de la entidad.
     *
     * @param spriteCont El contador de sprites.
     */
    public void setSpriteCont(int spriteCont) {
        this.spriteCont = spriteCont;
    }

    /**
     * Establece la dirección actual de la entidad.
     *
     * @param direccion La dirección actual de la entidad.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
