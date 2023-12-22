package es.upm.etsiinf.haveaduck.otheractivities.addfromapi;

import static es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter.ATTR_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;

import es.upm.etsiinf.haveaduck.MainActivity;
import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.ImageOnlyPatoAdapter;
import es.upm.etsiinf.haveaduck.bd.HandlerBD;
import es.upm.etsiinf.haveaduck.model.CompletePato;
import es.upm.etsiinf.haveaduck.notifications.NotificationHandler;
import es.upm.etsiinf.haveaduck.otheractivities.showpato.ShowPatoActivity;

public class AddDataFromApiActivity extends AppCompatActivity {

    private Bitmap imagen;

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

        //Este boton guarda la informacion añadida en la base de datos
        Button guardarButton = findViewById(R.id.add_api_guardar_button);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se crea el pato a añadir
                CompletePato pato = new CompletePato();

                pato.setImagen(imagen);
                pato.setName(((EditText)findViewById(R.id.add_api_nombre_editText)).getText().toString());
                pato.setDescripcion(((EditText)findViewById(R.id.add_api_descripcion_editText)).getText().toString());

                //Se añade el pato a la base de datos
                HandlerBD handlerBD = new HandlerBD(AddDataFromApiActivity.this);
                handlerBD.open();
                long id = handlerBD.addPato(pato);
                handlerBD.close();

                //Lanzar notificacion
                NotificationHandler.createNotificationChannel(AddDataFromApiActivity.this);

                Intent irANotificacion = new Intent(AddDataFromApiActivity.this, ShowPatoActivity.class);
                irANotificacion.putExtra(ATTR_ID,(int)id);
                PendingIntent pending = PendingIntent.getActivity(AddDataFromApiActivity.this, 1, irANotificacion, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                String title = "¡Nuevo pato! 🦆";
                String content = "Tu pato " + pato.getName() + " ha sido creado, ¡visitalo!";

                Notification.Builder notification = NotificationHandler.buildNotification(AddDataFromApiActivity.this, title, content, pending);

                NotificationManager manager = (NotificationManager)AddDataFromApiActivity.this.getSystemService(NOTIFICATION_SERVICE);
                manager.notify(1, notification.build());

                //Ya se ha terminado lo que hay que hacer en esta actividad asi que se sale
                Intent volver = new Intent(AddDataFromApiActivity.this, MainActivity.class);
                startActivity(volver);
            }
        });

    }

    public void setImage(Bitmap img){
        ((ImageView)findViewById(R.id.add_api_imageView)).setImageBitmap(img);
        this.imagen = img;
    }
}