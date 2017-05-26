package com.demo.cogmed.eventos.ui;

import com.demo.cogmed.eventos.EventoAbstracto;
import com.demo.cogmed.eventos.ObservadorEventos;

/**
 * Created by guillermo on 22/03/17.
 */

public class EventoDificultadSeleccionada extends EventoAbstracto {

    public static final String TIPO = EventoDificultadSeleccionada.class.getName();

    public final int dificultad;

    public EventoDificultadSeleccionada(int dificultad) { this.dificultad = dificultad; }

    @Override
    protected void fuego(ObservadorEventos observadorEventos) { observadorEventos.enEvento(this); }

    @Override
    public String getTipo() { return TIPO; }
}
