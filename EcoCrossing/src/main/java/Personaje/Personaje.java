package Personaje;

import Entidades.Jugador;
import com.mycompany.ecocrossing.EscaladorImagen;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Personaje representa un personaje del juego con capacidades para
 * cargar su vestimenta. Implementa la interfaz CargadorImagenes para
 * proporcionar funcionalidad de carga de imágenes.
 */
public class Personaje implements CargadorImagenes {

    public String nombre;

    /**
     * Constructor que inicializa el nombre del personaje.
     *
     * @param nombre El nombre del personaje.
     */
    public Personaje(String nombre) {
        this.nombre = nombre;
        //la vestimenta básica del personaje se referencia con el nombre
    }

    /**
     * Obtiene el nombre del personaje.
     *
     * @return El nombre del personaje.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del personaje.
     *
     * @param nombre El nuevo nombre del personaje.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Carga la vestimenta del jugador utilizando las imágenes correspondientes
     * según el nombre del personaje.
     *
     * @param jugador El jugador para quien se carga la vestimenta.
     */
    public void cargarVestimenta(Jugador jugador) {
        int escala = jugador.panelJuego.tamannoRecuadros;

        jugador.espalda = cargarImagen("/Jugador/" + nombre + "/espalda", escala, escala);
        jugador.arriba1 = cargarImagen("/Jugador/" + nombre + "/espalda_caminando1", escala, escala);
        jugador.arriba2 = cargarImagen("/Jugador/" + nombre + "/espalda_caminando2", escala, escala);
        jugador.frente = cargarImagen("/Jugador/" + nombre + "/frente", escala, escala);
        jugador.abajo1 = cargarImagen("/Jugador/" + nombre + "/caminando1", escala, escala);
        jugador.abajo2 = cargarImagen("/Jugador/" + nombre + "/caminando2", escala, escala);
        jugador.lado1 = cargarImagen("/Jugador/" + nombre + "/Derecha", escala, escala);
        jugador.der1 = cargarImagen("/Jugador/" + nombre + "/Derecha_caminando1", escala, escala);
        jugador.der2 = cargarImagen("/Jugador/" + nombre + "/Derecha_caminando2", escala, escala);
        jugador.lado2 = cargarImagen("/Jugador/" + nombre + "/Izquierda", escala, escala);
        jugador.izq1 = cargarImagen("/Jugador/" + nombre + "/Izquierda_caminando1", escala, escala);
        jugador.izq2 = cargarImagen("/Jugador/" + nombre + "/Izquierda_caminando2", escala, escala);
    }

    /**
     * Implementación del método de la interfaz CargadorImagenes para cargar
     * imágenes desde recursos del proyecto.
     *
     * @param ruta La ruta donde se encuentra almacenada la imagen dentro del
     * proyecto.
     * @param ancho El ancho deseado para la imagen cargada.
     * @param largo El largo deseado para la imagen cargada.
     * @return La imagen cargada y redimensionada como BufferedImage.
     */
    @Override
    public BufferedImage cargarImagen(String ruta, int ancho, int largo) {
        EscaladorImagen herramientaUtil = new EscaladorImagen();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("" + ruta + ".png"));
            imagen = herramientaUtil.escalarImagen(imagen, ancho, ancho);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagen;
    }
}
