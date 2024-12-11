package com.example.myapplication.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

public class GenerarCirculos {
    Circle circle;

    public GenerarCirculos(){

    }

        public void calculateCircleFromPoints(List<Circle.Point> points) {
            if (points.size() < 3) {
                throw new IllegalArgumentException("Se requieren al menos tres puntos para calcular una circunferencia.");
            }

            // Tomar tres puntos iniciales
            Circle.Point p1 = points.get(0);
            Circle.Point p2 = points.get(1);
            Circle.Point p3 = points.get(2);

            // Matrices para resolver el sistema de ecuaciones
            double[][] A = {
                    {p1.x, p1.y, 1},
                    {p2.x, p2.y, 1},
                    {p3.x, p3.y, 1}
            };

            double[] t = {
                    p1.x * p1.x + p1.y * p1.y,
                    p2.x * p2.x + p2.y * p2.y,
                    p3.x * p3.x + p3.y * p3.y
            };

            // Resolver el sistema de ecuaciones lineales A * X = t
            double[] X = solveLinearSystem(A, t);

            // Calcular centro y radio de la circunferencia
            double centerX = X[0] / 2;
            double centerY = X[1] / 2;
            double radius = Math.sqrt(centerX * centerX + centerY * centerY + X[2]);

            circle= new Circle(new Circle.Point(centerX, centerY), radius);
        }

        /**
         * Método para resolver un sistema de ecuaciones lineales 3x3 utilizando eliminación de Gauss.
         *
         * @param A Matriz de coeficientes 3x3.
         * @param t Vector de términos independientes.
         * @return Vector solución X.
         */
        private static double[] solveLinearSystem(double[][] A, double[] t) {
            int n = 3;

            // Eliminación Gaussiana
            for (int i = 0; i < n; i++) {
                // Escalar la fila para hacer A[i][i] = 1
                double scale = A[i][i];
                for (int j = 0; j < n; j++) {
                    A[i][j] /= scale;
                }
                t[i] /= scale;

                // Hacer ceros en las columnas restantes
                for (int k = 0; k < n; k++) {
                    if (k != i) {
                        double factor = A[k][i];
                        for (int j = 0; j < n; j++) {
                            A[k][j] -= factor * A[i][j];
                        }
                        t[k] -= factor * t[i];
                    }
                }
            }

            return t;
        }

    public Bitmap imgCircunferencia(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        // Dibujar la circunferencia
        canvas.drawCircle(
                (float) circle.center.x,
                (float) circle.center.y,
                (float) circle.radius,
                paint
        );

        return bitmap;
    }

}

