package com.example.myapplication.viewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.core.CalculosPF;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharedViewModel extends ViewModel {

    private final Map<String, MutableLiveData<String>> algorithmsMap = new HashMap<>();
    private final MutableLiveData<Bitmap> selectedImage = new MutableLiveData<>();

    private MutableLiveData<Bitmap> edgesImageLiveData = new MutableLiveData<>();
    private MutableLiveData<Bitmap> rectasLiveData = new MutableLiveData<>();
    private MutableLiveData<Bitmap> rectasAgrupLiveData = new MutableLiveData<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    // Métodos para la imagen seleccionada
    public LiveData<Bitmap> getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Bitmap image) {
        selectedImage.setValue(image);
    }

    // Métodos para los algoritmos
    public LiveData<String> getAlgorithm(String key) {
        if (!algorithmsMap.containsKey(key)) {
            algorithmsMap.put(key, new MutableLiveData<>());
        }
        return algorithmsMap.get(key);
    }

    public void setAlgorithm(String key, String value) {
        if (!algorithmsMap.containsKey(key)) {
            algorithmsMap.put(key, new MutableLiveData<>());
        }
        algorithmsMap.get(key).setValue(value);
    }

    public Map<String, String> getAllAlgorithms() {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, MutableLiveData<String>> entry : algorithmsMap.entrySet()) {
            if (entry.getValue().getValue() != null) {
                result.put(entry.getKey(), entry.getValue().getValue());
            }
        }
        return result;
    }

    public LiveData<Bitmap> getEdgesImageLiveData() {
        return edgesImageLiveData;
    }


    public LiveData<Bitmap> getRectasImageLiveData() {
        return rectasLiveData;
    }
    public LiveData<Bitmap> getrectasAgrupLiveData() { return rectasAgrupLiveData;}


    public void processImage(Bitmap image, Set<Map.Entry<String, String>> algorithmsConfig) {
        executor.execute(() -> {
            int a=8;
            int b=0;
            int g=6;
            int r=0;
            CalculosPF calculos = new CalculosPF();
            calculos.setImagen(image);
            calculos.setConfig(algorithmsConfig);
            calculos.procesar();
            // Obtener los bordes procesados y actualizar el LiveData
            Bitmap edgesImage = calculos.getBordes();
            Bitmap rectasImage = calculos.getRectas();
            Bitmap rectasAgrupImage = calculos.getRectasAgrup();
            edgesImageLiveData.postValue(edgesImage);
            rectasLiveData.postValue(rectasImage);
            rectasAgrupLiveData.postValue(rectasAgrupImage);
        });
    }
}

