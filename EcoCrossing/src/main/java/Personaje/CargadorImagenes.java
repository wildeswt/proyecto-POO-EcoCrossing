/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Personaje;

import java.awt.image.BufferedImage;

/**
 * La interfaz CargadorImagenes define un método para cargar imágenes con
 * parámetros específicos. Implementa este método para proporcionar la
 * funcionalidad de carga de imágenes según una ruta y dimensiones.
 */
public interface CargadorImagenes {

    /**
     * Carga una imagen desde la ruta especificada y la redimensiona según el
     * ancho y largo dados.
     *
     * @param ruta La ruta donde se encuentra almacenada la imagen.
     * @param ancho El ancho deseado para la imagen cargada.
     * @param largo El largo deseado para la imagen cargada.
     * @return La imagen cargada y redimensionada como BufferedImage.
     */
    public BufferedImage cargarImagen(String ruta, int ancho, int largo);
}
