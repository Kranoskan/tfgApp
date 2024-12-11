package com.example.myapplication.core;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class ClasificarRectas {

    private static final double PI = Math.PI;

    private List<List<Line>> rectasClasificadas;
    private int nh, nv, nc, nr, nl; // Contadores para cada tipo de rectas

    public ClasificarRectas() {
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

    public void clasificacion(List<Line>  rectas) {
        for (Line recta : rectas) {
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

    private double calcularAngulo(Line recta) {
        if (Double.isNaN(recta.gradient) || Double.isInfinite(recta.gradient)) {
            // Manejar caso de gradiente inválido
            return 0;  // O un valor por defecto
        }
        return Math.atan(recta.gradient);
    }

    private boolean esHorizontal(double angulo) {
        double minH1 = CONSTANTES.I_H;
        double maxH1 = 2 * PI - CONSTANTES.I_H;
        double minH2 = PI - CONSTANTES.I_H;
        double maxH2 = PI + CONSTANTES.I_H;

        return (angulo >= 0 && angulo < minH1) ||
                (angulo > maxH1 && angulo <= 2 * PI) ||
                (angulo > minH2 && angulo < maxH2);
    }

    private boolean esVertical(double angulo) {
        double minV1 = PI / 2 - CONSTANTES.I_V;
        double maxV1 = PI / 2 + CONSTANTES.I_V;
        double minV2 = 1.5 * PI - CONSTANTES.I_V;
        double maxV2 = 1.5 * PI + CONSTANTES.I_V;

        return (angulo > minV1 && angulo < maxV1) ||
                (angulo > minV2 && angulo < maxV2);
    }

    private boolean esCentro(Line recta) {
        // Si la línea cruza el centro (definido por su intersección con C=0)
        return Math.abs(recta.gradient) <= CONSTANTES.I_C;
    }

    private boolean esDerecha(double angulo) {
        double minV1 = PI / 2 + CONSTANTES.I_V;
        double maxH1 = 2 * PI - CONSTANTES.I_H;
        return (angulo >= minV1 && angulo <= PI) ||
                (angulo >= 1.5 * PI && angulo <= maxH1);
    }

    private boolean esIzquierda(double angulo) {
        double minH1 = CONSTANTES.I_H;
        double maxV1 = PI / 2 - CONSTANTES.I_V;
        return (angulo >= minH1 && angulo <= maxV1) ||
                (angulo >= PI && angulo <= 1.5 * PI);
    }

    public List<List<Line>> getRectasClasificadas() {
        return rectasClasificadas;
    }

    public int[] getContadores() {
        return new int[]{nh, nv, nc, nr, nl};
    }

    public Bitmap generarImagen(Bitmap inputBitmap) {
        // Crear el Bitmap de tamaño tamX, tamY
        int width = inputBitmap.getWidth();
        int height = inputBitmap.getHeight();

        // Crear una imagen de salida con las mismas dimensiones
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, inputBitmap.getConfig());
        Canvas canvas = new Canvas(outputBitmap);  // Crear un Canvas para dibujar en el Bitmap

        // Crear un Paint para las líneas
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);  // Color predeterminado de las líneas
        paint.setStrokeWidth(3);  // Ancho de la línea

        // Dibujar las líneas clasificadas
        for (int i = 0; i < rectasClasificadas.size(); i++) {
            List<Line> lineas = rectasClasificadas.get(i);

            // Asignar un color al subconjunto actual
            paint.setColor(Color.HSVToColor(new float[]{(i * 360f) / rectasClasificadas.size(), 1f, 1f}));

            // Dibujar las líneas del subconjunto
            for (Line line : lineas) {
                int startX = line.getStartX();
                int startY = line.getStartY();
                int endX = line.getEndX();
                int endY = line.getEndY();

                // Verificar que las coordenadas de la línea sean válidas
                if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
                    canvas.drawLine(startX, startY, endX, endY, paint);
                }
            }
        }

        // Devolver el Bitmap con las líneas dibujadas
        return outputBitmap;
    }


}