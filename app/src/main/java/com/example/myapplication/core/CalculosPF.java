package com.example.myapplication.core;

import android.content.Context;
import android.graphics.Bitmap;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalculosPF {
    Bitmap Image;
    Map<String, String> configAlg;
    Bitmap bordes;
    Bitmap rectas;
    Boolean finBordes;
    private Context context;
    private List<ExtraerRectasGC.Line> rectasArray;

    public CalculosPF() {
        finBordes=false;

    }

    public void setImagen(Bitmap inputImage){
        Image=inputImage;
    }

    public void setConfig (Set<Map.Entry<String, String>> config){
        if (configAlg == null) {
            configAlg = new HashMap<>(); // Inicializar si es necesario
        } else {
            configAlg.clear(); // Limpiar valores previos si es necesario
        }

        for (Map.Entry<String, String> entry : config) {
            configAlg.put(entry.getKey(), entry.getValue());
        }
    }

    public CalculosPF(Context context) {
        this.context = context;
    }

    public void procesar() {
        switch (configAlg.get(CONSTANTES.BORDES)) {
            case CONSTANTES.SOBEL:
                BordeSobel bordeSobel = new BordeSobel();
                bordeSobel.aplicarSobel(Image);
                bordes = bordeSobel.getResultado();
                break;
        }
        switch (configAlg.get(CONSTANTES.LINEAS)) {
            case CONSTANTES.GC:
                ExtraerRectasGC exRectGC = new ExtraerRectasGC();
                rectas=exRectGC.classifyLines(bordes);
                rectasArray=exRectGC.getClassifiedLines();
                break;
        }
    }


    public Bitmap getBordes() {
        return bordes;
    }

    public List<List<String>> getTabla() {
        return null;
    }


    public Bitmap getRectas() {
        return rectas;
    }

    public List<ExtraerRectasGC.Line> getRectasArray() {
        return rectasArray;
    }
}
