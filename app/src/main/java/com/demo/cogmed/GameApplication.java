package com.demo.cogmed;

import android.app.Application;

import com.demo.cogmed.utilerias.CargadorFuentes;

/**
 * Created by guillermo on 19/03/17.
 */

public class GameApplication extends Application {

    public void onCreate() {
        super.onCreate();
        CargadorFuentes.cargarFuentes(this);
    }
}
