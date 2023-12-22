package es.upm.etsiinf.haveaduck.model;

import android.graphics.Bitmap;

//Esta clase representa fotos que el usuario sube
public class PhotoQuack {

    int id;
    Bitmap imagen;

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

}
