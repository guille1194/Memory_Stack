package com.demo.cogmed.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.cogmed.R;
import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.comun.Musica;
import com.demo.cogmed.utilerias.CargadorFuentes;

/**
 * Created by guillermo on 18/04/17.
 */

public class VistaConfigPopup extends LinearLayout {
    private ImageView mImagenSonido;
    private TextView mTextoSonido;

    public VistaConfigPopup(Context context) { this(context, null); }

    public VistaConfigPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.drawable.settings_popup);
        LayoutInflater.from(getContext()).inflate(R.layout.vista_config_popup, this, true);
        mTextoSonido = (TextView) findViewById(R.id.sound_off_text);
        TextView vistaValor = (TextView) findViewById(R.id.rate_text);
        CargadorFuentes.setTypeface(context, new TextView[] {mTextoSonido, vistaValor}, CargadorFuentes.Fuente.GROBOLD);
        mImagenSonido = (ImageView) findViewById(R.id.sound_image);
        View sonidoApagado = findViewById(R.id.sound_off);
        sonidoApagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Musica.APAGADO = !Musica.APAGADO;
                setBotonMusica();
            }
        });
        View valor = findViewById(R.id.rate);
        valor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombrePaqueteApp = Compartido.contexto.getPackageName();
                try {
                    Compartido.actividad.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + nombrePaqueteApp)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    Compartido.actividad.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + nombrePaqueteApp)));
                }
            }
        });
        setBotonMusica();
    }

    private void setBotonMusica() {
        if (Musica.APAGADO) {
            mTextoSonido.setText("Sonido Apagado");
            mImagenSonido.setImageResource(R.drawable.button_music_off);
        } else {
            mTextoSonido.setText("Sonido Encendido");
            mImagenSonido.setImageResource(R.drawable.button_music_on);
        }

    }

}
