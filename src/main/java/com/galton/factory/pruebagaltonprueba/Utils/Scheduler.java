package com.galton.factory.pruebagaltonprueba.Utils;

import com.galton.factory.pruebagaltonprueba.Factories.Fabrica;
import java.util.List;

//Clase que se encarga de coordinar la producción de componentes en las fábricas siguiendo un algoritmo round-robin
public class Scheduler {

    //Variables
    private final List<Fabrica> fabricas;
    private int indice;
    private int maxBolas;
    private int bolasProducidas;

    //Constructor
    public Scheduler(List<Fabrica> fabricas) {
        this.fabricas = fabricas;
        this.indice = 0;
        this.maxBolas = 100;
        this.bolasProducidas = 0;
    }

    //Metodo para iniciar la producción de componentes siguiendo un algoritmo round-robin
    public void startProduction() {
        while (bolasProducidas < maxBolas) {
            Fabrica estacion = fabricas.get(indice);
            estacion.crearComponente();
            indice = (indice + 1) % fabricas.size();
            bolasProducidas++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}