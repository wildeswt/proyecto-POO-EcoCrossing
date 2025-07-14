package com.mycompany.ecocrossing;

import EcoCrossing.net.paquetes.Paquete01Desconectar;
import Personaje.Personaje;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Clase encargada de la interacción Usuario-Teclado-Juego.
 */
public class ManejoTeclas implements KeyListener {

    public boolean arribaPresionado, abajoPresionado, izquierdaPresionado,
            derechaPresionado, interacturaObjetoPresionado, hablarNPCPresionado,
            enterPresionado;
    PanelJuego panelJuego;

    // Depuración:
    boolean tiempo = false;

    /**
     * El manejo de teclas que se va a ser en el panel recibido.
     * @param panelJuego panel donde se llevará acabo el manejo
     */
    public ManejoTeclas(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Método principal encargado de cambiar los estados de la pantalla.
     * @param e tecla presionada
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //Obtener el codigo de la tecla presionada
        int codigo = e.getKeyCode();
        // Estado Menú (Navega entre las pantallas del titulo):
        if (panelJuego.estadoJuego == panelJuego.estadoTitulo) {
            if (panelJuego.ui.estadoPantallaTitulo == 0) {
                estadoTitulo0(codigo);
            } else if (panelJuego.ui.estadoPantallaTitulo == 1) {
                estadoTitulo1(codigo);
            }
        } // Estado Jugando (Partida Activa):
        else if (panelJuego.estadoJuego == panelJuego.estadoJugando) {
            estadoJugando(codigo);
        } // Estado Pausa:
        else if (panelJuego.estadoJuego == panelJuego.estadoPausa) {
            estadoPausa(codigo);
        } // Estado de Personaje:
        else if (panelJuego.estadoJuego == panelJuego.estadoPersonaje) {
            estadoPersonaje(codigo);
        } //Estado de Dialogos
        else if (panelJuego.estadoJuego == panelJuego.estadoDialogo) {
            estadoDialogo(codigo);
        } // Estado de Tradeo:
        else if (panelJuego.estadoJuego == panelJuego.estadoTradeo) {
            estadoTradeo(codigo);
        } // Estado Juego Terminado
        else if (panelJuego.estadoJuego == panelJuego.estadoJuegoTerminado) {
            estadoJuegoTerminado(codigo);
        }

    }

