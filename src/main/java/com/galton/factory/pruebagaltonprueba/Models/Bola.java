package com.galton.factory.pruebagaltonprueba.Models;

import com.galton.factory.pruebagaltonprueba.Visualizacion;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import java.util.Random;

//Clase que representa una bola
public class Bola implements Component, Runnable {

    //Variables
    private Circle shape;
    private Random random;

    //Constructor
    public Bola() {
        this.shape = new Circle(5, Color.RED);
        this.shape.setLayoutX(300);
        this.shape.setLayoutY(30);
        this.random = new Random();
    }

    //Metodo para dibujar la bola en la visualización
    @Override
    public void dibujar(Pane root) {
        if (shape != null) {
            Platform.runLater(() -> root.getChildren().add(shape));
        } else {
            System.out.println("Error: shape is null.");
        }
    }

    //Metodo para mover la bola por el tablero
    @Override
    public void run() {
        try {
            while (shape.getLayoutY() < 350) {
                Thread.sleep(100);
                Platform.runLater(() -> {
                    if (random.nextBoolean()) {
                        shape.setLayoutX(shape.getLayoutX() + 10);
                    } else {
                        shape.setLayoutX(shape.getLayoutX() - 10);
                    }
                    shape.setLayoutY(shape.getLayoutY() + 30);
                });
            }

            Platform.runLater(() -> {
                int containerIndex = (int) ((shape.getLayoutX() - 60) / 40); // Adjust to align with the containers
                if (containerIndex >= 0 && containerIndex < 10) {
                    int containerX = containerIndex * 40 + 60;
                    shape.setLayoutX(containerX);
                    shape.setLayoutY(400);
                    Visualizacion.getInstance().actualizarHistograma(containerIndex);
                } else {
                    System.out.println("Error: containerIndex fuera de límites.");
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}