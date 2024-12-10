package com.example.myapplication.core;


import java.util.ArrayList;
import java.util.List;

public class ClasificarRectas {

    private static final double PI = Math.PI;

    // Constantes para clasificar las rectas
    private final double I_V; // Inclinación vertical
    private final double I_H; // Inclinación horizontal
    private final double I_C; // Inclinación central

    private List<List<ExtraerRectasGC.Line>> rectasClasificadas;
    private int nh, nv, nc, nr, nl; // Contadores para cada tipo de rectas

    public ClasificarRectas(double iv, double ih, double ic) {
        this.I_V = iv;
        this.I_H = ih;
        this.I_C = ic;
        this.rectasClasificadas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rectasClasificadas.add(new ArrayList<>());
        }
        this.nh = 0;
        this.nv = 0;
        this.nc = 0;
        this.nr = 0;
        this.nl = 0;
    }

    public void clasificacion(List<ExtraerRectasGC.Line>  rectas) {
        for (ExtraerRectasGC.Line recta : rectas) {
            double angulo = calcularAngulo(recta);

            if (esHorizontal(angulo)) {
                rectasClasificadas.get(0).add(recta);
                nh++;
            } else if (esVertical(angulo)) {
                rectasClasificadas.get(1).add(recta);
                nv++;
            } else if (esCentro(recta)) {
                rectasClasificadas.get(2).add(recta);
                nc++;
            } else if (esDerecha(angulo)) {
                rectasClasificadas.get(3).add(recta);
                nr++;
            } else if (esIzquierda(angulo)) {
                rectasClasificadas.get(4).add(recta);
                nl++;
            }
        }
    }

    private double calcularAngulo(ExtraerRectasGC.Line recta) {
        // Se asume que las líneas tienen un gradiente representativo del ángulo
        return Math.atan(recta.gradient);
    }

    private boolean esHorizontal(double angulo) {
        double minH1 = I_H;
        double maxH1 = 2 * PI - I_H;
        double minH2 = PI - I_H;
        double maxH2 = PI + I_H;

        return (angulo >= 0 && angulo < minH1) ||
                (angulo > maxH1 && angulo <= 2 * PI) ||
                (angulo > minH2 && angulo < maxH2);
    }

    private boolean esVertical(double angulo) {
        double minV1 = PI / 2 - I_V;
        double maxV1 = PI / 2 + I_V;
        double minV2 = 1.5 * PI - I_V;
        double maxV2 = 1.5 * PI + I_V;

        return (angulo > minV1 && angulo < maxV1) ||
                (angulo > minV2 && angulo < maxV2);
    }

    private boolean esCentro(ExtraerRectasGC.Line recta) {
        // Si la línea cruza el centro (definido por su intersección con C=0)
        return Math.abs(recta.gradient) <= I_C;
    }

    private boolean esDerecha(double angulo) {
        double minV1 = PI / 2 + I_V;
        double maxH1 = 2 * PI - I_H;
        return (angulo >= minV1 && angulo <= PI) ||
                (angulo >= 1.5 * PI && angulo <= maxH1);
    }

    private boolean esIzquierda(double angulo) {
        double minH1 = I_H;
        double maxV1 = PI / 2 - I_V;
        return (angulo >= minH1 && angulo <= maxV1) ||
                (angulo >= PI && angulo <= 1.5 * PI);
    }

    public List<List<ExtraerRectasGC.Line>> getRectasClasificadas() {
        return rectasClasificadas;
    }

    public int[] getContadores() {
        return new int[]{nh, nv, nc, nr, nl};
    }
}