package com.demo.cogmed.eventos.motor;

import com.demo.cogmed.eventos.EventoAbstracto;
import com.demo.cogmed.eventos.ObservadorEventos;

/**
 * Created by guillermo on 19/03/17.
 */

public class EventoOcultarCartasPar extends EventoAbstracto {

    public static final String TIPO = EventoOcultarCartasPar.class.getName();
    public int id1;
    public int id2;

    public EventoOcultarCartasPar(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    protected void fuego(ObservadorEventos observadorEventos) {observadorEventos.enEvento(this);}

    @Override
    public String getTipo() { return TIPO; }
}
