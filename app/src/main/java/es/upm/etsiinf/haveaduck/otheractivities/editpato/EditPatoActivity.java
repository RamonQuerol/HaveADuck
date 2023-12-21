package es.upm.etsiinf.haveaduck.otheractivities.editpato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import es.upm.etsiinf.haveaduck.MainActivity;
import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter;
import es.upm.etsiinf.haveaduck.bd.HandlerBD;
import es.upm.etsiinf.haveaduck.model.CompletePato;
import es.upm.etsiinf.haveaduck.otheractivities.addfromapi.AddDataFromApiActivity;
import es.upm.etsiinf.haveaduck.otheractivities.showpato.ShowPatoActivity;

public class EditPatoActivity extends AppCompatActivity {

    CompletePato pato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pato);

        //Recoge el id del pato
        Intent i = getIntent();
        int idPato = i.getIntExtra(CompletePatoAdapter.ATTR_ID, 1);

        //Se coge el pato de la base de datos
        HandlerBD handlerBD = new HandlerBD(this);
        handlerBD.open();
        pato = handlerBD.getPato(idPato);
        handlerBD.close();

        //Se muestra el pato y su información
        ((ImageView)findViewById(R.id.editPato_imageView)).setImageBitmap(pato.getImagen());
        ((EditText)findViewById(R.id.editPato_nombre_editText)).setText(pato.getName());
        ((EditText)findViewById(R.id.editPato_descripcion_editText)).setText(pato.getDescripcion());


        //Este boton vuelve a la actividad principal
        Button volverButton = findViewById(R.id.editPato_volver_button);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(EditPatoActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });

        //Este boton guarda la edicion en la base de datos
        Button finishEditButton = findViewById(R.id.editPato_guardar_button);
        finishEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se obtiene el nuevo nombre y la nueva descripcion
                pato.setName(((EditText)findViewById(R.id.editPato_nombre_editText)).getText().toString());
                pato.setDescripcion(((EditText)findViewById(R.id.editPato_descripcion_editText)).getText().toString());

                //Se edita el pato de la base de datos
                HandlerBD handlerBD = new HandlerBD(EditPatoActivity.this);
                handlerBD.open();
                handlerBD.updatePato(pato);
                handlerBD.close();

                //Ya se ha terminado lo que hay que hacer en esta actividad asi que se sale
                Intent volver = new Intent(EditPatoActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });
    }
}