package com.example.myapplication.ui.results;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.core.CalculosPF;
import com.example.myapplication.viewModel.SharedViewModel;

import java.util.Map;

public class ResultsFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private ImageView imageView;
    private TextView resultsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_results, container, false);

        // Inicializar ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Referencias a los elementos de la vista
        imageView = root.findViewById(R.id.resultImageView);
        resultsTextView = root.findViewById(R.id.resultsTextView);
        Button showResultsButton = root.findViewById(R.id.showResultsButton);

        // Configurar acción del botón
        showResultsButton.setOnClickListener(v -> displayResults());

        return root;
    }

    private void displayResults() {
        // Obtener la imagen del SharedViewModel
        CalculosPF calculos = new CalculosPF();
        Bitmap image = sharedViewModel.getSelectedImage().getValue();
        if (image != null) {
            imageView.setImageBitmap(image);
            calculos.setImagen(image);
            // Obtener la configuración de los algoritmos del SharedViewModel
            calculos.setConfig(sharedViewModel.getAllAlgorithms().entrySet());
            StringBuilder results = new StringBuilder("Configuración seleccionada:\n");
            for (Map.Entry<String, String> entry : sharedViewModel.getAllAlgorithms().entrySet()) {
                results.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            calculos.procesar();
            calculos.getImagenes();
            calculos.getTabla();
        } else {
            imageView.setImageResource(R.drawable.ic_menu_camera);
            //no hay imagen
        }


    }
}