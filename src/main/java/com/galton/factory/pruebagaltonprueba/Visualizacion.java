package com.galton.factory.pruebagaltonprueba;

import com.galton.factory.pruebagaltonprueba.Models.Bola;
import com.galton.factory.pruebagaltonprueba.Models.Tablero;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

//Clase que representa la visualización de la simulación
public class Visualizacion extends Application {

    //Variables
    private static CountDownLatch latch;
    private static Pane root;
    private List<Bola> bolas;
    private Tablero tablero;
    private Rectangle[] histogramBars;
    private Text[] ballCounts;
    private int[] ballCountValues;
    private static Visualizacion instance = new Visualizacion();

    //Constructor
    public Visualizacion() {
        instance = this;
        this.bolas = new ArrayList<>();
        this.histogramBars = new Rectangle[10];
        this.ballCounts = new Text[10];
        this.ballCountValues = new int[10];
    }

    public static Visualizacion getInstance() {
        return instance;
    }

    //Metodo para inicializar la visualización
    public static void iniciarVisualizacion(String[] args, CountDownLatch latch) {
        Visualizacion.latch = latch;
        launch(args);
    }

    //Metodo para inicializar la visualización
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        Scene scene = new Scene(root, 600, 800);

        bolas = new ArrayList<>();

        primaryStage.setTitle("Simulación Tablero de Galton");
        primaryStage.setScene(scene);
        primaryStage.show();


        for (int i = 0; i < histogramBars.length; i++) {
            histogramBars[i] = new Rectangle(40, 0, Color.GREEN); // Adjust the width of the bars
            histogramBars[i].setLayoutX(60 + i * 40); // Align with the containers
            histogramBars[i].setLayoutY(750); // Separate more from the board
            root.getChildren().add(histogramBars[i]);

            ballCounts[i] = new Text("0");
            ballCounts[i].setLayoutX(75 + i * 40); // Align with the bars
            ballCounts[i].setLayoutY(750);
            root.getChildren().add(ballCounts[i]);
        }


        latch.countDown();
    }

    //Metodo para agregar el tablero a la visualización
    public void agregarTablero(Tablero tablero) {
        this.tablero = tablero;

        Platform.runLater(() -> {
            if (root != null) {
                tablero.dibujar(root);
            } else {
                System.out.println("Error: root is not initialized.");
            }
        });
    }

    //Metodo para agregar una bola a la visualización y animarla
    public void agregarBola(Bola bola) {
        bolas.add(bola);

        Platform.runLater(() -> {
            if (root != null) {
                bola.dibujar(root);
                new Thread(bola).start();
            } else {
                System.out.println("Error: root is not initialized.");
            }
        });
    }

    //Metodo para actualizar el histograma a medida que las bolas caen en los contenedores
    public void actualizarHistograma(int containerIndex) {
        Platform.runLater(() -> {
            if (containerIndex >= 0 && containerIndex < histogramBars.length) {
                if (histogramBars[containerIndex] != null) {
                    histogramBars[containerIndex].setHeight(histogramBars[containerIndex].getHeight() + 10);
                    histogramBars[containerIndex].setLayoutY(histogramBars[containerIndex].getLayoutY() - 10);
                    ballCountValues[containerIndex]++;
                    ballCounts[containerIndex].setText(String.valueOf(ballCountValues[containerIndex]));
                } else {
                    System.out.println("Error: histogramBars[" + containerIndex + "] es null.");
                }
            } else {
                System.out.println("Error: containerIndex fuera de límites.");
            }
        });
    }
}