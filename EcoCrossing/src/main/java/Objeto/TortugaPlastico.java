package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase TortugaPlastico representa una tortuga atrapada en plástico en el
 * juego.
 */
public class TortugaPlastico extends SuperObjeto {

    private static TortugaPlastico instance = null;

    // Constructor
    public TortugaPlastico() {
        this.nombre = "Tortuga con Plastico";
        colision = true;
        descripcion = "{" + nombre + "}\n Una tortuga en \nbusca de ser salvada.";
        instance = this;
    }

    /**
     * Obtiene la única instancia de TortugaPlastico (Singleton).
     *
     * @return La instancia única de TortugaPlastico.
     */
    public static TortugaPlastico getInstance() {
        return instance;
    }

    /**
     * Carga la imagen de la tortuga atrapada en plástico escalada según el
     * factor indicado.
     *
     * @param escala El factor de escala para la imagen.
     */
    public void cargar(int escala) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/tortugaPlastico.png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen (Line 29)");
            System.out.println(e.getMessage());
        }
    }
}
