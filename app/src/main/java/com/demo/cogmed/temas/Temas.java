package com.demo.cogmed.temas;

import android.graphics.Bitmap;

import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.utilerias.Utilerias;

import java.util.ArrayList;

/**
 * Created by guillermo on 22/03/17.
 */

public class Temas {

    public static String URI_DRAWABLE = "drawable://";

    public static Tema crearTemaAnimales() {
        Tema tema = new Tema();
        tema.id = 1;
        tema.nombre = "Animals";
        tema.urlImagenFondo = URI_DRAWABLE + "back_animals";
        tema.urlImagenCuadros = new ArrayList<String>();
        //40 extraibles
        for (int i = 1; i <= 28; i++) {
            tema.urlImagenCuadros.add(URI_DRAWABLE + String.format("animals_%d", i));
        }
        return tema;
    }

    public static Tema crearTemaMonstruos() {
        Tema tema = new Tema();
        tema.id = 2;
        tema.nombre = "Monsters";
        tema.urlImagenFondo = URI_DRAWABLE + "back_horror";
        tema.urlImagenCuadros = new ArrayList<String>();
        //40 extraibles
        for (int i = 1; i <= 40; i++) {
            tema.urlImagenCuadros.add(URI_DRAWABLE + String.format("monsters_%d", i));
        }
        return tema;
    }

    public static Bitmap getFondoImagen(Tema tema) {
        String nombreRecursoDibujable = tema.urlImagenFondo.substring(Temas.URI_DRAWABLE.length());
        int idRecursoDibujable = Compartido.contexto.getResources().getIdentifier(nombreRecursoDibujable, "drawable", Compartido.contexto.getPackageName());
        Bitmap bitmap = Utilerias.reducirEscala(idRecursoDibujable, Utilerias.anchoPantalla(), Utilerias.largoPantalla());
        return bitmap;
    }
}
