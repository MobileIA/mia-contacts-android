package com.mobileia.contacts.helper;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mobileia.contacts.R;

/**
 * Created by matiascamiletti on 22/1/18.
 */

public class ToolbarHelper {
    /**
     * Configura la toolbar
     * @param activity
     */
    public static void setUp(AppCompatActivity activity, String title){
        // Obtenemos toolbar
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        // Seteamos la nueva Toolbar
        activity.setSupportActionBar(toolbar);
        // Seteamos el titulo
        activity.getSupportActionBar().setTitle(title);
        // Activamos boton para volver
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
