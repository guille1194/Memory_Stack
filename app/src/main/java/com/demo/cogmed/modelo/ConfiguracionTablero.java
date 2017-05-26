package com.demo.cogmed.modelo;

import android.graphics.Bitmap;

/**
 * Created by guillermo on 24/03/17.
 */

public class ConfiguracionTablero {

    private static final int _6 = 6;
    private static final int _12 = 12;
    private static final int _18 = 18;
    private static final int _28 = 28;
    private static final int _32 = 32;
    private static final int _50 = 50;

    public final int dificultad;
    public final int numCuadros;
    public final int numCuadrosEnFila;
    public final int numFilas;
    public final int tiempo;

    public ConfiguracionTablero(int dificultad) {
        this.dificultad = dificultad;
        switch (dificultad) {
            case 1:
                numCuadros = _6;
                numCuadrosEnFila = 3;
                numFilas = 2;
                tiempo = 60;
                break;
            case 2:
                numCuadros = _12;
                numCuadrosEnFila = 4;
                numFilas = 3;
                tiempo = 90;
                break;
            case 3:
                numCuadros = _18;
                numCuadrosEnFila = 6;
                numFilas = 3;
                tiempo = 120;
                break;
            case 4:
                numCuadros = _28;
                numCuadrosEnFila = 7;
                numFilas = 4;
                tiempo = 150;
                break;
            case 5:
                numCuadros = _32;
                numCuadrosEnFila = 8;
                numFilas = 4;
                tiempo = 180;
                break;
            case 6:
                numCuadros = _50;
                numCuadrosEnFila = 10;
                numFilas = 5;
                tiempo = 210;
                break;
            default:
                throw new IllegalArgumentException("Selecciona uno de los tamanos predefinidos");
        }
    }
}
