package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Tortuga representa un objeto tortuga en el juego.
 */
public class Tortuga extends SuperObjeto {

    // Constructor
    public Tortuga() {
        this.nombre = "tortuga";
        colision = true;
        descripcion = "{" + nombre + "}\n Una tortuga salvada!.";
    }

    /**
     * Carga la imagen de la tortuga escalada según el factor indicado.
     *
     * @param escala El factor de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/tortuga.png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen (Line 29)");
            System.out.println(e.getMessage());
        }
    }
}
