package Objeto;

import com.mycompany.ecocrossing.PanelJuego;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase Corazon representa un objeto de tipo corazón en el juego. Extiende
 * de SuperObjeto y proporciona imágenes escaladas para diferentes estados del
 * corazón.
 */
public class Corazon extends SuperObjeto {

    private PanelJuego panelJuego;

    /**
     * Constructor de la clase Corazon. Inicializa el objeto corazón con
     * imágenes escaladas para diferentes estados.
     *
     * @param panelJuego El panel de juego donde se mostrará el corazón.
     * @throws IOException Si ocurre un error al cargar las imágenes.
     */
    public Corazon(PanelJuego panelJuego) throws IOException {
        this.panelJuego = panelJuego;
        nombre = "Corazon";

        try {
            // Cargar imágenes para diferentes estados del corazón y escalarlas según el tamaño de los recuadros en el panel de juego.
            imagen = ImageIO.read(getClass().getResourceAsStream("/Objetos/Corazon/corazon_full.png"));
            imagen2 = ImageIO.read(getClass().getResourceAsStream("/Objetos/Corazon/corazon_medio.png"));
            imagen3 = ImageIO.read(getClass().getResourceAsStream("/Objetos/Corazon/corazon_blanco.png"));
            imagen = herramientaUtil.escalarImagen(imagen, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
            imagen2 = herramientaUtil.escalarImagen(imagen2, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
            imagen3 = herramientaUtil.escalarImagen(imagen3, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
        } catch (IOException e) {
            System.out.println("Error al cargar las imágenes del corazón: " + e.getMessage());
        }
    }
}
