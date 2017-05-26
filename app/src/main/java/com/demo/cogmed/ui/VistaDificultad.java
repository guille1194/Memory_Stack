package com.demo.cogmed.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;

import java.util.Locale;

/**
 * Created by guillermo on 18/04/17.
 */

public class VistaDificultad extends LinearLayout {

    private ImageView mTitulo;

    public VistaDificultad(Context context) { this(context, null); }

    public VistaDificultad(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.vista_dificultad, this, true);
        setOrientation(LinearLayout.VERTICAL);
        mTitulo = (ImageView) findViewById(R.id.title);
    }

    public void setDificultad(int dificultad, int estrellas) {
        String recursoTitulo = String.format(Locale.US, "button_difficulty_%d_star_%d", dificultad, estrellas);
        int idRecursoDibujable = Compartido.contexto.getResources().getIdentifier(recursoTitulo, "drawable", Compartido.contexto.getPackageName());
        mTitulo.setImageResource(idRecursoDibujable);
    }

}
