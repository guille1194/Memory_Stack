package com.demo.cogmed;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.demo.cogmed.comun.Compartido;
import com.demo.cogmed.eventos.BusEvento;
import com.demo.cogmed.eventos.ui.EventoJuegoAnterior;
import com.demo.cogmed.motor.ControladorPantalla;
import com.demo.cogmed.motor.Motor;
import com.demo.cogmed.ui.PopupManager;
import com.demo.cogmed.utilerias.Utilerias;

import static com.demo.cogmed.motor.ControladorPantalla.*;

/**
 * Created by guillermo on 19/03/17.
 */

public class MainActivity extends FragmentActivity {

    private ImageView mImagenFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Compartido.contexto = getApplicationContext();
        Compartido.motor = Motor.getInstancia();
        Compartido.busEvento = BusEvento.getInstancia();

        setContentView(R.layout.activity_main);
        mImagenFondo = (ImageView) findViewById(R.id.background_image);

        Compartido.actividad = this;
        Compartido.motor.start();
        Compartido.motor.setVistaImagenFondo(mImagenFondo);

        //establecer fondo pantalla
        setImagenFondo();

        //establecer menu
        getInstancia().pantallaAbierta(Pantalla.MENU);
    }

    @Override
    protected void onDestroy() {
        Compartido.motor.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(PopupManager.esMostrado()) {
            PopupManager.cerrarPopup();
            if(ControladorPantalla.getUltimaPantalla() == Pantalla.JUEGO) {
                Compartido.busEvento.notificar(new EventoJuegoAnterior());
            }
        } else if (ControladorPantalla.getInstancia().onBack()) {
            super.onBackPressed();
        }
    }

    private void setImagenFondo() {
        Bitmap bitmap = Utilerias.reducirEscala(R.drawable.background, Utilerias.anchoPantalla(), Utilerias.largoPantalla());
        bitmap = Utilerias.cortar(bitmap, Utilerias.largoPantalla(), Utilerias.anchoPantalla());
        bitmap = Utilerias.reducirEscalaBitmap(bitmap, 2);
        mImagenFondo.setImageBitmap(bitmap);
    }
}
