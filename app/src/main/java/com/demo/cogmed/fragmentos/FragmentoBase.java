package com.demo.cogmed.fragmentos;

import android.support.v4.app.Fragment;

import com.demo.cogmed.eventos.ObservadorEventos;
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

/**
 * Created by guillermo on 19/03/17.
 */

public class FragmentoBase extends Fragment implements ObservadorEventos {

    @Override
    public void enEvento(EventoVoltearAbajoCarta evento) { throw new UnsupportedOperationException(); }

    @Override
    public void enEvento(EventoJuegoGanado evento) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enEvento(EventoOcultarCartasPar evento) { throw new UnsupportedOperationException(); }

    @Override
    public void enEvento(EventoJuegoAnterior evento) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enEvento(EventoDificultadSeleccionada evento) { throw new UnsupportedOperationException(); }

    @Override
    public void enEvento(EventoVoltearCarta evento) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enEvento(EventoSiguienteJuego evento) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enEvento(EventoReiniciarFondo evento) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enEvento(EventoInicio evento) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enEvento(EventoTemaSeleccionado evento) { throw new UnsupportedOperationException(); }
}
