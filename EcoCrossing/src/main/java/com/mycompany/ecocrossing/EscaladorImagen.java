package com.mycompany.ecocrossing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Clase Relacionada con todas las herramientas de utilidad para el proyecto,
 * por los momentos solo esta para mejorar el rendimiento.
 */
public class EscaladorImagen {

    /**
     * Escala una imagen al ancho y altura especificados.
     *
     * @param imagenOriginal La imagen original que se desea escalar.
     * @param ancho El nuevo ancho de la imagen escalada.
     * @param altura La nueva altura de la imagen escalada.
     * @return La imagen escalada.
     */
    public BufferedImage escalarImagen(BufferedImage imagenOriginal, int ancho, int altura) {
        BufferedImage imagenEscalada = new BufferedImage(ancho, altura, 2);
        Graphics2D g2 = imagenEscalada.createGraphics();
        g2.drawImage(imagenOriginal, 0, 0, ancho, altura, null);
        g2.dispose();

        return imagenEscalada;
    }
}
