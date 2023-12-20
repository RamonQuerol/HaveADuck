package es.upm.etsiinf.haveaduck.otheractivities.showpato;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import es.upm.etsiinf.haveaduck.MainActivity;
import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter;
import es.upm.etsiinf.haveaduck.bd.HandlerBD;
import es.upm.etsiinf.haveaduck.model.CompletePato;
import es.upm.etsiinf.haveaduck.otheractivities.addfromapi.AddDataFromApiActivity;

public class ShowPatoActivity extends AppCompatActivity {

    int idPato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pato);

        //Recoge el id del pato
        Intent i = getIntent();
        idPato = i.getIntExtra(CompletePatoAdapter.ATTR_ID, 1);

        //Se coge el pato de la base de datos
        HandlerBD handlerBD = new HandlerBD(this);
        handlerBD.open();
        CompletePato pato = handlerBD.getPato(idPato);
        handlerBD.close();

        //Se muestra el pato y su información
        ((ImageView)findViewById(R.id.showPato_imageView)).setImageBitmap(pato.getImagen());
        ((TextView)findViewById(R.id.showPato_nombre_textView)).setText(pato.getName());
        ((TextView)findViewById(R.id.showPato_descripcion_textView)).setText(pato.getDescripcion());


        //Este boton vuelve a la actividad principal
        Button volverButton = findViewById(R.id.showPato_volver_button);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(ShowPatoActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });
    }
}