package es.upm.etsiinf.haveaduck.model;

import android.graphics.Bitmap;

public class CompletePato {

    private int id;
    private Bitmap imagen;
    private String name;
    private String descripcion;

    //Booleano guardado como int para poder guardarlo correctamente en la base de datos
    //Indica si el pato es de los favoritos o no
    private int favorite;

    public CompletePato(){
        favorite = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

}
