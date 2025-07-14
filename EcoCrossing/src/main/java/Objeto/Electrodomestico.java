package Objeto;

import Personaje.CargadorImagenes;
import com.mycompany.ecocrossing.EscaladorImagen;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Electrodomestico representa un objeto de tipo electrodoméstico en el
 * juego. Extiende de SuperObjeto e implementa la interfaz CargadorImagenes para
 * cargar imágenes.
 */
public class Electrodomestico extends SuperObjeto implements CargadorImagenes {

    public boolean encendido = true;

    /**
     * Constructor de la clase Electrodomestico. Inicializa el objeto
     * electrodoméstico con su nombre y estado inicial encendido.
     *
     * @param nombre El nombre del electrodoméstico.
     */
    public Electrodomestico(String nombre) {
        this.nombre = nombre;
        colision = true;
        encendido = true;
    }

    /**
     * Obtiene el nombre del electrodoméstico.
     *
     * @return El nombre del electrodoméstico.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del electrodoméstico.
     *
     * @param nombre El nuevo nombre del electrodoméstico.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Enciende el electrodoméstico cargando la imagen correspondiente.
     *
     * @param escala El tamaño de escala para la imagen.
     */
    public void encender(int escala) {
        imagen = cargarImagen("/Objetos/Electrodomesticos/" + nombre + "ON", escala, escala);
        encendido = true;
    }

    /**
     * Apaga el electrodoméstico cargando la imagen correspondiente.
     *
     * @param escala El tamaño de escala para la imagen.
     */
    public void apagar(int escala) {
        imagen = cargarImagen("/Objetos/Electrodomesticos/" + nombre + "OFF", escala, escala);
        encendido = false;
    }

    /**
     * Carga y escala la imagen del electrodoméstico. Utiliza la clase
     * EscaladorImagen para escalar la imagen cargada.
     *
     * @param ruta La ruta de la imagen a cargar.
     * @param ancho El ancho al que se escalará la imagen.
     * @param largo El largo al que se escalará la imagen.
     * @return La imagen cargada y escalada.
     */
    @Override
    public BufferedImage cargarImagen(String ruta, int ancho, int largo) {
        EscaladorImagen herramientaUtil = new EscaladorImagen();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream(ruta + ".png"));
            imagen = herramientaUtil.escalarImagen(imagen, ancho, largo);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen del electrodoméstico: " + e.getMessage());
        }
        return imagen;
    }
}
