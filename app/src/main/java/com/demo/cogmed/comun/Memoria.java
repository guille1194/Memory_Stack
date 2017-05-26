package com.demo.cogmed.comun;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static com.demo.cogmed.comun.Compartido.*;

/**
 * Created by guillermo on 19/03/17.
 */

public class Memoria {

    private static final String NOMBRE_PREFRENCIAS_COMPARTIDAS = "com.demo.cogmed";
    private static String teclaInicioAlto = "theme_%d_difficulty_%d";

    public static void guardar(int tema, int dificultad, int estrellas) {
        int estrellasAlta = getEstrellasAlta(tema, dificultad);
        if(estrellas > estrellasAlta) {
            SharedPreferences sharedPreferences = contexto.getSharedPreferences(NOMBRE_PREFRENCIAS_COMPARTIDAS, Context.MODE_PRIVATE);
            Editor editar = sharedPreferences.edit();
            String llave = String.format(teclaInicioAlto, tema, dificultad);
            editar.putInt(llave, estrellas).commit();
        }
    }

    public static int getEstrellasAlta(int tema, int dificultad) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(NOMBRE_PREFRENCIAS_COMPARTIDAS, Context.MODE_PRIVATE);
        String llave = String.format(teclaInicioAlto, tema, dificultad);
        return sharedPreferences.getInt(llave, 0);
    }
}
