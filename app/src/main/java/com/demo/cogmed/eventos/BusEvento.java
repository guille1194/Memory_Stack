package com.demo.cogmed.eventos;


import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.synchronizedList;

/**
 * Created by guillermo on 19/03/17.
 */

public class BusEvento {
    private Handler mHandler;
    private static BusEvento mInstancia = null;
    private final Map<String, List<ObservadorEventos>> eventos = Collections.synchronizedMap(new HashMap<String, List<ObservadorEventos>>());
    private Object obj = new Object();

    private BusEvento() { mHandler = new Handler(); }

    public static BusEvento getInstancia() {
        if (mInstancia == null) {
            mInstancia = new BusEvento();
        }
        return mInstancia;
    }

    synchronized public void escuchar(String tipoEvento, ObservadorEventos observadorEventos) {
        List<ObservadorEventos> observadores = eventos.get(tipoEvento);
        if(observadores == null) {
            observadores = synchronizedList(new ArrayList<ObservadorEventos>());
        }
        observadores.add(observadorEventos);
        eventos.put(tipoEvento, observadores);
    }

    synchronized public void noescuchar(String tipoEvento, ObservadorEventos observadorEventos) {
        List<ObservadorEventos> observadores = eventos.get(tipoEvento);
        if (observadores != null) {
            observadores.remove(observadorEventos);
        }
    }

    public void notificar(Evento evento) {
        synchronized (obj) {
            List<ObservadorEventos> observadores = eventos.get(evento.getTipo());
            if (observadores != null) {
                for (ObservadorEventos observador : observadores) {
                    EventoAbstracto eventoAbstracto = (EventoAbstracto) evento;
                    eventoAbstracto.fuego(observador);
                }
            }
        }
    }

    public void notificar(final Evento evento, long retraso) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BusEvento.this.notificar(evento);
            }
        }, retraso);
    }
}




