package com.example.myapplication.ui.results;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.viewModel.SharedViewModel;

import java.util.Map;
import java.util.Set;

public class ResultsFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private LinearLayout imagesContainer;
    private ImageView imageView, edgesImageView, rectasImageView, rectasAgrupView;
    private TextView resultsTextView;
    private Button showResultsButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_results, container, false);

        // Inicializar vistas y ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        imagesContainer = root.findViewById(R.id.imagesContainer);
        imageView = root.findViewById(R.id.resultImageView);
        edgesImageView = root.findViewById(R.id.edgesImageView);
        rectasImageView = root.findViewById(R.id.rectasImageView);
        rectasAgrupView = root.findViewById(R.id.rectasAgrupView);
        resultsTextView = root.findViewById(R.id.resultsTextView);
        showResultsButton = root.findViewById(R.id.showResultsButton);

        showResultsButton.setOnClickListener(v -> displayResults());
        return root;
    }

    private void displayResults() {
        Bitmap image = sharedViewModel.getSelectedImage().getValue();
        if (image == null) {
            resultsTextView.setText("No se seleccionó ninguna imagen.");
            return;
        }

        // Mostrar el mensaje inicial y esconder imágenes mientras se procesa
        resultsTextView.setText("Procesando imagen...");
        imagesContainer.setVisibility(View.GONE);

        // Configurar los algoritmos y procesar la imagen
        Set<Map.Entry<String, String>> algorithmsConfig = sharedViewModel.getAllAlgorithms().entrySet();
        sharedViewModel.processImage(image, algorithmsConfig);

        // Observar el resultado del procesamiento de bordes
        sharedViewModel.getEdgesImageLiveData().observe(getViewLifecycleOwner(), edgesImage -> {
            if (edgesImage != null) {
                // Mostrar la imagen de bordes
                imageView.setImageBitmap(image);
                edgesImageView.setImageBitmap(edgesImage);
                imagesContainer.setVisibility(View.VISIBLE);
                resultsTextView.setVisibility(View.VISIBLE);
                resultsTextView.setText(buildConfigText(sharedViewModel.getAllAlgorithms()));

                // Actualizar el texto del botón
                showResultsButton.setText("Volver a calcular");
            }
        });

        // Observar el resultado del procesamiento de rectas
        sharedViewModel.getRectasImageLiveData().observe(getViewLifecycleOwner(), rectasImage -> {
            if (rectasImage != null) {
                // Mostrar la imagen de rectas
                rectasImageView.setImageBitmap(rectasImage);
            }
        });

        sharedViewModel.getrectasAgrupLiveData().observe(getViewLifecycleOwner(), rectasAgrup -> {
            if (rectasAgrup != null) {
                // Mostrar la imagen del resultado final
                rectasAgrupView.setImageBitmap(rectasAgrup);
                rectasAgrupView.setVisibility(View.VISIBLE); // Asegúrate de que sea visible
            }
        });


    }






    private String buildConfigText(Map<String, String> config) {
        StringBuilder results = new StringBuilder("Configuración seleccionada:\n");
        config.forEach((key, value) -> results.append(key).append(": ").append(value).append("\n"));
        return results.toString();
    }
}
