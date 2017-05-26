package com.demo.cogmed.fragmentos;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.comun.Memoria;
import com.demo.cogmed.eventos.ui.EventoTemaSeleccionado;
import com.demo.cogmed.temas.Tema;
import com.demo.cogmed.temas.Temas;

import java.util.Locale;

/**
 * Created by guillermo on 18/04/17.
 */

public class FragmentoSeleccTema extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(Compartido.contexto).inflate(R.layout.fragmento_selecc_tema, container, false);
        View animales = view.findViewById(R.id.theme_animals_container);
        View monstruos = view.findViewById(R.id.theme_monsters_container);

        final Tema temaAnimales = Temas.crearTemaAnimales();
        setEstrellas((ImageView) animales.findViewById(R.id.theme_animals), temaAnimales, "animals");
        final Tema temaMonstruos = Temas.crearTemaMonstruos();
        setEstrellas((ImageView) monstruos.findViewById(R.id.theme_monsters), temaMonstruos, "monsters");

        animales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compartido.busEvento.notificar(new EventoTemaSeleccionado(temaAnimales));
            }
        });

        monstruos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compartido.busEvento.notificar(new EventoTemaSeleccionado(temaMonstruos));
            }
        });

        //mejorar rendimiento
        mostrarAnimar(animales);
        mostrarAnimar(monstruos);

        return view;
    }

        private void mostrarAnimar(View view) {
            ObjectAnimator animadorEscalaX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
            ObjectAnimator animadorEscalaY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(300);
            animatorSet.playTogether(animadorEscalaX, animadorEscalaY);
            animatorSet.setInterpolator(new DecelerateInterpolator(2));
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            animatorSet.start();
        }

        private void setEstrellas(ImageView imageView, Tema tema, String tipo) {
            int suma = 0;
            for (int dificultad = 1; dificultad <= 6; dificultad++) {
                suma += Memoria.getEstrellasAlta(tema.id, dificultad);
            }
            int num = suma / 6;
            if (num != 0) {
                String nombreRecursoDibujable = String.format(Locale.US, tipo + "_theme_star_%d", num);
                int idRecursoManejable = Compartido.contexto.getResources().getIdentifier(nombreRecursoDibujable, "drawable", Compartido.contexto.getPackageName());
                imageView.setImageResource(idRecursoManejable);
            }
        }
    }
