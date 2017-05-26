package com.demo.cogmed.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.comun.Musica;
import com.demo.cogmed.eventos.ui.EventoJuegoAnterior;
import com.demo.cogmed.eventos.ui.EventoSiguienteJuego;
import com.demo.cogmed.modelo.EstadoJuego;
import com.demo.cogmed.utilerias.CargadorFuentes;
import com.demo.cogmed.utilerias.Reloj;

/**
 * Created by guillermo on 19/04/17.
 */

public class VistaPopupGanador extends RelativeLayout {

    private TextView mTiempo;
    private TextView mPuntaje;
    private ImageView mEstrella1;
    private ImageView mEstrella2;
    private ImageView mEstrella3;
    private ImageView mBotonSiguiente;
    private ImageView mBotonAtras;
    private Handler mHandler;

    public VistaPopupGanador(Context context) { this(context, null); }

    public VistaPopupGanador(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.vista_popup_ganador, this, true);
        mTiempo = (TextView) findViewById(R.id.time_bar_text);
        mPuntaje = (TextView) findViewById(R.id.score_bar_text);
        mEstrella1 = (ImageView) findViewById(R.id.star_1);
        mEstrella2 = (ImageView) findViewById(R.id.star_2);
        mEstrella3 = (ImageView) findViewById(R.id.star_3);
        mBotonAtras = (ImageView) findViewById(R.id.button_back);
        mBotonSiguiente = (ImageView) findViewById(R.id.button_next);
        CargadorFuentes.setTypeface(context, new TextView[] {mTiempo, mPuntaje }, CargadorFuentes.Fuente.GROBOLD);
        setBackgroundResource(R.drawable.level_complete);
        mHandler = new Handler();

        mBotonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compartido.busEvento.notificar(new EventoJuegoAnterior());
            }
        });

        mBotonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compartido.busEvento.notificar(new EventoSiguienteJuego());
            }
        });
    }

    public void setEstadoJuego(final EstadoJuego estadoJuego) {
        int min = estadoJuego.segundosRestantes / 60;
        int seg = estadoJuego.segundosRestantes - min * 60;
        mTiempo.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", seg));
        mPuntaje.setText("" + 0);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tiempoYPuntajeAnimado(estadoJuego.segundosRestantes, estadoJuego.puntajeObtenido);
                estrellasAnimadas(estadoJuego.estrellasObtenidas);
            }
        }, 500);
    }

    private void estrellasAnimadas(int start) {
        switch (start) {
            case 0:
                mEstrella1.setVisibility(View.GONE);
                mEstrella2.setVisibility(View.GONE);
                mEstrella3.setVisibility(View.GONE);
                break;
            case 1:
                mEstrella2.setVisibility(View.GONE);
                mEstrella3.setVisibility(View.GONE);
                mEstrella1.setAlpha(0f);
                estrellaAnimada(mEstrella1, 0);
                break;
            case 2:
                mEstrella3.setVisibility(View.GONE);
                mEstrella1.setVisibility(View.VISIBLE);
                mEstrella1.setAlpha(0f);
                estrellaAnimada(mEstrella1, 0);
                mEstrella2.setVisibility(View.VISIBLE);
                mEstrella2.setAlpha(0f);
                estrellaAnimada(mEstrella2, 600);
                break;
            case 3:
                mEstrella1.setVisibility(View.VISIBLE);
                mEstrella1.setAlpha(0f);
                estrellaAnimada(mEstrella1, 0);
                mEstrella2.setVisibility(View.VISIBLE);
                mEstrella2.setAlpha(0f);
                estrellaAnimada(mEstrella2, 600);
                mEstrella3.setVisibility(View.VISIBLE);
                mEstrella3.setAlpha(0f);
                estrellaAnimada(mEstrella3, 1200);
                break;
            default:
                break;
        }
    }

    private void estrellaAnimada(final View view, int retraso) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
        alpha.setDuration(100);
        ObjectAnimator escalaX = ObjectAnimator.ofFloat(view, "scaleX", 0, 1f);
        ObjectAnimator escalaY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, escalaX, escalaY);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.setStartDelay(retraso);
        animatorSet.setDuration(600);
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Musica.mostrarEstrella();
            }
        }, retraso);
    }

    private void tiempoYPuntajeAnimado(final int segundosRestantes, final int puntajeObtenido) {
        final int animacionTotal = 1200;
        Reloj.getInstancia().inicioTimer(animacionTotal, 35, new Reloj.ContadorTiempo() {

            @Override
            public void enSenal(long milisHastaTerminar) {
                float factor = milisHastaTerminar / (animacionTotal * 1f);
                int puntajeAMostrar = puntajeObtenido - (int) (puntajeObtenido * factor);
                int tiempoAMostrar = (int) (segundosRestantes * factor);
                int min = tiempoAMostrar / 60;
                int seg = tiempoAMostrar - min * 60;
                mTiempo.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", seg));
                mPuntaje.setText("" + puntajeAMostrar);
            }

            @Override
            public void enFinal() {
                mTiempo.setText(" " + String.format("%02d", 0) + ":" + String.format("%02d", 0));
                mPuntaje.setText("" + puntajeObtenido);
            }
        });
    }
}
