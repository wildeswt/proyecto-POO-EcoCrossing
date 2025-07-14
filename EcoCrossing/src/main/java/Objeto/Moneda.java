package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Moneda representa un objeto tipo moneda en el juego. Extiende de
 * SuperObjeto y proporciona un método para cargar y escalar la imagen de la
 * moneda.
 */
public class Moneda extends SuperObjeto {

    /**
     * Constructor de la clase Moneda. Inicializa la moneda con su nombre.
     */
    public Moneda() {
        nombre = "moneda";
    }

    /**
     * Carga y escala la imagen de la moneda.
     *
     * @param escala El tamaño de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/Moneda/moneda.png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de la moneda: " + e.getMessage());
        }
    }
}
