package com.demo.cogmed.fragmentos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.comun.Musica;
import com.demo.cogmed.eventos.ui.EventoInicio;
import com.demo.cogmed.ui.PopupManager;
import com.demo.cogmed.utilerias.Utilerias;

/**
 * Created by guillermo on 18/04/17.
 */

public class FragmentoMenu extends Fragment {
    private ImageView mTitulo;
    private ImageView mBotonJuegoInicio;
    private ImageView mLucesBotonInicio;
    private ImageView mTooltip;
    private ImageView mBotonJuegoConfiguracion;
    private ImageView mBotonJuegoGooglePlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_menu, container, false);
        mTitulo = (ImageView) view.findViewById(R.id.title);
        mBotonJuegoInicio = (ImageView) view.findViewById(R.id.start_game_button);
        mBotonJuegoConfiguracion = (ImageView) view.findViewById(R.id.settings_game_button);
        mBotonJuegoConfiguracion.setSoundEffectsEnabled(false);
        mBotonJuegoConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupManager.mostrarConfigPopup();
            }
        });
        mBotonJuegoGooglePlay = (ImageView) view.findViewById(R.id.google_play_button);
        mBotonJuegoGooglePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "La tabla de lideres estaran disponibles en la siguiente actualizacion del juego", Toast.LENGTH_LONG).show();
            }
        });
        mLucesBotonInicio = (ImageView) view.findViewById(R.id.start_game_button_lights);
        mTooltip = (ImageView) view.findViewById(R.id.tooltip);
        mBotonJuegoInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animarTodosAssetsDesactivados(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Compartido.busEvento.notificar(new EventoInicio());
                    }
                });
            }
        });

        iniciarAnimacionLuces();
        iniciarAnimacionTooltip();

        Musica.reproducirMusicaFondo();
        return view;
    }

    protected void animarTodosAssetsDesactivados(AnimatorListenerAdapter adapter) {
        //titulo
        //120dp + 50dp + buffer(30dp)
        ObjectAnimator animadorTitulo = ObjectAnimator.ofFloat(mTitulo, "translationY", Utilerias.px(-200));
        animadorTitulo.setInterpolator(new AccelerateInterpolator(2));
        animadorTitulo.setDuration(300);

        //luces
        ObjectAnimator animadorLucesX = ObjectAnimator.ofFloat(mLucesBotonInicio, "scaleX", 0f);
        ObjectAnimator animadorLucesY = ObjectAnimator.ofFloat(mLucesBotonInicio, "scaleY", 0f);

        //tooltip
        ObjectAnimator animadorTooltip = ObjectAnimator.ofFloat(mTooltip, "alpha", 0f);
        animadorTooltip.setDuration(100);

        //boton configuracion
        ObjectAnimator animadorConfiguracion = ObjectAnimator.ofFloat(mBotonJuegoConfiguracion, "translationY", Utilerias.px(120));
        animadorConfiguracion.setInterpolator(new AccelerateInterpolator(2));
        animadorConfiguracion.setDuration(300);

        //boton google play
        ObjectAnimator animadorGooglePlay = ObjectAnimator.ofFloat(mBotonJuegoGooglePlay, "translationY", Utilerias.px(120));
        animadorGooglePlay.setInterpolator(new AccelerateInterpolator(2));
        animadorGooglePlay.setDuration(300);

        //boton inicio
        ObjectAnimator animadorBotonInicio = ObjectAnimator.ofFloat(mBotonJuegoInicio, "translationY", Utilerias.px(130));
        animadorBotonInicio.setInterpolator(new AccelerateInterpolator(2));
        animadorBotonInicio.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animadorTitulo, animadorLucesX, animadorLucesY, animadorTooltip, animadorConfiguracion, animadorGooglePlay, animadorBotonInicio);
        animatorSet.addListener(adapter);
        animatorSet.start();
    }

    private void iniciarAnimacionTooltip() {
        ObjectAnimator escalaY = ObjectAnimator.ofFloat(mTooltip, "scaleY", 0.8f);
        escalaY.setDuration(200);
        ObjectAnimator escalaYAtras = ObjectAnimator.ofFloat(mTooltip, "scaleY", 1f);
        escalaYAtras.setDuration(500);
        escalaYAtras.setInterpolator(new BounceInterpolator());
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(1000);
        animatorSet.playSequentially(escalaY, escalaYAtras);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.setStartDelay(2000);
                animatorSet.start();
            }
        });
        mTooltip.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();
    }

    private void iniciarAnimacionLuces() {
        ObjectAnimator animador = ObjectAnimator.ofFloat(mLucesBotonInicio, "rotation", 0f, 360f);
        animador.setInterpolator(new AccelerateDecelerateInterpolator());
        animador.setDuration(6000);
        animador.setRepeatCount(ValueAnimator.INFINITE);
        mLucesBotonInicio.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animador.start();
    }

}
