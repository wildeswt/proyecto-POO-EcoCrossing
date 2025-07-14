package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Papelera representa un objeto tipo papelera en el juego. Extiende de
 * SuperObjeto y proporciona un método para cargar y escalar la imagen de la
 * papelera.
 */
public class Papelera extends SuperObjeto {

    /**
     * Constructor de la clase Papelera. Inicializa la papelera con su nombre y
     * descripción.
     */
    public Papelera() {
        this.nombre = "papelera";
        colision = true;
        descripcion = "{" + nombre + "}\nPara botar basura";
    }

    /**
     * Carga y escala la imagen de la papelera.
     *
     * @param escala El tamaño de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/Basura/papelera.png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de la papelera: " + e.getMessage());
        }
    }
}
