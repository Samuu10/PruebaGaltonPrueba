package com.galton.factory.pruebagaltonprueba.Factories;

import com.galton.factory.pruebagaltonprueba.Models.*;
import java.util.concurrent.BlockingQueue;

//Clase que representa una estacion de trabajo que crea bolas
public class EstacionTrabajoBola extends Thread implements Fabrica {

    //Variable que representa el buffer
    private BlockingQueue<Component> buffer;
    //Variable que representa la cantidad de bolas a producir
    private int cantidad;

    //Constructor
    public EstacionTrabajoBola(BlockingQueue<Component> buffer, int cantidad) {
        this.buffer = buffer;
        this.cantidad = cantidad;
    }

    //Metodo que crea una bola y la agrega al buffer
    @Override
    public Component crearComponente() {
        for(int i = 0; i < cantidad; i++) {
            try {
                buffer.put(new Bola());
                System.out.println("Bola producida");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}