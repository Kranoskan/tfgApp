package com.example.myapplication.core;
import android.graphics.Bitmap;
import android.graphics.Color;

public class BordeSobel {

        private Bitmap resultado;

        public void aplicarSobel(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            // Matrices de Sobel
            int[][] Gx = {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}
            };

            int[][] Gy = {
                    {-1, -2, -1},
                    { 0,  0,  0},
                    { 1,  2,  1}
            };

            // Crear un nuevo bitmap para almacenar el resultado
            resultado = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {

                    int sumX = 0;
                    int sumY = 0;

                    // Aplicar los filtros de Sobel
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int pixel = bitmap.getPixel(x + j, y + i);
                            int gray = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3;

                            sumX += gray * Gx[i + 1][j + 1];
                            sumY += gray * Gy[i + 1][j + 1];
                        }
                    }

                    // Magnitud del gradiente
                    int magnitude = (int) Math.sqrt(sumX * sumX + sumY * sumY);
                    magnitude = Math.min(255, magnitude); // Limitar a 255

                    // Asignar el color al resultado (azul para bordes detectados)
                    int edgeColor = Color.argb(255, 0, 0, magnitude);
                    resultado.setPixel(x, y, edgeColor);
                }
            }
        }

        public Bitmap getResultado() {
            return resultado;
        }
    }
