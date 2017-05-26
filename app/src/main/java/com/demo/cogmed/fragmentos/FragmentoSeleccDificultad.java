package com.demo.cogmed.fragmentos;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.comun.Memoria;
import com.demo.cogmed.eventos.ui.EventoDificultadSeleccionada;
import com.demo.cogmed.temas.Tema;
import com.demo.cogmed.ui.VistaDificultad;

import static com.demo.cogmed.comun.Compartido.*;

/**
 * Created by guillermo on 18/04/17.
 */

public class FragmentoSeleccDificultad extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.fragmento_selecc_dificultad, container, false);
        Tema tema = motor.getmTemaSeleccionado();

        VistaDificultad dificultad1 = (VistaDificultad) view.findViewById(R.id.select_difficulty_1);
        dificultad1.setDificultad(1, Memoria.getEstrellasAlta(tema.id, 1));
        setOnClick(dificultad1, 1);

        VistaDificultad dificultad2 = (VistaDificultad) view.findViewById(R.id.select_difficulty_2);
        dificultad1.setDificultad(2, Memoria.getEstrellasAlta(tema.id, 2));
        setOnClick(dificultad1, 2);

        VistaDificultad dificultad3 = (VistaDificultad) view.findViewById(R.id.select_difficulty_3);
        dificultad1.setDificultad(3, Memoria.getEstrellasAlta(tema.id, 3));
        setOnClick(dificultad1, 3);

        VistaDificultad dificultad4 = (VistaDificultad) view.findViewById(R.id.select_difficulty_4);
        dificultad1.setDificultad(4, Memoria.getEstrellasAlta(tema.id, 4));
        setOnClick(dificultad1, 4);

        VistaDificultad dificultad5 = (VistaDificultad) view.findViewById(R.id.select_difficulty_5);
        dificultad1.setDificultad(5, Memoria.getEstrellasAlta(tema.id, 5));
        setOnClick(dificultad1, 5);

        VistaDificultad dificultad6 = (VistaDificultad) view.findViewById(R.id.select_difficulty_6);
        dificultad1.setDificultad(6, Memoria.getEstrellasAlta(tema.id, 6));
        setOnClick(dificultad1, 6);

        animar(dificultad1, dificultad2, dificultad3, dificultad4, dificultad5, dificultad6);

        return view;
    }

    private void animar(View... view) {
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = animatorSet.play(new AnimatorSet());
        for (int i = 0; i < view.length; i++) {
            ObjectAnimator escalaX = ObjectAnimator.ofFloat(view[i], "scaleX", 0.8f, 1f);
            ObjectAnimator escalaY = ObjectAnimator.ofFloat(view[i], "scaleY", 0.8f, 1f);
            builder.with(escalaX).with(escalaY);
        }
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }

    private void setOnClick(View view, final int dificultad) {
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                busEvento.notificar(new EventoDificultadSeleccionada(dificultad));
            }
        });
    }
}
