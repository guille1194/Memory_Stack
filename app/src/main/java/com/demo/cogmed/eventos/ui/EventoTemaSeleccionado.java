package com.demo.cogmed.eventos.ui;

import com.demo.cogmed.eventos.EventoAbstracto;
import com.demo.cogmed.eventos.ObservadorEventos;
import com.demo.cogmed.temas.Tema;

/**
 * Created by guillermo on 22/03/17.
 */

public class EventoTemaSeleccionado extends EventoAbstracto {

    public static final String TIPO = EventoTemaSeleccionado.class.getName();
    public final Tema tema;

    public EventoTemaSeleccionado(Tema tema) {this.tema = tema; }

    @Override
    protected void fuego(ObservadorEventos observadorEventos) { observadorEventos.enEvento(this); }

    @Override
    public String getTipo() { return TIPO; }
}
