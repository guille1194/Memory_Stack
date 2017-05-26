package com.demo.cogmed.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.modelo.EstadoJuego;

/**
 * Created by guillermo on 18/04/17.
 */

public class PopupManager {
    public static void mostrarConfigPopup() {
        RelativeLayout contenedorPopup = (RelativeLayout) Compartido.actividad.findViewById(R.id.popup_container);
        contenedorPopup.removeAllViews();

        //pantalla
        ImageView imageView = new ImageView(Compartido.contexto);
        imageView.setBackgroundColor(Color.parseColor("#88555555"));
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setClickable(true);
        contenedorPopup.addView(imageView);

        //popup
        VistaConfigPopup vistaConfigPopup = new VistaConfigPopup(Compartido.contexto);
        int ancho = Compartido.contexto.getResources().getDimensionPixelSize(R.dimen.popup_settings_width);
        int largo = Compartido.contexto.getResources().getDimensionPixelSize(R.dimen.popup_settings_height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ancho, largo);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        contenedorPopup.addView(vistaConfigPopup, params);

        //animar todos juntos
        ObjectAnimator animadorEscalaX = ObjectAnimator.ofFloat(vistaConfigPopup, "scaleX", 0f, 1f);
        ObjectAnimator animadorEscalaY = ObjectAnimator.ofFloat(vistaConfigPopup, "scaleY", 0f, 1f);
        ObjectAnimator animadorAlpha = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animadorEscalaX, animadorEscalaY);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator(2));
        animatorSet.start();
    }

    public static void mostrarPopupGanador(EstadoJuego estadoJuego) {
        RelativeLayout contenedorPopup = (RelativeLayout) Compartido.actividad.findViewById(R.id.popup_container);
        contenedorPopup.removeAllViews();

        // popup
        VistaPopupGanador vistaPopupGanador = new VistaPopupGanador(Compartido.contexto);
        vistaPopupGanador.setEstadoJuego(estadoJuego);
        int ancho = Compartido.contexto.getResources().getDimensionPixelSize(R.dimen.popup_won_width);
        int largo = Compartido.contexto.getResources().getDimensionPixelSize(R.dimen.popup_won_height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ancho, largo);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        contenedorPopup.addView(vistaPopupGanador, params);

        // animate all together
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(vistaPopupGanador, "scaleX", 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(vistaPopupGanador, "scaleY", 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator(2));
        vistaPopupGanador.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();
    }

    public static void cerrarPopup() {
        final RelativeLayout contenedorPopup = (RelativeLayout) Compartido.actividad.findViewById(R.id.popup_container);
        int contadorHijo = contenedorPopup.getChildCount();
        if (contadorHijo > 0) {
            View fondo = null;
            View vistaPopup = null;
            if (contadorHijo == 1) {
                vistaPopup = contenedorPopup.getChildAt(0);
            } else {
                fondo = contenedorPopup.getChildAt(0);
                vistaPopup = contenedorPopup.getChildAt(1);
            }

            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator animadorEscalaX = ObjectAnimator.ofFloat(vistaPopup, "scaleX", 0f);
            ObjectAnimator animdorEscalaY = ObjectAnimator.ofFloat(vistaPopup, "scaleY", 0f);
            if (contadorHijo > 1) {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(fondo, "alpha", 0f);
                animatorSet.playTogether(animadorEscalaX, animdorEscalaY, alphaAnimator);
            } else {
                animatorSet.playTogether(animadorEscalaX, animdorEscalaY);
            }
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new AccelerateInterpolator(2));
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    contenedorPopup.removeAllViews();
                }
            });
            animatorSet.start();
        }
    }

    public static boolean esMostrado() {
        RelativeLayout contenedorPopup = (RelativeLayout) Compartido.actividad.findViewById(R.id.popup_container);
        return contenedorPopup.getChildCount() > 0;
    }


}
