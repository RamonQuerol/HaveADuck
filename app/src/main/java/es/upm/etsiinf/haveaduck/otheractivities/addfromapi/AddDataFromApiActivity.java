package es.upm.etsiinf.haveaduck.otheractivities.addfromapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import es.upm.etsiinf.haveaduck.MainActivity;
import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.ImageOnlyPatoAdapter;

public class AddDataFromApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_from_api);

        //Se recoge la url de la imagen en la que se hizo click
        Intent i = getIntent();
        String url = i.getStringExtra(ImageOnlyPatoAdapter.ATTR_URL);

        //Se llama a un thread para volver a descargar la imagen
        Thread th = new Thread(new DownloadThatPatoThread(this, url));
        th.start();


        //Este boton vuelve a la actividad principal
        Button volverButton = findViewById(R.id.add_api_volver_button);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(AddDataFromApiActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });



    }

    public void setImage(Bitmap img){
        ((ImageView)findViewById(R.id.add_api_imageView)).setImageBitmap(img);
    }
}