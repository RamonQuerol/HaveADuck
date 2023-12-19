package es.upm.etsiinf.haveaduck.otheractivities.addfromapi;

import android.graphics.Bitmap;

import es.upm.etsiinf.haveaduck.utils.NetUtils;

public class DownloadThatPatoThread implements Runnable{

    AddDataFromApiActivity activity;

    String url;


    public DownloadThatPatoThread(AddDataFromApiActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    @Override
    public void run(){

        try {

            Bitmap imagenPato = NetUtils.getURLImage(url);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.setImage(imagenPato);
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
