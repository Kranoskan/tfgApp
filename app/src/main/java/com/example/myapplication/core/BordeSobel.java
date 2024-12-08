package com.example.myapplication.core;
import android.graphics.Bitmap;
import android.graphics.Color;

public class BordeSobel {

    private Bitmap resultadoBitmap;


    public void aplicarSobel(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Crear un nuevo bitmap para almacenar el resultado
        resultadoBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Variables para almacenar los valores de los gradientes en las direcciones X y Y
        int gx, gy, g;

        // Iterar sobre cada píxel de la imagen, excepto los bordes
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {

                // Obtener los valores de los píxeles vecinos en la vecindad de 3x3
                int pixel1 = bitmap.getPixel(x - 1, y - 1);  // Arriba a la izquierda
                int pixel2 = bitmap.getPixel(x, y - 1);      // Arriba
                int pixel3 = bitmap.getPixel(x + 1, y - 1);  // Arriba a la derecha
                int pixel4 = bitmap.getPixel(x - 1, y);      // Izquierda
                int pixel5 = bitmap.getPixel(x, y);          // Centro
                int pixel6 = bitmap.getPixel(x + 1, y);      // Derecha
                int pixel7 = bitmap.getPixel(x - 1, y + 1);  // Abajo a la izquierda
                int pixel8 = bitmap.getPixel(x, y + 1);      // Abajo
                int pixel9 = bitmap.getPixel(x + 1, y + 1);  // Abajo a la derecha

                // Convertir los píxeles a valores de gris (escala de grises)
                int gris1 = (Color.red(pixel1) + Color.green(pixel1) + Color.blue(pixel1)) / 3;
                int gris2 = (Color.red(pixel2) + Color.green(pixel2) + Color.blue(pixel2)) / 3;
                int gris3 = (Color.red(pixel3) + Color.green(pixel3) + Color.blue(pixel3)) / 3;
                int gris4 = (Color.red(pixel4) + Color.green(pixel4) + Color.blue(pixel4)) / 3;
                int gris5 = (Color.red(pixel5) + Color.green(pixel5) + Color.blue(pixel5)) / 3;
                int gris6 = (Color.red(pixel6) + Color.green(pixel6) + Color.blue(pixel6)) / 3;
                int gris7 = (Color.red(pixel7) + Color.green(pixel7) + Color.blue(pixel7)) / 3;
                int gris8 = (Color.red(pixel8) + Color.green(pixel8) + Color.blue(pixel8)) / 3;
                int gris9 = (Color.red(pixel9) + Color.green(pixel9) + Color.blue(pixel9)) / 3;

                // Aplicar el operador Sobel para los gradientes en X y Y
                gx = (gris1 * -1) + (gris2 * 0) + (gris3 * 1) +
                        (gris4 * -2) + (gris5 * 0) + (gris6 * 2) +
                        (gris7 * -1) + (gris8 * 0) + (gris9 * 1);

                gy = (gris1 * -1) + (gris4 * -2) + (gris7 * -1) +
                        (gris3 * 1) + (gris6 * 2) + (gris9 * 1);

                // Calcular la magnitud del gradiente (módulo)
                g = (int) Math.sqrt(gx * gx + gy * gy);

                // Limitar el valor de g entre 0 y 255 para representar el color
                g = Math.min(255, Math.max(0, g));

                // Establecer el color en el pixel resultante
                resultadoBitmap.setPixel(x, y, Color.rgb(g, g, g));
            }
        }
    }


    public Bitmap getResultado() {
        return resultadoBitmap;
    }
}
