package es.upm.etsiinf.haveaduck.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class ImageOnlyPato {

    @SerializedName("url")
    private String url;

    private Bitmap imageBtm;

    public Bitmap getImageBtm() {
        return imageBtm;
    }

    public void setImageBtm(Bitmap imageBtm) {
        this.imageBtm = imageBtm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
