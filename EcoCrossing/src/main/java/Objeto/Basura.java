package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Basura representa un objeto de basura en el juego. Extiende de
 * SuperObjeto e incluye métodos para cargar su imagen y configurar su
 * descripción según su nombre.
 */
public class Basura extends SuperObjeto {

    /**
     * Constructor de la clase Basura.
     *
     * @param nombre El nombre del objeto de basura.
     */
    public Basura(String nombre) { //Sin parametros
        this.nombre = nombre;
        colision = true;

        // Configuración de la descripción según el nombre del objeto
        if (nombre.equals("banana")) {
            descripcion = "{" + nombre + "}\nDefinitivamente no es\npara comer...";
        } else if (nombre.equals("carton")) {
            descripcion = "{" + nombre + "}\nPor lo menos no esta\nmojado...";
        } else if (nombre.equals("lata")) {
            descripcion = "{" + nombre + "}\nQue estaba adentro?";
        } else if (nombre.equals("papel")) {
            descripcion = "{" + nombre + "}\nDe puntas filosas...";
        }
        // precio=2;
    }

    /**
     * Carga la imagen del objeto de basura.
     *
     * @param escala El factor de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/Basura/" + nombre + ".png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen (Line 29)");
            System.err.println(e.getMessage());
        }
    }
}
