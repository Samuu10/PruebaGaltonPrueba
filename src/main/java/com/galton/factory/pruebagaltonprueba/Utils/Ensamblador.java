package com.galton.factory.pruebagaltonprueba.Utils;

import com.galton.factory.pruebagaltonprueba.Models.Bola;
import com.galton.factory.pruebagaltonprueba.Models.Component;
import com.galton.factory.pruebagaltonprueba.Models.Tablero;
import com.galton.factory.pruebagaltonprueba.Visualizacion;
import java.util.concurrent.BlockingQueue;

//Clase que se encarga de ensamblar los componentes creados por las fábricas
public class Ensamblador extends Thread {

    //Variables
    private BlockingQueue<Component> buffer;
    private Visualizacion visualizacion;
    private boolean tableroCreado = false;
    private int bolasCreadas = 0;
    private final int maxBolas;

    //Constructor
    public Ensamblador(BlockingQueue<Component> buffer, Visualizacion visualizacion) {
        this.buffer = buffer;
        this.visualizacion = visualizacion;
        this.maxBolas = 100;
    }

    //Metodo para ensamblar los componentes
    @Override
    public void run() {
        try {
            while (bolasCreadas < maxBolas) {
                Component componente = buffer.take();
                if (componente instanceof Bola) {
                    Bola bola = (Bola) componente;
                    visualizacion.agregarBola(bola);
                    bolasCreadas++;
                } else if (componente instanceof Tablero && !tableroCreado) {
                    Tablero tablero = (Tablero) componente;
                    visualizacion.agregarTablero(tablero);
                    tableroCreado = true;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al ensamblar componente: " + e.getMessage());
        }
    }
}