package com.demo.cogmed.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.eventos.motor.EventoJuegoGanado;
import com.demo.cogmed.eventos.motor.EventoOcultarCartasPar;
import com.demo.cogmed.eventos.motor.EventoVoltearAbajoCarta;
import com.demo.cogmed.modelo.Juego;
import com.demo.cogmed.ui.PopupManager;
import com.demo.cogmed.ui.VistaTablero;
import com.demo.cogmed.utilerias.CargadorFuentes;
import com.demo.cogmed.utilerias.Reloj;

import static com.demo.cogmed.utilerias.CargadorFuentes.*;

/**
 * Created by guillermo on 18/04/17.
 */

public class FragmentoJuego extends FragmentoBase {

    private VistaTablero mVistaTablero;
    private TextView mTiempo;
    private ImageView mImagenTiempo;
    private LinearLayout ads;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragmento_juego, container, false);
        view.setClipChildren(false);
        ((ViewGroup)view.findViewById(R.id.game_board)).setClipChildren(false);
        mTiempo = (TextView) view.findViewById(R.id.time_bar_text);
        mImagenTiempo = (ImageView) view.findViewById(R.id.time_bar_image);
        setTypeface(Compartido.contexto, new TextView[] {mTiempo}, Fuente.GROBOLD);
        mVistaTablero = VistaTablero.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.game_container);
        frameLayout.addView(mVistaTablero);
        frameLayout.setClipChildren(false);

        //construir tablero
        construirTablero();
        Compartido.busEvento.escuchar(EventoVoltearAbajoCarta.TIPO, this);
        Compartido.busEvento.escuchar(EventoOcultarCartasPar.TIPO, this);
        Compartido.busEvento.escuchar(EventoJuegoGanado.TIPO, this);

        return view;
    }

    @Override
    public void onDestroy() {
        Compartido.busEvento.noescuchar(EventoVoltearAbajoCarta.TIPO, this);
        Compartido.busEvento.noescuchar(EventoOcultarCartasPar.TIPO, this);
        Compartido.busEvento.noescuchar(EventoJuegoGanado.TIPO, this);
        super.onDestroy();
    }

    private void construirTablero() {
        Juego juego = Compartido.motor.getJuegoActivo();
        int tiempo = juego.configuracionTablero.tiempo;
        setTiempo(tiempo);
        mVistaTablero.setTablero(juego);

        relojInicio(tiempo);
    }

    private void setTiempo(int tiempo) {
        int min = tiempo / 60;
        int seg = tiempo - min*60;
        mTiempo.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", seg));
    }

    private void relojInicio(int seg) {
        Reloj reloj = Reloj.getInstancia();
        reloj.inicioTimer(seg * 1000, 1000, new Reloj.ContadorTiempo() {

            @Override
            public void enSenal(long milisHastaTerminar) { setTiempo((int) (milisHastaTerminar/1000)); }

            @Override
            public void enFinal() {
                setTiempo(0);
            }
        });
    }

    @Override
    public void enEvento(EventoJuegoGanado evento) {
        mTiempo.setVisibility(View.GONE);
        mImagenTiempo.setVisibility(View.GONE);
        PopupManager.mostrarPopupGanador(evento.estadoJuego);
    }

    @Override
    public void enEvento(EventoVoltearAbajoCarta evento) { mVistaTablero.voltearTodoAbajo();}

    @Override
    public void enEvento(EventoOcultarCartasPar evento) { mVistaTablero.ocultarCartas(evento.id1, evento.id2); }


}
