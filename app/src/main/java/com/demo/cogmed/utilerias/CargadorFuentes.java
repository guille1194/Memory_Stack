package com.demo.cogmed.utilerias;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.widget.TextView;

/**
 * Created by guillermo on 24/03/17.
 */

public class CargadorFuentes {
    public static final int GROBOLD = 0;

    private static SparseArray<Typeface> fuentes = new SparseArray<Typeface>();
    private static boolean fuentesCargadas = false;

    public static enum Fuente {
        GROBOLD(CargadorFuentes.GROBOLD, "fonts/grobold.ttf");

        private int val;
        private String path;

        private Fuente(int val, String path) {
            this.val = val;
            this.path = path;
        }

        public static String getPorVal (int val) {
            for (Fuente fuente: values()) {
                if(fuente.val == val) {
                    return fuente.path;
                }
            }
            return null;
        }
    }

    public static void cargarFuentes(Context context) {
        for (int i = 0; i < Fuente.values().length; i++) {
            fuentes.put(i, Typeface.createFromAsset(context.getAssets(), Fuente.getPorVal(i)));
        }
        fuentesCargadas = true;
    }

    public static Typeface getTypeface(Context context, Fuente fuente) {
        if (!fuentesCargadas) {
            cargarFuentes(context);
        }
        return fuentes.get(fuente.val);
    }

    public static void setTypeface(Context context, TextView[] textViews, Fuente fuente) {
       setTypefaceToTextViews(context, textViews, fuente, Typeface.BOLD);
    }

    public static void setTypefaceToTextViews(Context context, TextView[] textViews, Fuente fuente, int estiloFuente) {
        if(!fuentesCargadas) {
            cargarFuentes(context);
        }
        Typeface fuenteActual = fuentes.get(fuente.val);

        for (int i = 0; i < textViews.length; i++) {
            if (textViews[i] != null)
                textViews[i].setTypeface(fuenteActual, estiloFuente);
        }
    }

}
