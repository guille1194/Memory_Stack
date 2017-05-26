package com.demo.cogmed.comun;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.demo.cogmed.R;

import static com.demo.cogmed.comun.Compartido.*;

/**
 * Created by guillermo on 19/03/17.
 */

public class Musica {

    public static boolean APAGADO = false;

    public static void reproducirActual() {
        if(!APAGADO) {
            MediaPlayer mp = MediaPlayer.create(contexto, R.raw.respuesta_correcta);
            mp.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }
            });
            mp.start();
            }
        }

        public static void reproducirMusicaFondo() {
            //Pendiente
        }

        public static void mostrarEstrella() {
            if (!APAGADO) {
                MediaPlayer mp = MediaPlayer.create(contexto, R.raw.estrella);
                mp.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                        mp = null;
                    }
                });

                mp.start();
            }
        }
}
