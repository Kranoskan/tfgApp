package com.example.myapplication.core;

public class Circle {
    public static class Point {
        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

        public Point center;
        public double radius;

        public Circle(Point center, double radius) {
            this.center = center;
            this.radius = radius;
        }

}
