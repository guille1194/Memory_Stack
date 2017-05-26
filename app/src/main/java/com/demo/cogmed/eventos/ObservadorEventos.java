package com.demo.cogmed.eventos;

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

public interface ObservadorEventos {

    void enEvento(EventoVoltearAbajoCarta evento);

    void enEvento(EventoJuegoGanado evento);

    void enEvento(EventoOcultarCartasPar evento);

    void enEvento(EventoJuegoAnterior evento);

    void enEvento(EventoDificultadSeleccionada evento);

    void enEvento(EventoVoltearCarta evento);

    void enEvento(EventoSiguienteJuego evento);

    void enEvento(EventoReiniciarFondo evento);

    void enEvento(EventoInicio evento);

    void enEvento(EventoTemaSeleccionado evento);
}
