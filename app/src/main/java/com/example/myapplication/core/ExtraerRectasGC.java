package com.example.myapplication.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExtraerRectasGC {

    private List<List<Point>> rectasResultado;
    private Bitmap originalImage;

    // Constructor
    public ExtraerRectasGC() {
        this.rectasResultado = new ArrayList<>();
    }

    /**
     * Obtiene los puntos de los bordes de la imagen.
     * @param bitmap Imagen de entrada en formato Bitmap.
     * @return Lista de puntos que representan los bordes.
     */
    public List<Point> getPuntos(Bitmap bitmap) {

        List<Point> puntosBordes = new ArrayList<>();
        originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Recorremos la imagen pixel a pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Consideramos que un píxel negro (0xFF000000) representa un borde
                if (bitmap.getPixel(x, y) == 0xFF000000) {
                    puntosBordes.add(new Point(x, y));
                }
            }
        }

        return puntosBordes;
    }

    /**
     * Ejecuta el algoritmo para extraer rectas.
     * @param bitmap Imagen de entrada en formato Bitmap.
     */
    public void ejecutar(Bitmap bitmap) {
        // Obtenemos los puntos de los bordes
        List<Point> puntosBordes = getPuntos(bitmap);

        // Aplicamos el algoritmo de agrupamiento de puntos en rectas
        rectasResultado = extraerRectas(puntosBordes);
    }

    /**
     * Devuelve las rectas extraídas como listas de puntos.
     * @return Lista de listas de puntos, donde cada lista representa una recta.
     */
    public List<List<Point>> getResultado() {
        return rectasResultado;
    }

    /**
     * Algoritmo para agrupar puntos en rectas basado en propiedades geométricas.
     * @param puntosBordes Lista de puntos que representan los bordes.
     * @return Lista de listas de puntos agrupados en rectas.
     */
    private List<List<Point>> extraerRectas(List<Point> puntosBordes) {
        List<List<Point>> rectas = new ArrayList<>();

        // Aquí implementarías el algoritmo de agrupamiento y ajuste de rectas.
        // Este es un ejemplo simple que agrupa puntos cercanos para formar "rectas".

        while (!puntosBordes.isEmpty()) {
            List<Point> rectaActual = new ArrayList<>();
            Point puntoInicial = puntosBordes.remove(0);
            rectaActual.add(puntoInicial);

            puntosBordes.removeIf(p -> {
                double distancia = Math.sqrt(Math.pow(p.x - puntoInicial.x, 2) + Math.pow(p.y - puntoInicial.y, 2));
                if (distancia < 10) { // Agrupamos puntos cercanos a 10 píxeles
                    rectaActual.add(p);
                    return true;
                }
                return false;
            });

            rectas.add(rectaActual);
        }

        return rectas;
    }

    public Bitmap getImgResultago() {
        if (rectasResultado == null || rectasResultado.isEmpty()) {
            throw new IllegalStateException("Debe ejecutar primero el método ejecutar() antes de llamar a representarRectas().");
        }

        // Crear una copia del Bitmap original
        Bitmap resultBitmap = originalImage.copy(Bitmap.Config.ARGB_8888, true);

        // Preparar para dibujar
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        // Generar colores aleatorios para cada recta
        Random random = new Random();
        for (List<Point> recta : rectasResultado) {
            paint.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

            // Dibujar la recta punto por punto
            for (int i = 0; i < recta.size() - 1; i++) {
                Point p1 = recta.get(i);
                Point p2 = recta.get(i + 1);
                canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
            }
        }

        return resultBitmap;
    }
}
