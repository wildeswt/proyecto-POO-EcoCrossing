package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Comida representa un objeto de tipo comida en el juego. Extiende de
 * SuperObjeto y proporciona funcionalidades específicas para cada tipo de
 * comida.
 */
public class Comida extends SuperObjeto {

    /**
     * Constructor de la clase Comida. Inicializa el objeto comida con un nombre
     * específico y establece algunas propiedades como la descripción y el
     * precio.
     *
     * @param nombre El nombre del tipo de comida.
     */
    public Comida(String nombre) {
        this.nombre = nombre;
        colision = true;

        // Selecciona la descripción y el precio basado en el nombre del objeto comida.
        if (nombre.equals("dona")) {
            descripcion = "{" + nombre + "}\nAlta en azúcar...";
        } else if (nombre.equals("helado")) {
            descripcion = "{" + nombre + "}\nTe puede congelar\nel cerebro...";
        } else if (nombre.equals("pizza")) {
            descripcion = "{" + nombre + "}\nLa favorita de\nmuchos...";
        } else if (nombre.equals("ponque")) {
            descripcion = "{" + nombre + "}\nHecho con amor...";
        } else if (nombre.equals("galleta")) {
            descripcion = "{" + nombre + "}\nNada más tradicional\n...";
        } else if (nombre.equals("salchicha")) {
            descripcion = "{" + nombre + "}\nEstarán hechas con\ncarne de tortuga?";
        } else if (nombre.equals("maiz")) {
            descripcion = "{" + nombre + "}\nLo recomiendan con\nmantequilla...";
        } else if (nombre.equals("leche")) {
            descripcion = "{" + nombre + "}\nNo apto para\nintolerantes...";
        } else if (nombre.equals("queso")) {
            descripcion = "{" + nombre + "}\nHuele mal...";
        } else if (nombre.equals("cafe")) {
            descripcion = "{" + nombre + "}\nYa es una necesidad...\n";
        }

        precio = 2; // Precio estándar para todos los tipos de comida.
    }

    /**
     * Carga la imagen de la comida escalada a un tamaño específico.
     *
     * @param escala El factor de escala para la imagen de la comida.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/Comida/" + nombre + ".png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }
    }
}
