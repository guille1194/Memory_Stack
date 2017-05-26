package com.demo.cogmed.motor;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.eventos.ui.EventoReiniciarFondo;
import com.demo.cogmed.fragmentos.FragmentoJuego;
import com.demo.cogmed.fragmentos.FragmentoMenu;
import com.demo.cogmed.fragmentos.FragmentoSeleccDificultad;
import com.demo.cogmed.fragmentos.FragmentoSeleccTema;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.demo.cogmed.comun.Compartido.*;

/**
 * Created by guillermo on 18/04/17.
 */

public class ControladorPantalla {
    private static ControladorPantalla mInstancia = null;
    private static List<Pantalla> pantallasAbiertas = new ArrayList<Pantalla>();
    private FragmentManager mFragmentManager;

    private ControladorPantalla() {
    }

    public static ControladorPantalla getInstancia() {
        if (mInstancia == null) {
            mInstancia = new ControladorPantalla();
        }
        return mInstancia;
    }

    public static enum Pantalla {
        MENU,
        JUEGO,
        DIFICULTAD,
        TEMA_SELECCIONADO
    }

    public static Pantalla getUltimaPantalla() { return pantallasAbiertas.get(pantallasAbiertas.size() - 1); }

    public void pantallaAbierta(Pantalla pantalla) {
        mFragmentManager = actividad.getSupportFragmentManager();

        if(pantalla == Pantalla.JUEGO && pantallasAbiertas.get(pantallasAbiertas.size() - 1) == Pantalla.JUEGO) {
            pantallasAbiertas.remove(pantallasAbiertas.size() - 1);
        }
        else if (pantalla == Pantalla.DIFICULTAD && pantallasAbiertas.get(pantallasAbiertas.size() -1) == Pantalla.JUEGO) {
            pantallasAbiertas.remove(pantallasAbiertas.size() -1);
            pantallasAbiertas.remove(pantallasAbiertas.size() -1);
        }
        Fragment fragment = getFragmento(pantalla);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        pantallasAbiertas.add(pantalla);
    }

    public boolean onBack() {
        if(pantallasAbiertas.size() > 0) {
            Pantalla pantallaAEliminar = pantallasAbiertas.get(pantallasAbiertas.size() - 1);
            pantallasAbiertas.remove(pantallasAbiertas.size() -1);
            if(pantallasAbiertas.size() == 0) {
                return true;
            }
            Pantalla pantalla = pantallasAbiertas.get(pantallasAbiertas.size() -1);
            pantallasAbiertas.remove(pantallasAbiertas.size() -1);
            pantallaAbierta(pantalla);
            if ((pantalla == Pantalla.TEMA_SELECCIONADO || pantalla == Pantalla.JUEGO) &&
                (pantallaAEliminar == Pantalla.DIFICULTAD || pantallaAEliminar == Pantalla.JUEGO)) {
                busEvento.notificar(new EventoReiniciarFondo());
            }
            return false;
        }
        return true;
    }

    private Fragment getFragmento(Pantalla pantalla) {
        switch (pantalla) {
            case MENU:
                return new FragmentoMenu();
            case DIFICULTAD:
                return new FragmentoSeleccDificultad();
            case JUEGO:
                return new FragmentoJuego();
            case TEMA_SELECCIONADO:
                return new FragmentoSeleccTema();
            default:
                break;
        }
        return null;
    }
}
