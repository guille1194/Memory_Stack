package com.demo.cogmed.eventos;

/**
 * Created by guillermo on 19/03/17.
 */

public abstract class EventoAbstracto implements Evento {

    protected abstract void fuego(ObservadorEventos observadorEventos);

}