    /**
     * Primer estado de la pantalla.
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoTitulo0(int codigo) {
        if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
            panelJuego.ui.comandoNumerico--;
            if (panelJuego.ui.comandoNumerico < 0) {
                panelJuego.ui.comandoNumerico = 2;
            }
        } else if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
            panelJuego.ui.comandoNumerico++;
            if (panelJuego.ui.comandoNumerico > 2) {
                panelJuego.ui.comandoNumerico = 0;
            }
        } else if (codigo == KeyEvent.VK_ENTER) {
            if (panelJuego.ui.comandoNumerico == 0) {
                panelJuego.ui.estadoPantallaTitulo = 1;
            } else if (panelJuego.ui.comandoNumerico == 1) {
                String url = "https://idaliendres.github.io/AcercaDeProyectoJava/"; // Cambia la URL por la que desees abrir
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (panelJuego.ui.comandoNumerico == 2) {
                System.exit(0);
            }
        }
    }

    /**
     * Segundo estado de la pantalla
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoTitulo1(int codigo) {
        //Subir cursos
        if (panelJuego.ui.vestimenta == -1) {
            //si la vestimenta es -1 que se no se ha escogido vestimenta
            if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                panelJuego.ui.comandoNumerico--;
                if (panelJuego.ui.comandoNumerico < 0) {
                    panelJuego.ui.comandoNumerico = 4;
                }
            } //bajar cursor
            else if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
                panelJuego.ui.comandoNumerico++;
                if (panelJuego.ui.comandoNumerico > 4) {
                    panelJuego.ui.comandoNumerico = 0;
                }
            }
        } else {
            if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                panelJuego.ui.vestimenta--;
                if (panelJuego.ui.vestimenta < 0) {
                    panelJuego.ui.vestimenta = 4;
                }
            } //bajar cursor
            else if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
                panelJuego.ui.vestimenta++;
                if (panelJuego.ui.vestimenta > 4) {
                    panelJuego.ui.vestimenta = 0;
                }
            }
        }

        //eventos al precionar enter
        if (codigo == KeyEvent.VK_ENTER) {

            if (panelJuego.ui.vestimenta == 3) {
                panelJuego.ui.vestimenta = -1;
                panelJuego.ui.comandoNumerico = 0;
            } else if (panelJuego.ui.comandoNumerico >= 0 && panelJuego.ui.comandoNumerico <= 3 && panelJuego.ui.vestimenta != -1) {
                // Implementar lógica para cargar una skin en específico, y luego arrancar el juego con ella...
                panelJuego.estadoJuego = panelJuego.estadoJugando;
                int nroJugador = panelJuego.ui.comandoNumerico;
                int nroVestimenta = panelJuego.ui.vestimenta;
                String nombre = panelJuego.ui.cargarNombre(nroJugador);

                //creamos el personaje por defecto 
                Personaje personaje = new Personaje(nombre);
                panelJuego.jugador.personaje = personaje;

                //en caso de la vestimenta la agregamos al personaje
                if (nroVestimenta != 0) {
                    String estilo = panelJuego.ui.cargarRopa(nombre, nroVestimenta);
                    personaje.setNombre(estilo);
                }

                //iniciamos el juego 
                panelJuego.iniciarjuegoThread();

                panelJuego.reproducirMusica(0);
            } else if (panelJuego.ui.comandoNumerico == 4) {
                // En este caso volvemos al menú principal...
                panelJuego.ui.vestimenta = -1;
                panelJuego.ui.comandoNumerico = 0;
                panelJuego.ui.estadoPantallaTitulo = 0;
            } //en caso de selecionar un personaje mostramos las diferente opciones del personaje
            else if (panelJuego.ui.vestimenta == -1) {
                //mostramos las opciones del personaje samuel 
                panelJuego.ui.vestimenta = 0;
            }
        }
    }

    /**
     * Estado cuando el jugador se encuentra en el Gameplay convencional
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoJugando(int codigo) {
        //Verificar a que direccion se refiere
        if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
            arribaPresionado = true;
        } else if (codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT) {
            izquierdaPresionado = true;
        } else if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
            abajoPresionado = true;
        } else if (codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT) {
            derechaPresionado = true;
        } // Estado de pausa:
        else if (codigo == KeyEvent.VK_P) {
            panelJuego.estadoJuego = panelJuego.estadoPausa;
        } // Verificar teclas especiales
        else if (codigo == KeyEvent.VK_R) {
            interacturaObjetoPresionado = true;
        } else if (codigo == KeyEvent.VK_Q) {
            hablarNPCPresionado = true;
        } // Estado personaje:
        else if (codigo == KeyEvent.VK_C) {
            panelJuego.estadoJuego = panelJuego.estadoPersonaje;
        } // Verificar depuración:
        else if (codigo == KeyEvent.VK_T) {
            tiempo = !tiempo;
        }
    }

    /**
     * Estado cuando el jugador se encuentra en pausa
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoPausa(int codigo) {
        if (codigo == KeyEvent.VK_P) {
            panelJuego.estadoJuego = panelJuego.estadoJugando;
        }
        
        if(codigo == KeyEvent.VK_ENTER){
            enterPresionado= true;
        }
        
        int comandoNumericoMax=0;
        switch(panelJuego.ui.subEstado){
            case 0:
                comandoNumericoMax=3;
                break;
            case 1:
                comandoNumericoMax=2;
                break;
        }
        
        if(codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP){
            panelJuego.ui.comandoNumerico--;
            panelJuego.reproducirEfectosSonido(2);
            if(panelJuego.ui.comandoNumerico < 0){
                panelJuego.ui.comandoNumerico=3;
            }
                
        }
        
        if(codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN){
            panelJuego.ui.comandoNumerico++;
            panelJuego.reproducirEfectosSonido(2);
            if(panelJuego.ui.comandoNumerico > comandoNumericoMax){
                panelJuego.ui.comandoNumerico=0;
            }
        }
        
        if(codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT){
            if(panelJuego.ui.subEstado == 0){
                if(panelJuego.ui.comandoNumerico == 0 && panelJuego.musica.escalaVolumen > 0){
                    panelJuego.musica.escalaVolumen--;
                    panelJuego.musica.VerificarVolumen();
                    panelJuego.reproducirEfectosSonido(2);
                }
                if(panelJuego.ui.comandoNumerico == 1 && panelJuego.efectosSonido.escalaVolumen > 0){
                    panelJuego.efectosSonido.escalaVolumen--;
                    panelJuego.reproducirEfectosSonido(2);
                }
            }
        }
        
        if(codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT){
            if(panelJuego.ui.subEstado == 0){
                if(panelJuego.ui.comandoNumerico == 0 && panelJuego.musica.escalaVolumen < 5){
                    panelJuego.musica.escalaVolumen++;
                    panelJuego.musica.VerificarVolumen();
                    panelJuego.reproducirEfectosSonido(2);
                }
                if(panelJuego.ui.comandoNumerico == 1 && panelJuego.efectosSonido.escalaVolumen < 5){
                    panelJuego.efectosSonido.escalaVolumen++;
                    panelJuego.reproducirEfectosSonido(2);
                }
            }
        }
    }

    /**
     * Estado cuando el jugador se encuentra viendo sus stats
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoPersonaje(int codigo) {
        if (codigo == KeyEvent.VK_C) {
            panelJuego.estadoJuego = panelJuego.estadoJugando;
        }
        inventarioJugador(codigo);
    }

    /**
     * Estado cuando un jugador entra en "Diálogo" con otra entidad.
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoDialogo(int codigo) {
        if (codigo == KeyEvent.VK_SPACE) {
            panelJuego.estadoJuego = panelJuego.estadoJugando;
        }
    }

    /**
     * Estado cuando el jugador entra en un tradeo con una entidad.
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoTradeo(int codigo) {
        if (codigo == KeyEvent.VK_ENTER) {
            enterPresionado = true;
        }

        // Sub-estado de seleccionar en el tradeo...
        if (panelJuego.ui.subEstado == 0) {
            if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                panelJuego.ui.comandoNumerico--;
                if (panelJuego.ui.comandoNumerico < 0) {
                    panelJuego.ui.comandoNumerico = 2;
                }
                panelJuego.reproducirEfectosSonido(2);
            } else if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
                panelJuego.ui.comandoNumerico++;
                if (panelJuego.ui.comandoNumerico > 2) {
                    panelJuego.ui.comandoNumerico = 0;
                }
                panelJuego.reproducirEfectosSonido(2);
            }
        }

        // Sub-estado de comprar en el tradeo...
        if (panelJuego.ui.subEstado == 1) {
            inventarioNpc(codigo);
            if (codigo == KeyEvent.VK_ESCAPE) {
                panelJuego.ui.subEstado = 0;
            }
        }

        // Sub-estado de comprar en el tradeo...
        if (panelJuego.ui.subEstado == 2) {
            inventarioJugador(codigo);
            if (codigo == KeyEvent.VK_ESCAPE) {
                panelJuego.ui.subEstado = 0;
            }
        }
    }

    /**
     * Estado cuando el juego finaliza
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void estadoJuegoTerminado(int codigo) {
        if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
            panelJuego.ui.comandoNumerico--;
            if (panelJuego.ui.comandoNumerico < 0) {
                panelJuego.ui.comandoNumerico = 1;
            }
        }
        if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
            panelJuego.ui.comandoNumerico++;
            if (panelJuego.ui.comandoNumerico > 1) {
                panelJuego.ui.comandoNumerico = 0;
            }
        }
        if (codigo == KeyEvent.VK_ENTER) {
            if (panelJuego.ui.comandoNumerico == 0) {
                panelJuego.estadoJuego = panelJuego.estadoJugando;
                panelJuego.regenerar();
                panelJuego.ui.juegoFinalizado=false;
            } else if (panelJuego.ui.comandoNumerico == 1) {
                panelJuego.estadoJuego = 0;
                panelJuego.ui.estadoPantallaTitulo = 0;

                // Eliminar al jugador del servidor
                Paquete01Desconectar paquete = new Paquete01Desconectar(this.panelJuego.jugador.getNombreUsuario());
                paquete.escribirDatos(this.panelJuego.clienteSocket);

                panelJuego.reiniciar();
            }
        }
    }

    /**
     * Navegación del jugador por su menú de objetos.
     * @param codigo conversión a int de la tecla presionada para manejar lo que suceda
     */
    public void inventarioJugador(int codigo) {
        if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
            if (panelJuego.ui.jugadorRanuraFila != 0) {
                panelJuego.ui.jugadorRanuraFila--;
                panelJuego.reproducirEfectosSonido(2);
            }
        } else if (codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT) {
            if (panelJuego.ui.jugadorRanuraColumna != 0) {
                panelJuego.ui.jugadorRanuraColumna--;
                panelJuego.reproducirEfectosSonido(2);
            }
        } else if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
            if (panelJuego.ui.jugadorRanuraFila != 3) {
                panelJuego.ui.jugadorRanuraFila++;
                panelJuego.reproducirEfectosSonido(2);
            }
        } else if (codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT) {
            if (panelJuego.ui.jugadorRanuraColumna != 4) {
                panelJuego.ui.jugadorRanuraColumna++;
                panelJuego.reproducirEfectosSonido(2);
            }
        }
    }
    
    
    public void inventarioNpc(int codigo) {
        if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
            if (panelJuego.ui.npcRanuraFila != 0) {
                panelJuego.ui.npcRanuraFila--;
                panelJuego.reproducirEfectosSonido(2);
            }
        } else if (codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT) {
            if (panelJuego.ui.npcRanuraColumna != 0) {
                panelJuego.ui.npcRanuraColumna--;
                panelJuego.reproducirEfectosSonido(2);
            }
        } else if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
            if (panelJuego.ui.npcRanuraFila != 3) {
                panelJuego.ui.npcRanuraFila++;
                panelJuego.reproducirEfectosSonido(2);
            }
        } else if (codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT) {
            if (panelJuego.ui.npcRanuraColumna != 4) {
                panelJuego.ui.npcRanuraColumna++;
                panelJuego.reproducirEfectosSonido(2);
            }
        }
    }

    /**
     * Método invocado cuando se suelta una tecla.
     * @param e conversión a int de la tecla presionada para manejar lo que suceda
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //Obtener el codigo de la tecla que se solto
        int codigo = e.getKeyCode();

        //Verificar a que direccion se refiere
        if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
            arribaPresionado = false;
        }
        if (codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT) {
            izquierdaPresionado = false;
        }
        if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
            abajoPresionado = false;
        }
        if (codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT) {
            derechaPresionado = false;
        }

        //Verificar teclas especiales
        if (codigo == KeyEvent.VK_R) {
            interacturaObjetoPresionado = false;
        }

        if (codigo == KeyEvent.VK_Q) {
            hablarNPCPresionado = false;
        }
    }

}
