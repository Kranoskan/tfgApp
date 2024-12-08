package com.example.myapplication.viewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class SharedViewModel extends ViewModel {

    private final Map<String, MutableLiveData<String>> algorithmsMap = new HashMap<>();
    private final MutableLiveData<Bitmap> selectedImage = new MutableLiveData<>();

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
}

