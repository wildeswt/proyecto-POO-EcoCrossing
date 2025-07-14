/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecocrossing;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Esta clase proporciona funcionalidad para cargar, reproducir en bucle y
 * detener archivos de sonido.
 */
public class Sonido {

    Clip clip;
    // Usamos esta URL para almacenar la ruta de los archivos de sonido
    URL sonidoURL[] = new URL[30];
    FloatControl fc;
    int escalaVolumen=3;
    float volumen;

    /**
     * Constructor que inicializa las rutas de los archivos de sonido.
     */
    public Sonido() {
        sonidoURL[0] = getClass().getResource("/Sonido/EcoCrossingTema.wav");
        sonidoURL[1] = getClass().getResource("/Sonido/RecogerObjetoSonido.wav");
        sonidoURL[2] = getClass().getResource("/Sonido/cursor.wav");
    }

    /**
     * Carga un archivo de sonido específico en el objeto Clip.
     *
     * @param i Índice del archivo de sonido en el array sonidoURL.
     */
    public void colocarArchivo(int i) {
        try {
            AudioInputStream flujoEntradaAudio = AudioSystem.getAudioInputStream(sonidoURL[i]);
            clip = AudioSystem.getClip();
            clip.open(flujoEntradaAudio);
            fc= (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            VerificarVolumen();
        } catch (IOException e) {
            System.out.println("Error de IO: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("Error de línea no disponible: " + e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error de archivo de audio no soportado: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error de índice de arreglo fuera de límites: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error de puntero nulo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido: " + e.getMessage());
        }
    }

    /**
     * Reproduce el archivo de sonido actualmente cargado en el Clip.
     */
    public void reproducir() {
        try {
            clip.start();
        } catch (NullPointerException e) {
            System.out.println("Error: No se ha cargado ningún archivo de sonido.");
        } catch (Exception e) {
            System.out.println("Error desconocido al reproducir: " + e.getMessage());
        }
    }

    /**
     * Inicia la reproducción en bucle del archivo de sonido actualmente cargado
     * en el Clip.
     */
    public void bucle() {
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (NullPointerException e) {
            System.out.println("Error: No se ha cargado ningún archivo de sonido.");
        } catch (Exception e) {
            System.out.println("Error desconocido al iniciar el bucle: " + e.getMessage());
        }
    }

    /**
     * Detiene la reproducción del archivo de sonido actualmente cargado en el
     * Clip.
     */
    public void parar() {
        try {
            clip.stop();
        } catch (NullPointerException e) {
            System.out.println("Error: No se ha cargado ningún archivo de sonido.");
        } catch (Exception e) {
            System.out.println("Error desconocido al parar: " + e.getMessage());
        }
    }
    
    /**
     * Devuelve el valor del volumen de acuerdo a la escala actual.
     */
    public void VerificarVolumen(){
        switch(escalaVolumen){
            case 0:
                volumen=-80f;
                break;
            case 1:
                volumen=-20f;
                break;
            case 2:
                volumen=-12f;
                break;
            case 3:
                volumen=-5f;
                break;
            case 4:
                volumen=1f;
                break;
            case 5:
                volumen=6f;
                break;
        }
        fc.setValue(volumen);
    }
}
