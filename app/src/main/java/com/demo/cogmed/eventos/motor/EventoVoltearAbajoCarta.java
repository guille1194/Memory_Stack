package com.demo.cogmed.eventos.motor;

import com.demo.cogmed.eventos.EventoAbstracto;
import com.demo.cogmed.eventos.ObservadorEventos;

/**
 * Created by guillermo on 19/03/17.
 */

public class EventoVoltearAbajoCarta extends EventoAbstracto {

    public static final String TIPO = EventoVoltearAbajoCarta.class.getName();

    public EventoVoltearAbajoCarta() {
    }

    @Override
    protected void fuego(ObservadorEventos observadorEventos) { observadorEventos.enEvento(this); }

    @Override
    public String getTipo() { return TIPO; }

}
