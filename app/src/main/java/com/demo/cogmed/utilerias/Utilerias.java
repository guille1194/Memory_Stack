package com.demo.cogmed.utilerias;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.ThumbnailUtils;

import com.demo.cogmed.comun.Compartido;

/**
 * Created by guillermo on 22/03/17.
 */

public class Utilerias {
    public static int px(int dp) {
        return (int) (Compartido.contexto.getResources().getDisplayMetrics().density * dp);
    }

    public static int anchoPantalla() {
        return Compartido.contexto.getResources().getDisplayMetrics().widthPixels;
    }

    public static int largoPantalla() {
        return Compartido.contexto.getResources().getDisplayMetrics().heightPixels;
    }

    public static Bitmap cortar(Bitmap fuente, int nuevoLargo, int nuevoAncho) {
        int fuenteAncho = fuente.getWidth();
        int fuenteLargo = fuente.getHeight();

        float xEscala = (float) nuevoAncho / fuenteAncho;
        float yEscala = (float) nuevoLargo / fuenteLargo;
        float escala = Math.max(xEscala, yEscala);

        float escaladoAncho = escala * fuenteAncho;
        float escaladoLargo = escala * fuenteLargo;

        float izquierda = (nuevoAncho - escaladoAncho) / 2;
        float arriba = (nuevoLargo - escaladoLargo) / 2;

        RectF objetivoRect = new RectF(izquierda, arriba, izquierda + escaladoAncho, arriba + escaladoLargo);

        Bitmap dest = Bitmap.createBitmap(nuevoAncho, nuevoLargo, fuente.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(fuente, null, objetivoRect, null);

        return dest;
    }

    public static Bitmap reducirEscala(int recurso, int anchoReq, int largoReq) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(Compartido.contexto.getResources(), recurso);

        //Calcular en un tamanio ejemplo
        options.inSampleSize = calcularEnTamanoMuestra(options, anchoReq, largoReq);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(Compartido.contexto.getResources(), recurso, options);
    }

    public static Bitmap reducirEscalaBitmap(Bitmap wallpaper, int factor) {

        int anchoPixeles = wallpaper.getWidth() / factor;
        int largoPixeles = wallpaper.getHeight() / factor;
        return ThumbnailUtils.extractThumbnail(wallpaper, anchoPixeles, largoPixeles);
    }

    public static int calcularEnTamanoMuestra(BitmapFactory.Options options, int anchoReq, int largoReq) {

        final int largo = options.outHeight;
        final int ancho = options.outWidth;
        int enTamanoMuestra = 1;

        if (largo > largoReq || ancho > anchoReq) {
            final int radioLargo = Math.round((float) largo / (float) largoReq);
            final int radioAncho = Math.round((float) ancho / (float) anchoReq);

            enTamanoMuestra = radioLargo < radioAncho ? radioLargo : radioAncho;
        }

        return enTamanoMuestra;

    }
}
