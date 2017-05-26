package com.demo.cogmed.motor;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Memoria;
import com.demo.cogmed.comun.Musica;
import com.demo.cogmed.eventos.AdaptadorObservadorEventos;
import com.demo.cogmed.eventos.motor.EventoJuegoGanado;
import com.demo.cogmed.eventos.motor.EventoOcultarCartasPar;
import com.demo.cogmed.eventos.motor.EventoVoltearAbajoCarta;
import com.demo.cogmed.eventos.ui.EventoDificultadSeleccionada;
import com.demo.cogmed.eventos.ui.EventoInicio;
import com.demo.cogmed.eventos.ui.EventoJuegoAnterior;
import com.demo.cogmed.eventos.ui.EventoReiniciarFondo;
import com.demo.cogmed.eventos.ui.EventoSiguienteJuego;
import com.demo.cogmed.eventos.ui.EventoTemaSeleccionado;
import com.demo.cogmed.eventos.ui.EventoVoltearCarta;
import com.demo.cogmed.modelo.ArregloTablero;
import com.demo.cogmed.modelo.ConfiguracionTablero;
import com.demo.cogmed.modelo.EstadoJuego;
import com.demo.cogmed.modelo.Juego;
import com.demo.cogmed.temas.Tema;
import com.demo.cogmed.temas.Temas;
import com.demo.cogmed.ui.PopupManager;
import com.demo.cogmed.utilerias.Reloj;
import com.demo.cogmed.utilerias.Utilerias;
import com.demo.cogmed.motor.ControladorPantalla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.demo.cogmed.comun.Compartido.*;
import static com.demo.cogmed.motor.ControladorPantalla.*;
import static com.demo.cogmed.utilerias.Utilerias.*;

/**
 * Created by guillermo on 22/03/17.
 */

public class Motor extends AdaptadorObservadorEventos {

    private static Motor mInstancia = null;
    private Juego mJugandoJuego = null;
    private int mIdVolteado = -1;
    private int mAVoltear = -1;
    private ControladorPantalla mControlladorPantalla;
    private Tema mTemaSeleccionado;
    private ImageView mImagenFondo;
    private Handler mHandler;

    private Motor() {
        mControlladorPantalla = ControladorPantalla.getInstancia();
        mHandler = new Handler();
    }

    public static Motor getInstancia() {
        if (mInstancia == null) {
            mInstancia = new Motor();
        }
        return mInstancia;
    }

    public void start() {
        busEvento.escuchar(EventoDificultadSeleccionada.TIPO, this);
        busEvento.escuchar(EventoVoltearCarta.TIPO, this);
        busEvento.escuchar(EventoInicio.TIPO, this);
        busEvento.escuchar(EventoTemaSeleccionado.TIPO, this);
        busEvento.escuchar(EventoJuegoAnterior.TIPO, this);
        busEvento.escuchar(EventoSiguienteJuego.TIPO, this);
        busEvento.escuchar(EventoReiniciarFondo.TIPO, this);
    }

    public void stop() {
        mJugandoJuego = null;
        mImagenFondo.setImageDrawable(null);
        mImagenFondo = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;

        busEvento.noescuchar(EventoDificultadSeleccionada.TIPO, this);
        busEvento.noescuchar(EventoVoltearCarta.TIPO, this);
        busEvento.noescuchar(EventoInicio.TIPO, this);
        busEvento.noescuchar(EventoTemaSeleccionado.TIPO, this);
        busEvento.noescuchar(EventoJuegoAnterior.TIPO, this);
        busEvento.noescuchar(EventoSiguienteJuego.TIPO, this);
        busEvento.noescuchar(EventoReiniciarFondo.TIPO, this);

        mInstancia = null;
    }

