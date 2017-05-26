package com.demo.cogmed.eventos.motor;

import com.demo.cogmed.eventos.EventoAbstracto;
import com.demo.cogmed.eventos.ObservadorEventos;
import com.demo.cogmed.modelo.EstadoJuego;

/**
 * Created by guillermo on 19/03/17.
 */

public class EventoJuegoGanado extends EventoAbstracto {

    public static final String TIPO = EventoJuegoGanado.class.getName();

    public EstadoJuego estadoJuego;

    public EventoJuegoGanado(EstadoJuego estadoJuego) { this.estadoJuego = estadoJuego; }

    @Override
    protected void fuego(ObservadorEventos observadorEventos) { observadorEventos.enEvento(this); }

    @Override
    public String getTipo() { return TIPO; }
}
