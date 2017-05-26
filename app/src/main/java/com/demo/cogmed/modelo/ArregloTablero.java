package com.demo.cogmed.modelo;

import android.graphics.Bitmap;

import com.demo.cogmed.temas.Temas;
import com.demo.cogmed.utilerias.Utilerias;

import java.util.Map;

import static com.demo.cogmed.comun.Compartido.*;

/**
 * Created by guillermo on 24/03/17.
 */

public class ArregloTablero {

    //como {0-2, 4-3, 1-5}
    public Map<Integer, Integer> pares;
    //como {0-monstruos_20, 1-monstruos_12, 2-monstruos_20,....}
    public Map<Integer, String> cuadroUrls;

    public Bitmap getCuadroBitmap(int id, int tamano) {
        String string = cuadroUrls.get(id);
        if (string.contains(Temas.URI_DRAWABLE)) {
            String nombreRecursoDibujable = string.substring(Temas.URI_DRAWABLE.length());
            int idRecursoDibujable = contexto.getResources().getIdentifier(nombreRecursoDibujable, "drawable", contexto.getPackageName());
            Bitmap bitmap = Utilerias.reducirEscala(idRecursoDibujable, tamano, tamano);
            return Utilerias.cortar(bitmap, tamano, tamano);
        }
        return null;
    }

    public boolean esPar(int id1, int id2) {
        Integer integer = pares.get(id1);
        if (integer == null) {
            return false;
        }
        return integer.equals(id2);
    }

}
