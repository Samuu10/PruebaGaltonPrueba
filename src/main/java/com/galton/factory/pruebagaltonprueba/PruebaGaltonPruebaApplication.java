package com.galton.factory.pruebagaltonprueba;

import com.galton.factory.pruebagaltonprueba.Models.Component;
import com.galton.factory.pruebagaltonprueba.Models.Tablero;
import com.galton.factory.pruebagaltonprueba.Utils.Buffer;
import com.galton.factory.pruebagaltonprueba.Factories.EstacionTrabajoBola;
import com.galton.factory.pruebagaltonprueba.Factories.EstacionTrabajoTablero;
import com.galton.factory.pruebagaltonprueba.Factories.Fabrica;
import com.galton.factory.pruebagaltonprueba.Utils.Ensamblador;
import com.galton.factory.pruebagaltonprueba.Utils.Scheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class PruebaGaltonPruebaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PruebaGaltonPruebaApplication.class, args);

        //Creación del buffer compartido
        Buffer bufferCompartido = new Buffer(10);
        //Creación de la cola de componentes
        BlockingQueue<Component> buffer = bufferCompartido.getBuffer();

        //Creación de la estación de trabajo del tablero
        Fabrica estacionTrabajoTablero = new EstacionTrabajoTablero(buffer);
        //Creación de la estación de trabajo de la bola con 1000 bolas
        Fabrica estacionTrabajoBola = new EstacionTrabajoBola(buffer, 1);

        //Listamos las estaciones de trabajo para el scheduler
        estacionTrabajoTablero.crearComponente();
        List<Fabrica> estacionesTrabajo = Arrays.asList(estacionTrabajoBola);

        //Creamos un CountDownLatch para que el hilo principal espere a que el hilo de JavaFX se inicialice
        CountDownLatch latch = new CountDownLatch(1);

        //Inicializamos la visualización de JavaFx en un hilo separado
        new Thread(() -> Visualizacion.iniciarVisualizacion(new String[] {}, latch)).start();

        try {
            //Esperamos a que JavaFX se inicialice
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Creamos el ensamblador y lo iniciamos
        Ensamblador ensamblador = new Ensamblador(buffer, Visualizacion.getInstance());
        ensamblador.start();

        //Creamos el scheduler y lo iniciamos
        Scheduler scheduler = new Scheduler(estacionesTrabajo);
        new Thread(scheduler::startProduction).start();
    }
}