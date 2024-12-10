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

    public static class Line {
        public float gradient;
        public List<int[]> points; // Lista de puntos que pertenecen a esta línea (x, y)

        public Line(float gradient) {
            this.gradient = gradient;
            this.points = new ArrayList<>();
        }
    }

    // Array para almacenar las rectas clasificadas
    private List<Line> classifiedLines;

    public ExtraerRectasGC() {
        classifiedLines = new ArrayList<>();
    }

    /**
     * Método para clasificar líneas en una imagen Bitmap.
     *
     * @param inputBitmap Imagen de entrada que contiene puntos.
     * @return Imagen Bitmap con las líneas clasificadas por colores.
     */
    public Bitmap classifyLines(Bitmap inputBitmap) {
        int width = inputBitmap.getWidth();
        int height = inputBitmap.getHeight();

        // Crear una imagen de salida con las mismas dimensiones
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, inputBitmap.getConfig());
        Canvas canvas = new Canvas(outputBitmap);

        // Pintura para dibujar las líneas clasificadas
        Paint paint = new Paint();
        paint.setStrokeWidth(2);

        // Variables auxiliares para detectar gradientes
        int[][] pixelArray = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelArray[x][y] = inputBitmap.getPixel(x, y) == Color.BLACK ? 1 : 0;
            }
        }

        // Detectar líneas con gradiente
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (pixelArray[x][y] == 1) {
                    float gradient = detectGradient(pixelArray, x, y);
                    Line line = getOrCreateLine(gradient);
                    line.points.add(new int[]{x, y});
                    pixelArray[x][y] = 0; // Marca el punto como procesado
                }
            }
        }

        // Dibujar las líneas clasificadas en el outputBitmap
        for (int i = 0; i < classifiedLines.size(); i++) {
            Line line = classifiedLines.get(i);

            // Asignar un color único a cada línea
            paint.setColor(Color.HSVToColor(new float[]{(i * 360f) / classifiedLines.size(), 1f, 1f}));

            for (int[] point : line.points) {
                canvas.drawPoint(point[0], point[1], paint);
            }
        }

        return outputBitmap;
    }

    /**
     * Detecta el gradiente de una línea basada en un punto inicial.
     *
     * @param pixelArray Matriz de píxeles de la imagen binaria.
     * @param x          Coordenada X del punto.
     * @param y          Coordenada Y del punto.
     * @return Gradiente de la línea.
     */
    private float detectGradient(int[][] pixelArray, int x, int y) {
        int dx = 0, dy = 0;

        // Examinar los píxeles vecinos
        if (x + 1 < pixelArray.length && pixelArray[x + 1][y] == 1) dx++;
        if (x - 1 >= 0 && pixelArray[x - 1][y] == 1) dx--;
        if (y + 1 < pixelArray[0].length && pixelArray[x][y + 1] == 1) dy++;
        if (y - 1 >= 0 && pixelArray[x][y - 1] == 1) dy--;

        // Evitar división por cero
        return (dx == 0) ? Float.MAX_VALUE : (float) dy / dx;
    }

    /**
     * Obtiene una línea existente basada en el gradiente o crea una nueva.
     *
     * @param gradient Gradiente de la línea.
     * @return Objeto Line correspondiente al gradiente.
     */
    private Line getOrCreateLine(float gradient) {
        for (Line line : classifiedLines) {
            if (Math.abs(line.gradient - gradient) < 0.1) { // Tolerancia para agrupar gradientes similares
                return line;
            }
        }

        // Crear una nueva línea si no se encuentra ninguna coincidente
        Line newLine = new Line(gradient);
        classifiedLines.add(newLine);
        return newLine;
    }

    /**
     * Obtiene las líneas clasificadas.
     *
     * @return Lista de líneas clasificadas.
     */
    public List<Line> getClassifiedLines() {
        return classifiedLines;
    }
}
