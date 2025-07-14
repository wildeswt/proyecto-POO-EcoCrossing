package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Llave representa un objeto tipo llave en el juego. Extiende de
 * SuperObjeto y proporciona métodos para cargar y escalar la imagen de la
 * llave.
 */
public class Llave extends SuperObjeto {

    /**
     * Constructor de la clase Llave. Inicializa la llave con su nombre,
     * descripción y precio predeterminados.
     */
    public Llave() {
        nombre = "llave";
        descripcion = "{" + nombre + "}\nEs la llave del pueblo";
        precio = 10; // Precio predeterminado
    }

    /**
     * Carga y escala la imagen de la llave.
     *
     * @param escala El tamaño de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/llave/" + nombre + ".png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de la llave: " + e.getMessage());
        }
    }
}
