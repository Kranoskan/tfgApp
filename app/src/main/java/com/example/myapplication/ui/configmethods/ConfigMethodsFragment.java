package com.example.myapplication.ui.configmethods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.core.CONSTANTES;
import com.example.myapplication.viewModel.SharedViewModel;

public class ConfigMethodsFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config_methods, container, false);

        // Obtener instancia del SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Configurar los Spinners
        setupSpinner(root, R.id.spinner_edge_detection, R.array.edge_detection_algorithms, CONSTANTES.BORDES);
        setupSpinner(root, R.id.spinner_line_extraction, R.array.line_extraction_algorithms, CONSTANTES.LINEAS);
        setupSpinner(root, R.id.spinner_circle_estimation, R.array.circle_estimation_algorithms, CONSTANTES.CIRCUNFERENCIAS);
        setupSpinner(root, R.id.spinner_pf_calculation, R.array.pf_calculation_algorithms, CONSTANTES.PF);

        return root;
    }

    private void setupSpinner(View root, int spinnerId, int arrayResourceId, final String configKey) {
        Spinner spinner = root.findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                arrayResourceId,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Observar el valor almacenado en el SharedViewModel y actualizar la selección del Spinner
        sharedViewModel.getAlgorithm(configKey).observe(getViewLifecycleOwner(), algorithm -> {
            if (algorithm != null) {
                int position = adapter.getPosition(algorithm);
                if (position >= 0) {
                    spinner.setSelection(position);
                }
            }
        });

        // Guardar la selección en el SharedViewModel
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAlgorithm = parent.getItemAtPosition(position).toString();
                sharedViewModel.setAlgorithm(configKey, selectedAlgorithm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Opcional: Manejar caso donde no se selecciona ningún valor
            }
        });
    }
}

