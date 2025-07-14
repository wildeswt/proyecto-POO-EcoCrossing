/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objeto;

import Personaje.CargadorImagenes;
import com.mycompany.ecocrossing.EscaladorImagen;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La clase valla representa un objeto de tipo valla en el juego. Extiende de
 * SuperObjeto e implementa la interfaz CargadorImagenes para cargar imágenes.
 */
public class Valla extends SuperObjeto implements CargadorImagenes {

    public boolean abierto = false;

    /**
     * Constructor de la clase Electrodomestico. Inicializa el objeto
     * electrodoméstico con su nombre y estado inicial encendido.
     *
     * @param nombre El nombre del electrodoméstico.
     */
    public Valla(String nombre) {
        this.nombre = nombre;
        colision = true;
        abierto = false;
    }

    /**
     * Abre la valla cargando la imagen correspondiente.
     *
     * @param escala El tamaño de escala para la imagen.
     * @param imagenAbierta ruta de la valla abierta
     */
    public void abrir(int escala, String imagenAbierta) {
        imagen = cargarImagen("/Recuadros/" + imagenAbierta, escala, escala);
        abierto = true;
        colision = false;
    }

    /**
     * Cierra la valla cargando la imagen correspondiente.
     *
     * @param escala El tamaño de escala para la imagen.
     */
    public void cerrar(int escala) {
        imagen = cargarImagen("/Recuadros/" + nombre, escala, escala);
        abierto = false;
        colision = true;
    }

    /**
     * Carga y escala la imagen del electrodoméstico. Utiliza la clase
     * EscaladorImagen para escalar la imagen cargada.
     *
     * @param ruta La ruta de la imagen a cargar.
     * @param ancho El ancho al que se escalará la imagen.
     * @param largo El largo al que se escalará la imagen.
     * @return La imagen cargada y escalada.
     */
    @Override
    public BufferedImage cargarImagen(String ruta, int ancho, int largo) {
        EscaladorImagen herramientaUtil = new EscaladorImagen();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream(ruta + ".png"));
            imagen = herramientaUtil.escalarImagen(imagen, ancho, largo);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen del electrodoméstico: " + e.getMessage());
        }
        return imagen;
    }

}
