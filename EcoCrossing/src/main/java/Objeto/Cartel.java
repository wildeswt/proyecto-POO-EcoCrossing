package Objeto;

import Personaje.CargadorImagenes;
import com.mycompany.ecocrossing.EscaladorImagen;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Cartel representa un objeto de tipo cartel en el juego. Extiende de
 * SuperObjeto e implementa la interfaz CargadorImagenes para cargar imágenes de
 * cartel.
 */
public class Cartel extends SuperObjeto implements CargadorImagenes {

    /**
     * Constructor de la clase Cartel.
     *
     * @param nombre El nombre del cartel.
     */
    public Cartel(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del cartel.
     *
     * @return El nombre del cartel.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cartel.
     *
     * @param nombre El nombre a establecer para el cartel.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Carga la imagen del cartel escalada a un tamaño específico.
     *
     * @param escala El factor de escala para la imagen del cartel.
     */
    public void cargar(int escala) {
        this.imagen = cargarImagen("/Objetos/cartel", escala, escala);
    }

    /**
     * Implementación del método de la interfaz CargadorImagenes para cargar una
     * imagen de cartel.
     *
     * @param ruta La ruta de la imagen del cartel.
     * @param ancho El ancho deseado para la imagen del cartel.
     * @param largo El largo deseado para la imagen del cartel.
     * @return La imagen del cartel cargada y escalada.
     */
    @Override
    public BufferedImage cargarImagen(String ruta, int ancho, int largo) {
        EscaladorImagen herramientaUtil = new EscaladorImagen();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream(ruta + ".png"));
            imagen = herramientaUtil.escalarImagen(imagen, ancho, largo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imagen;
    }
}
