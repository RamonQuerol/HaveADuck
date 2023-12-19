package es.upm.etsiinf.haveaduck.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    //Datos base de datos
    private static String DB_NAME = "quack_db.bd";
    private static int VERSION_ACTUAL = 2;

    //Datos de patos guardados
    public static final String TABLE_PATOS = "patos";
    public static final String PATO_ID = "_id";
    public static final String PATO_IMAGE = "image";
    public  static  final  String PATO_NAME = "name";
    public static final String PATO_DESCRIPTION = "description";

    //Datos de fotos sacadas desde el dispositivo
    public static final String TABLE_PHOTOS = "camera";
    public static final String PHOTO_ID = "_id";
    public static final String PHOTO_IMAGE = "image";


    //Secuencias SQL para crear la base de datos
    private static final String PATO_TABLE_CREATE = "create table "
            + TABLE_PATOS + "(" + PATO_ID
            + " integer primary key autoincrement, " +  PATO_IMAGE
            + " blob not null, " + PATO_NAME + " text not null, " + PATO_DESCRIPTION + " text not null);";
    private static final String PHOTO_TABLE_CREATE = "create table " + TABLE_PHOTOS + "(" + PHOTO_ID
            + " integer primary key autoincrement, " +  PHOTO_IMAGE + " blob not null);";

    public MyDataBaseHelper(Context context){
        super(context,DB_NAME,null,VERSION_ACTUAL);
    }

    //Se ejecutara al instalar la aplicacion
    //La primera vez que accedemos a la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Script para crear la BBDD
        db.execSQL(PATO_TABLE_CREATE);
        db.execSQL(PHOTO_TABLE_CREATE);
    }


    //Se ejecuta cuando se actualiza la version de la base datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(MyDataBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }
}
