package com.galton.factory.pruebagaltonprueba.Factories;

import com.galton.factory.pruebagaltonprueba.Models.*;
import java.util.concurrent.BlockingQueue;

//Clase que representa una estacion de trabajo que crea tableros
public class EstacionTrabajoTablero implements Fabrica{

    //Variable que representa el buffer
    private BlockingQueue<Component> buffer;

    //Constructor
    public EstacionTrabajoTablero(BlockingQueue<Component> buffer) {
        this.buffer = buffer;
    }

    //Metodo que crea un tablero y lo agrega al buffer
    @Override
    public Component crearComponente() {
        try {
            buffer.put(new Tablero());
            System.out.println("Tablero producido");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}