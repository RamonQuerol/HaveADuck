package es.upm.etsiinf.haveaduck;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedList;
import java.util.List;

import es.upm.etsiinf.haveaduck.model.ImageOnlyPato;
import es.upm.etsiinf.haveaduck.ui.home.HomeFragment;
import es.upm.etsiinf.haveaduck.utils.NetUtils;

public class DownloadPatosThread implements Runnable{
    private static final String TAG = DownloadPatosThread.class.getName();

    private HomeFragment homeFrag;
    private MainActivity ma;

    public DownloadPatosThread(MainActivity ma, HomeFragment fragment){
        this.homeFrag = fragment;
        this.ma = ma;
    }

    @Override
    public void run(){
        //Preparamos el interfaz
        ma.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeFrag.startDownload();
            }
        });

        //Realizamos tareas
        List<ImageOnlyPato> pls = new LinkedList<ImageOnlyPato>();
        try {
            String baseURL = "https://random-d.uk/api";
            //String jsonPlanetList= NetUtils.getURLText(baseURL + "/random");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
            Gson gson = gsonBuilder.create();



            for (int i = 0; i<12; i++){

                String jsonPlanetList= NetUtils.getURLText(baseURL + "/random");
                ImageOnlyPato pato = gson.fromJson(jsonPlanetList, ImageOnlyPato.class);
                Bitmap imagenPato = NetUtils.getURLImage(pato.getUrl());
                pato.setImageBtm(imagenPato);
                pls.add(pato);
            }

            //Log.i(TAG, jsonPlanetList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final List<ImageOnlyPato> res = pls;
        //Devolvemos esultados a la interfaz
        ma.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeFrag.finishDownload(res);
            }
        });
    }
}
