package es.upm.etsiinf.haveaduck.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.upm.etsiinf.haveaduck.model.CompletePato;
import es.upm.etsiinf.haveaduck.model.PhotoQuack;
import es.upm.etsiinf.haveaduck.utils.BdUtils;

public class HandlerBD {

    private SQLiteDatabase database;
    private MyDataBaseHelper dbHelper;
    private String[] patoColumns = {MyDataBaseHelper.PATO_ID, MyDataBaseHelper.PATO_IMAGE,
            MyDataBaseHelper.PATO_NAME, MyDataBaseHelper.PATO_DESCRIPTION, MyDataBaseHelper.PATO_FAVORITO};
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


    /////Operaciones patos//////


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

        Cursor cursor = database.query(MyDataBaseHelper.TABLE_PATOS, patoColumns,
                null, null, null, null, null);

        return cursorToMultiplePatos(cursor);
    }

    //Devuelve todos los patos favoritos
    public List<CompletePato> getAllFavorites(){
        Cursor cursor = database.query(MyDataBaseHelper.TABLE_PATOS, patoColumns,
                MyDataBaseHelper.PATO_FAVORITO + " = " + 1,
                null, null, null, null);

        return cursorToMultiplePatos(cursor);
    }

    //Este metodo cambia el estado de favoritos a no favoritos y biceversa
    public int toggleFavorite(CompletePato pato){
        int newState;
        if(pato.getFavorite()>0){
            newState = 0;
        }else{
            newState = 1;
        }

        ContentValues vals = new ContentValues();

        vals.put(MyDataBaseHelper.PATO_FAVORITO, newState);

        database.update(MyDataBaseHelper.TABLE_PATOS, vals,
                MyDataBaseHelper.PATO_ID + " = " + pato.getId(), null);

        return newState;
    }

    //Añade un pato a la base de datos
    public long addPato(CompletePato pato){
        ContentValues vals = new ContentValues();

        vals.put(MyDataBaseHelper.PATO_IMAGE, BdUtils.bitmapToByteArray(pato.getImagen()));
        vals.put(MyDataBaseHelper.PATO_NAME, pato.getName());
        vals.put(MyDataBaseHelper.PATO_DESCRIPTION, pato.getDescripcion());
        vals.put(MyDataBaseHelper.PATO_FAVORITO, pato.getFavorite());


        //Devuelve el id asignado por la base de datos
        return database.insert(MyDataBaseHelper.TABLE_PATOS, null, vals);
    }

    public int updatePato(CompletePato pato){
        ContentValues vals = new ContentValues();

        vals.put(MyDataBaseHelper.PATO_IMAGE, BdUtils.bitmapToByteArray(pato.getImagen()));
        vals.put(MyDataBaseHelper.PATO_NAME, pato.getName());
        vals.put(MyDataBaseHelper.PATO_DESCRIPTION, pato.getDescripcion());

        //Devuelve el id del pato
        return database.update(MyDataBaseHelper.TABLE_PATOS, vals,
                MyDataBaseHelper.PATO_ID + " = " + pato.getId(), null);
    }

    //Elimina un pato de la base de datos
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
        pato.setFavorite((cursor.getInt(4)));

        return pato;
    }

    //Recoge todos los patos que haya en el cursor
    private List<CompletePato> cursorToMultiplePatos(Cursor cursor){
        List<CompletePato> allPat = new ArrayList<CompletePato>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CompletePato pato = cursorToPato(cursor);
            allPat.add(pato);
            cursor.moveToNext();
        }
        return allPat;
    }


    /////Operaciones fotoPatos//////

    //Recoge una foto personal de la base de datos
    public PhotoQuack getPhotoQuack(int id){

        Cursor cursor = database.query(MyDataBaseHelper.TABLE_PHOTOS, photoColumns,
                MyDataBaseHelper.PHOTO_ID + " = " + id,
                null, null, null, null);

        cursor.moveToFirst();
        return cursorToPhotoQuack(cursor);
    }

    //Devuelve todas las fotos personales guardadas en la base de datos
    public List<PhotoQuack> getAllPhotosQuacks(){

        Cursor cursor = database.query(MyDataBaseHelper.TABLE_PHOTOS, photoColumns,
                null, null, null, null, null);

        List<PhotoQuack> allPhotos = new ArrayList<PhotoQuack>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            PhotoQuack foto = cursorToPhotoQuack(cursor);
            allPhotos.add(foto);
            cursor.moveToNext();
        }

        return allPhotos;
    }

    //Añade una nueva foto personal a la base de datos
    public long addPhotoQuack(PhotoQuack foto){
        ContentValues vals = new ContentValues();

        vals.put(MyDataBaseHelper.PATO_IMAGE, BdUtils.bitmapToByteArray(foto.getImagen()));


        //Devuelve el id asignado por la base de datos
        return database.insert(MyDataBaseHelper.TABLE_PHOTOS, null, vals);
    }

    //Elimina un pato de la base de datos
    public void deletePhotoQuack(int id){
        database.delete(MyDataBaseHelper.TABLE_PHOTOS,
                MyDataBaseHelper.PHOTO_ID + " = " + id, null);
    }

    private PhotoQuack cursorToPhotoQuack(Cursor cursor){
        PhotoQuack foto = new PhotoQuack();

        foto.setId(cursor.getInt(0));
        foto.setImagen(BdUtils.byteArrayToBitmap(cursor.getBlob(1)));

        return foto;
    }

}
