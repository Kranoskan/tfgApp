package com.example.myapplication.core;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalculosPF {
    Bitmap Image;
    ArrayList<String> configAlg;

    public void setImagen(Bitmap inputImage){
        Image=inputImage;
    }

    public void setConfig (Set<Map.Entry<String, String>> config){
        configAlg=new ArrayList<>();
        for (Map.Entry<String, String> entry : config) {
            configAlg.add(entry.getValue());
        }
    }

    public void procesar() {
    }

    public Bitmap getImagenes() {
        return Image;
    }

    public List<List<String>> getTabla() {
        return null;
    }
}
