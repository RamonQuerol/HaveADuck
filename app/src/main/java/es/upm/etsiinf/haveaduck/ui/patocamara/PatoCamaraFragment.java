package es.upm.etsiinf.haveaduck.ui.patocamara;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter;
import es.upm.etsiinf.haveaduck.adapters.PhotoQuackAdapter;
import es.upm.etsiinf.haveaduck.bd.HandlerBD;
import es.upm.etsiinf.haveaduck.databinding.FragmentPatocamaraBinding;
import es.upm.etsiinf.haveaduck.model.PhotoQuack;
import es.upm.etsiinf.haveaduck.otheractivities.showpato.ShowPatoActivity;

public class PatoCamaraFragment extends Fragment {

    private FragmentPatocamaraBinding binding;

    private static final int REQUEST_IMAGE_CAPTURE = 16;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PatoCamaraViewModel patoCamaraViewModel =
                new ViewModelProvider(this).get(PatoCamaraViewModel.class);

        binding = FragmentPatocamaraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Se cogen los patos de la base de datos y se añaden a la lista
        HandlerBD handlerBD = new HandlerBD(root.getContext());
        handlerBD.open();
        PhotoQuackAdapter adapter = new PhotoQuackAdapter(this, handlerBD.getAllPhotosQuacks());
        handlerBD.close();

        ((GridView)root.findViewById(R.id.patoCamara_main_list)).setAdapter(adapter);

        Button newPhotoButton = root.findViewById(R.id.patoCamara_add_button);
        newPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                PhotoQuack foto = new PhotoQuack();
                foto.setImagen(bitmap);

                HandlerBD handlerBD = new HandlerBD(binding.getRoot().getContext());
                handlerBD.open();
                handlerBD.addPhotoQuack(foto);
                handlerBD.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(binding.getRoot().getContext(), "La acción se ha cancelado", Toast.LENGTH_SHORT).show();
        }
    }

}