    @Override
    public void enEvento(EventoReiniciarFondo evento) {
        Drawable drawable = mImagenFondo.getDrawable();
        if (drawable != null) {
            ((TransitionDrawable) drawable).reverseTransition(2000);
        } else {
            new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    Bitmap bitmap = reducirEscala(R.drawable.background, anchoPantalla(), largoPantalla());
                    return bitmap;
                }

                protected void onPostExecute(Bitmap bitmap) {
                    mImagenFondo.setImageBitmap(bitmap);
                }

            }.execute();
        }
    }

    @Override
    public void enEvento(EventoInicio evento) {
        mControlladorPantalla.pantallaAbierta(Pantalla.TEMA_SELECCIONADO);
    }

    @Override
    public void enEvento(EventoSiguienteJuego evento) {
        PopupManager.cerrarPopup();
        int dificultad = mJugandoJuego.configuracionTablero.dificultad;
        if (mJugandoJuego.estadoJuego.estrellasObtenidas == 3 && dificultad < 6) {
            dificultad++;
        }
        busEvento.notificar(new EventoDificultadSeleccionada(dificultad));
    }

    @Override
    public void enEvento(EventoJuegoAnterior evento) {
        PopupManager.cerrarPopup();
        mControlladorPantalla.pantallaAbierta(Pantalla.DIFICULTAD);
    }

    @Override
    public void enEvento(EventoTemaSeleccionado evento) {
        mTemaSeleccionado = evento.tema;
        mControlladorPantalla.pantallaAbierta(Pantalla.DIFICULTAD);
        AsyncTask<Void, Void, TransitionDrawable> task = new AsyncTask<Void, Void, TransitionDrawable>() {

            @Override
            protected TransitionDrawable doInBackground(Void... params) {
                Bitmap bitmap = reducirEscala(R.drawable.background, anchoPantalla(), largoPantalla());
                Bitmap imagenFondo = Temas.getFondoImagen(mTemaSeleccionado);
                imagenFondo = cortar(imagenFondo, largoPantalla(), anchoPantalla());
                Drawable fondos[] = new Drawable[2];
                fondos[0] = new BitmapDrawable(contexto.getResources(), bitmap);
                fondos[1] = new BitmapDrawable(contexto.getResources(), imagenFondo);
                TransitionDrawable crossfader = new TransitionDrawable(fondos);
                return crossfader;
            }

            @Override
            protected void onPostExecute(TransitionDrawable resultado) {
                super.onPostExecute(resultado);
                mImagenFondo.setImageDrawable(resultado);
                resultado.startTransition(2000);
            }
        };
        task.execute();
    }

    @Override
    public void enEvento(EventoDificultadSeleccionada evento) {
        mIdVolteado = -1;
        mJugandoJuego = new Juego();
        mJugandoJuego.configuracionTablero = new ConfiguracionTablero(evento.dificultad);
        mJugandoJuego.tema = mTemaSeleccionado;
        mAVoltear = mJugandoJuego.configuracionTablero.numCuadros;

        //arreglar tablero
        arreglarTablero();

        //iniciar pantalla
        mControlladorPantalla.pantallaAbierta(Pantalla.JUEGO);
    }

    private void arreglarTablero() {
        ConfiguracionTablero configuracionTablero = mJugandoJuego.configuracionTablero;
        ArregloTablero arregloTablero = new ArregloTablero();

        //construir pares
        //resultado {0,1,2,...n} // n- numero de cuadros
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < configuracionTablero.numCuadros; i++) {
            ids.add(i);
        }
        //barajar
        //resultado {4, 10, 2, 39,...}
        Collections.shuffle(ids);

        //colocar el tablero
        List<String> urlsImagenCuadro = mJugandoJuego.tema.urlImagenCuadros;
        Collections.shuffle(urlsImagenCuadro);
        arregloTablero.pares = new HashMap<Integer, Integer>();
        arregloTablero.cuadroUrls = new HashMap<Integer, String>();
        int j = 0;
        for (int i = 0; i < ids.size(); i++) {
            if (i + 1 < ids.size()) {
                //{4,10}, {2,39},...
                arregloTablero.pares.put(ids.get(i), ids.get(i + 1));
                //{10,4}, {39,2},...
                arregloTablero.pares.put(ids.get(i + 1), ids.get(i));
                //{4,
                arregloTablero.cuadroUrls.put(ids.get(i), urlsImagenCuadro.get(j));
                arregloTablero.cuadroUrls.put(ids.get(i + 1), urlsImagenCuadro.get(j));
                i++;
                j++;
            }
        }

        mJugandoJuego.configuracionTablero = configuracionTablero;
    }

    @Override
    public void enEvento(EventoVoltearCarta evento) {
        // Log.i("my_tag", "Flip: " + evento.id);
        int id = evento.id;
        if (mIdVolteado == -1) {
            mIdVolteado = id;
            // Log.i("my_tag", "Flip: mFlippedId: " + evento.id);
        } else {
            if (mJugandoJuego.arregloTablero.esPar(mIdVolteado, id)) {
                // Log.i("my_tag", "Flip: is pair: " + mFlippedId + ", " + id);
                // send event - hide id1, id2
                busEvento.notificar(new EventoOcultarCartasPar(mIdVolteado, id), 1000);
                //jugar musica
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Musica.reproducirActual();
                    }
                }, 1000);
                mAVoltear -= 2;
                if (mAVoltear == 0) {
                    int segundosPasados = (int) (Reloj.getInstancia().getTiempoPasado() / 1000);
                    Reloj.getInstancia().pausa();
                    int tiempoTotal = mJugandoJuego.configuracionTablero.tiempo;
                    EstadoJuego estadoJuego = new EstadoJuego();
                    mJugandoJuego.estadoJuego = estadoJuego;
                    //segundos restantes
                    estadoJuego.segundosRestantes = tiempoTotal - segundosPasados;

                    //calculando estrellas
                    if (segundosPasados <= tiempoTotal / 2) {
                        estadoJuego.estrellasObtenidas = 3;
                    } else if (segundosPasados <= tiempoTotal - tiempoTotal / 5) {
                        estadoJuego.estrellasObtenidas = 2;
                    } else if (segundosPasados < tiempoTotal) {
                        estadoJuego.estrellasObtenidas = 1;
                    } else {
                        estadoJuego.estrellasObtenidas = 0;
                    }

                    //calcular puntaje
                    estadoJuego.estrellasObtenidas = mJugandoJuego.configuracionTablero.dificultad * estadoJuego.segundosRestantes * mJugandoJuego.tema.id;

                    //guardar a memoria
                    Memoria.guardar(mJugandoJuego.tema.id, mJugandoJuego.configuracionTablero.dificultad, estadoJuego.estrellasObtenidas);

                    busEvento.notificar(new EventoJuegoGanado(estadoJuego), 1200);
                }
            } else {
                // Log.i("my_tag", "Flip: all down");
                // send event - flip all down
                busEvento.notificar(new EventoVoltearAbajoCarta(), 1000);
            }
            mIdVolteado = -1;
            // Log.i("my_tag", "Flip: mFlippedId: " + mFlippedId);
        }
    }

    public Juego getJuegoActivo() {
        return mJugandoJuego;
    }

    public Tema getmTemaSeleccionado() {
        return mTemaSeleccionado;
    }

    public void setVistaImagenFondo(ImageView imagenFondo) {
        mImagenFondo = imagenFondo;
    }
}

