package com.demo.cogmed.utilerias;

import android.util.Log;
/**
 * Created by guillermo on 22/03/17.
 */

public class Reloj {
    private static PausaTimer mPausaTimer = null;
    private static Reloj mInstancia = null;

    private Reloj() { Log.i("mi_etiqueta", "INSTANCIA NUEVA DE RELOJ"); }

    public static class PausaTimer extends CuentaRegresivaReloj {
        private ContadorTiempo mContadorTiempo = null;

        public PausaTimer(long milisEnTimer, long intervaloCuentaRegresiva, boolean correrInicio, ContadorTiempo contadorTiempo) {
            super(milisEnTimer, intervaloCuentaRegresiva, correrInicio);
            mContadorTiempo = contadorTiempo;
        }

        @Override
        public void enSenal(long milisHastaTerminar) {
            if (mContadorTiempo != null) {
                mContadorTiempo.enSenal(milisHastaTerminar);
            }
        }

        @Override
        public void enFinal() {
            if(mContadorTiempo != null) {
                mContadorTiempo.enFinal();
            }
        }

    }

    public static Reloj getInstancia() {
        if(mInstancia == null) {
            mInstancia = new Reloj();
        }
        return mInstancia;
    }

    public void inicioTimer(long milisEnTimer, long intervaloCuentaRegresiva, ContadorTiempo contadorTiempo) {
        if (mPausaTimer != null) {
            mPausaTimer.cancelar();
        }
        mPausaTimer = new PausaTimer(milisEnTimer, intervaloCuentaRegresiva, true, contadorTiempo );
        mPausaTimer.crear();
    }

    public void pausa(){
        if(mPausaTimer != null) {
            mPausaTimer.pausa();
        }
    }

    public void continuar() {
        if(mPausaTimer != null) {
            mPausaTimer.continuar();
        }
    }

    public void cancelar() {
        if(mPausaTimer != null) {
            mPausaTimer.mContadorTiempo = null;
            mPausaTimer.cancelar();
        }
    }

    public long getTiempoPasado() { return mPausaTimer.tiempoPasado(); }

    public interface ContadorTiempo {
        public void enSenal(long milisHastaTerminar);

        public void enFinal();

    }
}
