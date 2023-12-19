package es.upm.etsiinf.haveaduck.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.upm.etsiinf.haveaduck.model.CompletePato;
import es.upm.etsiinf.haveaduck.utils.BdUtils;

public class HandlerBD {

    private SQLiteDatabase database;
    private MyDataBaseHelper dbHelper;
    private String[] patoColumns = {MyDataBaseHelper.PATO_ID, MyDataBaseHelper.PATO_IMAGE,
            MyDataBaseHelper.PATO_NAME, MyDataBaseHelper.PATO_DESCRIPTION};
    private String[] photoColumns = {MyDataBaseHelper.PHOTO_ID, MyDataBaseHelper.PHOTO_IMAGE};

    public HandlerBD (Context context){
        dbHelper = new MyDataBaseHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase(); //Aunque ponga solo writable sirve tanto para leer como escribir
    }

    public void close(){
        database.close();
    }

    //Recoge un pato de la base de datos
    public CompletePato getPato(int id){

        Cursor cursor = database.query(MyDataBaseHelper.TABLE_PATOS, patoColumns,
                MyDataBaseHelper.PATO_ID + " = " + id,
                null, null, null, null);

        cursor.moveToFirst();
        return cursorToPato(cursor);
    }

    //Devuelve todos los patos guardados en la base de datos
    public List<CompletePato> getAllPatos(){
        List<CompletePato> allPat = new ArrayList<CompletePato>();
        Cursor cursor = database.query(MyDataBaseHelper.TABLE_PATOS, patoColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CompletePato pato = cursorToPato(cursor);
            allPat.add(pato);
            cursor.moveToNext();
        }

        return allPat;
    }

    public long addPato(CompletePato pato){
        ContentValues vals = new ContentValues();

        vals.put(MyDataBaseHelper.PATO_DESCRIPTION, pato.getDescripcion());
        vals.put(MyDataBaseHelper.PATO_NAME, pato.getName());
        vals.put(MyDataBaseHelper.PATO_IMAGE, BdUtils.bitmapToByteArray(pato.getImagen()));



        //Devuelve el id asignado por la base de datos
        return database.insert(MyDataBaseHelper.TABLE_PATOS, null, vals);
    }

    public void deletePato(int id){
        database.delete(MyDataBaseHelper.TABLE_PATOS,
                MyDataBaseHelper.PATO_ID + " = " + id, null);
    }

    //Como el nombre indica, transforma objetos de tipo Cursor en CompletePatos
    private CompletePato cursorToPato(Cursor cursor){
        CompletePato pato = new CompletePato();

        pato.setId(cursor.getInt(0));
        pato.setImagen(BdUtils.byteArrayToBitmap(cursor.getBlob(1)));
        pato.setName(cursor.getString(2));
        pato.setDescripcion(cursor.getString(3));

        return pato;
    }
}
