package es.upm.etsiinf.haveaduck.otheractivities.showpato;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import es.upm.etsiinf.haveaduck.MainActivity;
import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter;
import es.upm.etsiinf.haveaduck.bd.HandlerBD;
import es.upm.etsiinf.haveaduck.model.CompletePato;
import es.upm.etsiinf.haveaduck.otheractivities.addfromapi.AddDataFromApiActivity;
import es.upm.etsiinf.haveaduck.otheractivities.editpato.EditPatoActivity;

public class ShowPatoActivity extends AppCompatActivity {

    CompletePato pato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pato);

        //Recoge el id del pato
        Intent i = getIntent();
        int idPato = i.getIntExtra(CompletePatoAdapter.ATTR_ID, 1);

        //Se coge el pato de la base de datos
        HandlerBD handlerBD = new HandlerBD(this);
        handlerBD.open();
        pato = handlerBD.getPato(idPato);
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

        //Boton para eliminar el pato
        Button deleteButton = findViewById(R.id.showPato_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerBD handlerBD = new HandlerBD(ShowPatoActivity.this);
                handlerBD.open();
                handlerBD.deletePato(pato.getId());
                handlerBD.close();

                Intent volver = new Intent(ShowPatoActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });

        //Botones para añadir y quitar de favoritos (Los nombres muestran el estado antes de realizar la accion)
        Button favoriteButton = findViewById(R.id.showPato_favorite_button); //Boton que muestra que ahora esta en favoritos
        Button notFavoriteButton = findViewById(R.id.showPato_notFavorite_button); //Boton que muestra que ahora no esta en favoritos

        //Como ahora mismo el pato esta en favoritos se cambiara su estado a no favorito
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerBD handlerBD = new HandlerBD(ShowPatoActivity.this);
                handlerBD.open();
                pato.setFavorite(handlerBD.toggleFavorite(pato));
                handlerBD.close();

                notFavoriteButton.setVisibility(Button.VISIBLE);
                favoriteButton.setVisibility(Button.INVISIBLE);

                Toast.makeText(ShowPatoActivity.this, pato.getName() + " ya no es de tus favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        //Como ahora mismo el pato no esta en favoritos se cambiara su estado a favorito
        notFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerBD handlerBD = new HandlerBD(ShowPatoActivity.this);
                handlerBD.open();
                pato.setFavorite(handlerBD.toggleFavorite(pato));
                handlerBD.close();

                favoriteButton.setVisibility(Button.VISIBLE);
                notFavoriteButton.setVisibility(Button.INVISIBLE);

                Toast.makeText(ShowPatoActivity.this, pato.getName() + " ahora es de tus favoritos", Toast.LENGTH_SHORT).show();

            }
        });


        //Para la edicion de patos
        Button editButton = findViewById(R.id.showPato_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowPatoActivity.this, EditPatoActivity.class);
                i.putExtra(CompletePatoAdapter.ATTR_ID,pato.getId());
                startActivity(i);
            }
        });


        //Para compartir patos
        Button shareButton = findViewById(R.id.showPato_share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);

                Uri uri = safeImage(pato.getImagen());

                //shareIntent.putExtra(Intent.EXTRA_TITLE, pato.getName());

                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, null));
            }

            private Uri safeImage(Bitmap image){
                File imageFolder = new File(ShowPatoActivity.this.getCacheDir(), "images");
                Uri uri = null;

                try {
                    imageFolder.mkdirs();
                    File file = new File(imageFolder, "imagen_compartir.jpg");
                    FileOutputStream stream = new FileOutputStream(file);
                    image.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    stream.flush();
                    stream.close();
                    uri = FileProvider.getUriForFile(Objects.requireNonNull(ShowPatoActivity.this.getApplicationContext()),
                            "es.upm.etsiinf.haveaduck.provider", file);

                }catch(Exception e){
                    e.printStackTrace();
                }

                return uri;
            }
        });

        //Toggle para hacer aparecer y desaparecer los botones
        Button optionsButton = findViewById(R.id.showPato_options_button);
        Button cancelButton = findViewById(R.id.showPato_cancel_button);

        //optionButton hace aparecer los botones de accion (los anteriores mostrados)
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.setVisibility(Button.VISIBLE);
                deleteButton.setVisibility(Button.VISIBLE);

                //Se muestra un boton u otro segun si es un pato favorito o no
                if(pato.getFavorite()>0){
                    favoriteButton.setVisibility(Button.VISIBLE);
                }else{
                    notFavoriteButton.setVisibility(Button.VISIBLE);
                }

                editButton.setVisibility(Button.VISIBLE);
                shareButton.setVisibility(Button.VISIBLE);

                optionsButton.setVisibility(Button.INVISIBLE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareButton.setVisibility(Button.INVISIBLE);
                editButton.setVisibility(Button.INVISIBLE);
                notFavoriteButton.setVisibility(Button.INVISIBLE);
                favoriteButton.setVisibility(Button.INVISIBLE);
                deleteButton.setVisibility(Button.INVISIBLE);

                optionsButton.setVisibility(Button.VISIBLE);

                cancelButton.setVisibility(Button.INVISIBLE);
            }
        });
    }
}