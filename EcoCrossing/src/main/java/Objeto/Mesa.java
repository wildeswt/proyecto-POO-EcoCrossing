package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Mesa representa un objeto tipo mesa en el juego. Extiende de
 * SuperObjeto y proporciona métodos para cargar y escalar la imagen de la mesa.
 */
public class Mesa extends SuperObjeto {

    /**
     * Constructor de la clase Mesa. Inicializa la mesa con su nombre,
     * descripción y activa la colisión.
     */
    public Mesa() {
        nombre = "mesa";
        descripcion = "{" + nombre + "}\nEs una mesa";
        colision = true;
    }

    /**
     * Carga y escala la imagen de la mesa.
     *
     * @param escala El tamaño de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/" + nombre + ".png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de la mesa: " + e.getMessage());
        }
    }
}
