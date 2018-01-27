package com.mobileia.contacts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobileia.contacts.MobileiaContacts;
import com.mobileia.contacts.listener.OnSelectContactListener;

/**
 * Created by matiascamiletti on 27/1/18.
 */

abstract public class BaseContactActivity extends AppCompatActivity implements OnSelectContactListener {
    /**
     * Propiedad para almacenar libreria de contactos
     */
    protected MobileiaContacts mMiaContacts = new MobileiaContacts();

    /**
     * Funcion que se ejecuta al inicio para la creacion de la activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configuramos manejador
        mMiaContacts.setOnSelectContact(this);
    }

    /**
     * Funcion que se requiere para obtener los datos de los contactos seleccionados
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMiaContacts.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Funcion para abrir la pantalla de seleccion de contactos
     */
    public void showSelectContacts(){
        // Abrimos activity de contactos
        mMiaContacts.show(this);
    }
}
