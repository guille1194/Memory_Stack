package com.demo.cogmed.eventos.ui;

import com.demo.cogmed.eventos.EventoAbstracto;
import com.demo.cogmed.eventos.ObservadorEventos;

/**
 * Created by guillermo on 22/03/17.
 */

public class EventoVoltearCarta extends EventoAbstracto {

    public static final String TIPO = EventoVoltearCarta.class.getName();

    public final int id;

    public EventoVoltearCarta(int id) {
        this.id = id;
    }

    @Override
    protected void fuego(ObservadorEventos observadorEventos) { observadorEventos.enEvento(this); }

    @Override
    public String getTipo() { return TIPO; }
}
