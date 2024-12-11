package com.example.myapplication.core;

import java.util.ArrayList;
import java.util.List;

public class Line {
    public float gradient;
    public int startX, startY, endX, endY;
    public List<int[]> points; // Lista de puntos que pertenecen a esta línea (x, y)

    public Line(float gradient) {
        this.gradient = gradient;
        this.points = new ArrayList<>();
    }

    // Nueva función para obtener los valores de startX, startY, endX, endY
    public int[] getStartPoint() {
        if (points != null && !points.isEmpty()) {
            return points.get(0); // El primer punto de la lista es el start (x, y)
        }
        return null; // Retorna null si la lista está vacía
    }

    public int[] getEndPoint() {
        if (points != null && !points.isEmpty()) {
            return points.get(points.size() - 1); // El último punto de la lista es el end (x, y)
        }
        return null; // Retorna null si la lista está vacía
    }

    // Método para obtener los valores de startX, startY, endX, endY
    public int getStartX() {
        int[] startPoint = getStartPoint();
        if (startPoint != null) {
            startX=startPoint[0];
            return startPoint[0]; // Obtener el valor X del punto de inicio
        }
        return -1; // Retorna -1 si no se puede obtener el punto de inicio
    }

    public int getStartY() {
        int[] startPoint = getStartPoint();
        if (startPoint != null) {
            startY=startPoint[1];
            return startPoint[1]; // Obtener el valor Y del punto de inicio
        }
        return -1; // Retorna -1 si no se puede obtener el punto de inicio
    }

    public int getEndX() {
        int[] endPoint = getEndPoint();
        if (endPoint != null) {
            endX=endPoint[0];
            return endPoint[0]; // Obtener el valor X del punto final
        }
        return -1; // Retorna -1 si no se puede obtener el punto final
    }

    public int getEndY() {
        int[] endPoint = getEndPoint();
        if (endPoint != null) {
            endY=endPoint[1];
            return endPoint[1]; // Obtener el valor Y del punto final
        }
        return -1; // Retorna -1 si no se puede obtener el punto final
    }
}