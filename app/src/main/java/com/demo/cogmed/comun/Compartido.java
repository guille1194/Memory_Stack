package com.demo.cogmed.comun;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.demo.cogmed.eventos.BusEvento;
import com.demo.cogmed.motor.Motor;

/**
 * Created by guillermo on 19/03/17.
 */

public class Compartido {

    public static Context contexto;
    public static FragmentActivity actividad;
    public static Motor motor;
    public static BusEvento busEvento;
}
