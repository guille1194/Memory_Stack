package com.demo.cogmed.eventos.ui;

import com.demo.cogmed.eventos.EventoAbstracto;
import com.demo.cogmed.eventos.ObservadorEventos;

/**
 * Created by guillermo on 22/03/17.
 */

public class EventoJuegoAnterior extends EventoAbstracto {

    public static final String TIPO = EventoJuegoAnterior.class.getName();

    @Override
    protected void fuego(ObservadorEventos observadorEventos) {
        observadorEventos.enEvento(this);
    }

    @Override
    public String getTipo() { return TIPO; }
}
