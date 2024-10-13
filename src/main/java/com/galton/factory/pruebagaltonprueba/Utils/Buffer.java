package com.galton.factory.pruebagaltonprueba.Utils;

import com.galton.factory.pruebagaltonprueba.Models.Component;
import lombok.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//Clase que representa un buffer compartido entre productores y consumidores
@Getter
@Setter
public class Buffer {

    //Variable
    private final BlockingQueue<Component> buffer;

    //Constructor
    public Buffer(int espacio) {
        this.buffer = new ArrayBlockingQueue<>(espacio);
    }

    //Metodo que agrega un componente al buffer
    public void agregar(Component componente) {
        try {
            buffer.put(componente); // Coloca un componente en el buffer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al agregar componente al buffer: " + e.getMessage());
        }
    }
}