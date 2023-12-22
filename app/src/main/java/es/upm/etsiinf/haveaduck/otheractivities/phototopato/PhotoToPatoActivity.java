package es.upm.etsiinf.haveaduck.otheractivities.phototopato;

import static es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter.ATTR_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import es.upm.etsiinf.haveaduck.MainActivity;
import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter;
import es.upm.etsiinf.haveaduck.adapters.PhotoQuackAdapter;
import es.upm.etsiinf.haveaduck.bd.HandlerBD;
import es.upm.etsiinf.haveaduck.model.CompletePato;
import es.upm.etsiinf.haveaduck.model.PhotoQuack;
import es.upm.etsiinf.haveaduck.notifications.NotificationHandler;
import es.upm.etsiinf.haveaduck.otheractivities.addfromapi.AddDataFromApiActivity;
import es.upm.etsiinf.haveaduck.otheractivities.showpato.ShowPatoActivity;

public class PhotoToPatoActivity extends AppCompatActivity {

    PhotoQuack foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_to_pato);

        //Recoge el id del pato
        Intent i = getIntent();
        int idPhoto = i.getIntExtra(PhotoQuackAdapter.ATTR_ID, 1);

        //Se coge el pato de la base de datos
        HandlerBD handlerBD = new HandlerBD(this);
        handlerBD.open();
        foto = handlerBD.getPhotoQuack(idPhoto);
        handlerBD.close();

        //Se muestra el pato y su información
        ((ImageView)findViewById(R.id.photoToPato_imageView)).setImageBitmap(foto.getImagen());

        //Este boton vuelve a la actividad principal
        Button volverButton = findViewById(R.id.photoToPato_volver_button);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(PhotoToPatoActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });

        //Este boton guarda la informacion añadida en la base de datos
        Button guardarButton = findViewById(R.id.photoToPato_guardar_button);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se crea el pato a añadir
                CompletePato pato = new CompletePato();

                pato.setImagen(foto.getImagen());
                pato.setName(((EditText)findViewById(R.id.photoToPato_nombre_editText)).getText().toString());
                pato.setDescripcion(((EditText)findViewById(R.id.photoToPato_descripcion_editText)).getText().toString());

                //Se añade el pato a la base de datos
                HandlerBD handlerBD = new HandlerBD(PhotoToPatoActivity.this);
                handlerBD.open();
                long id = handlerBD.addPato(pato);
                handlerBD.close();

                //Lanzar notificacion
                NotificationHandler.createNotificationChannel(PhotoToPatoActivity.this);

                Intent irANotificacion = new Intent(PhotoToPatoActivity.this, ShowPatoActivity.class);
                irANotificacion.putExtra(ATTR_ID,(int)id);
                PendingIntent pending = PendingIntent.getActivity(PhotoToPatoActivity.this, 1, irANotificacion, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                String title = "¡Nuevo pato! 🦆";
                String content = "Tu pato " + pato.getName() + " ha sido creado, ¡visitalo!";

                Notification.Builder notification = NotificationHandler.buildNotification(PhotoToPatoActivity.this, title, content, pending);

                NotificationManager manager = (NotificationManager)PhotoToPatoActivity.this.getSystemService(NOTIFICATION_SERVICE);
                manager.notify(1, notification.build());

                //Ya se ha terminado lo que hay que hacer en esta actividad asi que se sale
                Intent volver = new Intent(PhotoToPatoActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });
    }
}