package com.demo.cogmed.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demo.cogmed.R;
/**
 * Created by guillermo on 1/04/17.
 */

public class VistaCuadro extends FrameLayout {
    private RelativeLayout mTopImagen;
    private ImageView mImagenCuadro;
    private boolean mVoltearAbajo = true;

    public VistaCuadro(Context context) { this(context, null); }

    public VistaCuadro(Context context, AttributeSet attrs) { super(context, attrs); }

    public static VistaCuadro fromXml(Context context, ViewGroup padre) {
        return (VistaCuadro) LayoutInflater.from(context).inflate(R.layout.vista_cuadro, padre, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopImagen = (RelativeLayout) findViewById(R.id.image_top);
        mImagenCuadro = (ImageView) findViewById(R.id.image);
    }

    public void setImagenCuadro(Bitmap bitmap) { mImagenCuadro.setImageBitmap(bitmap); }

    public void levantar() {
        mVoltearAbajo = false;
        voltear();
    }

    public void voltearAbajo() {
        mVoltearAbajo = true;
        voltear();
    }

    private void voltear() {
        AnimacionVoltear animacionVoltear = new AnimacionVoltear(mTopImagen, mImagenCuadro);
        if(mTopImagen.getVisibility() == View.GONE);
        animacionVoltear.reversa();
    }

    public boolean tiradoAbajo() { return mVoltearAbajo; }

    public class AnimacionVoltear extends Animation {
        private Camera camera;

        private View fromView;
        private View toView;

        private float centroX;
        private float centroY;

        private boolean forward = true;

        public AnimacionVoltear(View fromView, View toView) {
            this.fromView = fromView;
            this.toView = toView;

            setDuration(700);
            setFillAfter(false);
            setInterpolator(new AccelerateDecelerateInterpolator());
        }

        public void reversa() {
            forward = false;
            View cambiarVista = toView;
            toView = fromView;
            fromView = cambiarVista;
        }

        @Override
        public void initialize(int ancho, int largo, int padreAncho, int padreLargo) {
            super.initialize(ancho, largo, padreAncho, padreLargo);
            centroX = ancho / 2;
            centroY = largo / 2;
            camera = new Camera();
        }

        @Override
        public void applyTransformation(float tiempoInterpolacion, Transformation t) {
            final double radianes = Math.PI * tiempoInterpolacion;
            float grados = (float) (180.0 * radianes / Math.PI);


            if(tiempoInterpolacion >= 0.5f) {
                grados -= 180.f;
                fromView.setVisibility(View.GONE);
                toView.setVisibility(View.VISIBLE);
            }

            if (forward)
                grados = -grados;

            final Matrix matrix = t.getMatrix();
            camera.save();
            camera.rotateY(grados);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-centroX, -centroY);
            matrix.postTranslate(centroX, centroY);
        }
    }
}
