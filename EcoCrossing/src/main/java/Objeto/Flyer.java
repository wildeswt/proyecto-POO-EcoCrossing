package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Flyer representa un objeto tipo flyer en el juego.
 * Extiende de SuperObjeto y proporciona métodos para cargar y escalar la imagen del flyer.
 */
public class Flyer extends SuperObjeto {

    /**
     * Constructor de la clase Flyer.
     * Inicializa el objeto flyer con su nombre y descripción predeterminada.
     * @param nombre El nombre del flyer.
     */
    public Flyer(String nombre) {
        this.nombre = nombre;
        descripcion = "{" + nombre + "}\nEs un lindo Flyer...";
        precio = 20; // Precio predeterminado
    }
    
    /**
     * Carga y escala la imagen del flyer.
     * @param escala El tamaño de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/flyers/" + nombre + ".png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen del flyer: " + e.getMessage());
        }
    }

}
