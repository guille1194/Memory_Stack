package com.demo.cogmed.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.eventos.ui.EventoVoltearCarta;
import com.demo.cogmed.modelo.ArregloTablero;
import com.demo.cogmed.modelo.ConfiguracionTablero;
import com.demo.cogmed.modelo.Juego;
import com.demo.cogmed.utilerias.Utilerias;


/**
 * Created by guillermo on 24/03/17.
 */

public class VistaTablero extends LinearLayout {

    private LinearLayout.LayoutParams mRowLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams mTileLayoutParams;
    private int mAnchoPantalla;
    private int mLargoPantalla;
    private ConfiguracionTablero mConfiguracionTablero;
    private ArregloTablero mArregloTablero;
    private Map<Integer, VistaCuadro> mReferenciaVista;
    private List<Integer> Volteado = new ArrayList<Integer>();
    private boolean mBloqueado = false;
    private int mTamano;

    public VistaTablero(Context context) { this(context, null); }

    public VistaTablero(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mLargoPantalla = getResources().getDisplayMetrics().heightPixels - margin - padding*2;
        mAnchoPantalla = getResources().getDisplayMetrics().widthPixels - padding - Utilerias.px(20);
        mReferenciaVista = new HashMap<Integer, VistaCuadro>();
        setClipToPadding(false);
    }

    @Override
    protected void onFinishInflate() { super.onFinishInflate(); }

    public static VistaTablero fromXml(Context context, ViewGroup parent) {
        return (VistaTablero) LayoutInflater.from(context).inflate(R.layout.vista_tablero, parent, false);
    }

    public void setTablero(Juego juego) {
        mConfiguracionTablero = juego.configuracionTablero;
        mArregloTablero = juego.arregloTablero;

        int margenUnico = getResources().getDimensionPixelSize(R.dimen.card_margin);
        float densidad = getResources().getDisplayMetrics().density;
        margenUnico = Math.max((int) (1 * densidad), (int) (margenUnico - mConfiguracionTablero.dificultad * 2 * densidad));
        int margenSuma = 0;
        for (int fila = 0; fila < mConfiguracionTablero.numFilas; fila++) {
            margenSuma += margenUnico * 2;
        }
        int largoCuadros = (mLargoPantalla - margenSuma) / mConfiguracionTablero.numFilas;
        int anchoCuadros = (mAnchoPantalla - margenSuma) / mConfiguracionTablero.numCuadrosEnFila;
        mTamano = Math.min(largoCuadros, anchoCuadros);

        mTileLayoutParams = new LinearLayout.LayoutParams(mTamano, mTamano);
        mTileLayoutParams.setMargins(margenUnico, margenUnico, margenUnico, margenSuma);

        construirTablero();
    }

    private void construirTablero() {

        for (int fila = 0; fila < mConfiguracionTablero.numFilas; fila++) {
            agregarFilaTablero(fila);
        }
        setClipChildren(false);
    }

    private void agregarFilaTablero(int numFila) {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        for (int cuadro = 0; cuadro < mConfiguracionTablero.numCuadrosEnFila; cuadro++) {
            agregarCuadro(numFila * mConfiguracionTablero.numCuadrosEnFila + cuadro, linearLayout);
        }

        //agregar a esta vista
        addView(linearLayout, mRowLayoutParams);
        linearLayout.setClipChildren(false);
    }

    private void agregarCuadro(final int id, ViewGroup padre) {
        final VistaCuadro vistaCuadro = VistaCuadro.fromXml(getContext(), padre);
        vistaCuadro.setLayoutParams(mTileLayoutParams);
        padre.addView(vistaCuadro);
        padre.setClipChildren(false);
        mReferenciaVista.put(id, vistaCuadro);

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return mArregloTablero.getCuadroBitmap(id, mTamano);
            }

            @Override
            protected void onPostExecute(Bitmap resultado) {
                vistaCuadro.setImagenCuadro(resultado);
            }
        }.execute();

        vistaCuadro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!mBloqueado && vistaCuadro.tiradoAbajo()) {
                    vistaCuadro.levantar();
                    Volteado.add(id);
                    if(Volteado.size() == 2) {
                        mBloqueado = true;
                    }
                    Compartido.busEvento.notificar(new EventoVoltearCarta(id));
                }
            }
        });

        ObjectAnimator animadorEscalaX = ObjectAnimator.ofFloat(vistaCuadro, "scaleX", 0.8f, 1f);
        animadorEscalaX.setInterpolator(new BounceInterpolator());
        ObjectAnimator animadorEscalaY = ObjectAnimator.ofFloat(vistaCuadro, "scaleY", 0.8f, 1f);
        animadorEscalaY.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animadorEscalaX, animadorEscalaY);
        animatorSet.setDuration(500);
        vistaCuadro.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();
    }

    public void voltearTodoAbajo() {
        for (Integer id : Volteado) {
            mReferenciaVista.get(id).voltearAbajo();
        }
        Volteado.clear();
        mBloqueado = false;
    }

    public void ocultarCartas(int id1, int id2) {
        animarEsconder(mReferenciaVista.get(id1));
        animarEsconder(mReferenciaVista.get(id2));
        Volteado.clear();
        mBloqueado = false;
    }

    protected void animarEsconder(final VistaCuadro v) {
        ObjectAnimator animador = ObjectAnimator.ofFloat(v, "alpha", 0f);
        animador.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
                v.setVisibility(View.INVISIBLE);
            }
        });
        animador.setDuration(100);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animador.start();
    }
}
