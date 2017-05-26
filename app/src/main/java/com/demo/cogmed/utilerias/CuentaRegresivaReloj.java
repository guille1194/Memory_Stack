package com.demo.cogmed.utilerias;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by guillermo on 22/03/17.
 */

@SuppressLint("HandlerThread")
public abstract class CuentaRegresivaReloj {

    private long mDetenerTiempoEnFuturo;

    private long mMilisEnFuturo;

    private final long mTotalCuentaRegresiva;

    private final long mIntervaloCuentaRegresiva;

    private long mPausaTiempoRestante;

    private boolean mCorrerInicio;

    public CuentaRegresivaReloj(long milisEnTimer, long intervaloCuentaRegresiva, boolean correrInicio) {
        mMilisEnFuturo = milisEnTimer;
        mTotalCuentaRegresiva = mMilisEnFuturo;
        mIntervaloCuentaRegresiva = intervaloCuentaRegresiva;
        mCorrerInicio = correrInicio;
    }

    public final void cancelar() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public synchronized final CuentaRegresivaReloj crear() {
        if(mMilisEnFuturo <= 0) {
            enFinal();
        } else {
            mPausaTiempoRestante = mMilisEnFuturo;
        }

        if (mCorrerInicio) {
            continuar();
        }
        return this;
    }

    public void pausa() {
        if (Corriendo()) {
            mPausaTiempoRestante = tiempoRestante();
            cancelar();
        }
    }

    public void continuar() {
        if(Pausado()) {
            mMilisEnFuturo = mPausaTiempoRestante;
            mDetenerTiempoEnFuturo = SystemClock.elapsedRealtime() + mMilisEnFuturo;
            mHandler.sendMessage(mHandler.obtainMessage(MSG));
            mPausaTiempoRestante = 0;
        }
    }

    public boolean Pausado() {
        return (mPausaTiempoRestante > 0);
    }

    public boolean Corriendo() { return (!Pausado()); }

    public long tiempoRestante() {
        long milisHastaTerminar;
        if (Pausado()) {
            milisHastaTerminar = mPausaTiempoRestante;
        } else {
            milisHastaTerminar = mDetenerTiempoEnFuturo - SystemClock.elapsedRealtime();
            if (milisHastaTerminar < 0)
                milisHastaTerminar = 0;
        }
        return milisHastaTerminar;
    }

    public long totalCuentaRegresiva() { return mTotalCuentaRegresiva; }

    public long tiempoPasado() {return mTotalCuentaRegresiva - tiempoRestante(); }

    public boolean Empezado() { return (mPausaTiempoRestante <= mMilisEnFuturo); }

    public abstract void enSenal(long milisHastaTerminar);

    public abstract void enFinal();

    private static final int MSG = 1;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            synchronized (CuentaRegresivaReloj.this) {
                long milisRestantes = tiempoRestante();

                if(milisRestantes <= 0) {
                    cancelar();
                    enFinal();
                } else if (milisRestantes < mIntervaloCuentaRegresiva) {
                    sendMessageDelayed(obtainMessage(MSG), milisRestantes);
                } else {
                    long ultimaMarcaInicio = SystemClock.elapsedRealtime();
                    enSenal(milisRestantes);

                    long retraso = mIntervaloCuentaRegresiva - (SystemClock.elapsedRealtime() - ultimaMarcaInicio);

                    while (retraso < 0)
                        retraso += mIntervaloCuentaRegresiva;

                    sendMessageDelayed(obtainMessage(MSG), retraso);
                }
            }
        }
    };
}
