package com.example.myapplication.core;

import com.example.myapplication.R;

public class CONSTANTES {
    public static final String BORDES = "edge_detection";
    public static final String LINEAS= "line_extraction";
    public static final String CLASIFICACION= "clasificaction";
    public static final String PF= "pf_calculation";
    public static final String SOBEL= "Sobel";
    public static final String GC= "Extraer_Rectas_GC";
    public static final String KALANTARI= "kalantari_clasificacion";
    public static final String KALANTARI_CIRCUNFERENCIAS= "Kalantari_circunferencias";

    // Constantes para clasificar las rectas con valores predefinidos
    public static final double I_V = 0.15; // Inclinación vertical (~8.6°)
    public static final double I_H = 0.15; // Inclinación horizontal (~8.6°)
    public static final double I_C = 0.3;  // Inclinación central (moderada tolerancia)


}
