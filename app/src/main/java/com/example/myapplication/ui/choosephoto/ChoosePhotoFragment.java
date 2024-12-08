package com.example.myapplication.ui.choosephoto;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.viewModel.SharedViewModel;

public class ChoosePhotoFragment extends Fragment {

    private ImageView selectedImageView;
    private Button selectImageButton;
    private SharedViewModel sharedViewModel;

    // Definimos el ActivityResultLauncher
    private final ActivityResultLauncher<Intent> selectImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(androidx.activity.result.ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            // Obtenemos la URI de la imagen seleccionada
                            android.net.Uri selectedImageUri = data.getData();
                            try {
                                // Convertimos la URI a Bitmap
                                Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);

                                // Actualizamos la imagen en el ViewModel
                                sharedViewModel.setSelectedImage(selectedImageBitmap);

                                // Mostramos la imagen en la ImageView
                                updateImageView(selectedImageBitmap);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Comprobar permisos de lectura
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        // Inflar el layout
        View view = inflater.inflate(R.layout.fragment_choose_photo, container, false);

        // Encontrar componentes en el layout
        selectedImageView = view.findViewById(R.id.selectedImageView);
        selectImageButton = view.findViewById(R.id.selectImageButton);

        // Obtener instancia del SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observar la imagen en el ViewModel
        sharedViewModel.getSelectedImage().observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                updateImageView(bitmap);
            }
        });

        // Setear el click listener para el botón
        selectImageButton.setOnClickListener(v -> {
            // Abrir la galería para seleccionar una imagen
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            selectImageLauncher.launch(intent); // Usar el launcher
        });

        return view;
    }

    private void updateImageView(Bitmap bitmap) {
        selectedImageView.setImageBitmap(bitmap);
        selectedImageView.setVisibility(View.VISIBLE); // Asegurar que la ImageView sea visible
        selectImageButton.setText("Cambiar imagen"); // Cambiar el texto del botón
    }
}
