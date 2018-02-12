package com.mobileia.contacts;

import android.app.Activity;
import android.content.Intent;

import com.mobileia.contacts.activity.SelectContactActivity;
import com.mobileia.contacts.entity.Person;
import com.mobileia.contacts.listener.OnSelectContactListener;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 22/1/18.
 */

public class MobileiaContacts {
    /**
     * Codigo de peticion
     */
    public static final int PICK_CONTACT_REQUEST = 2351;
    /**
     * Almacena el manejador para devolver los contactos seleccionados
     */
    protected OnSelectContactListener mListener;

    /**
     * Iniciamos la activity para seleccionar los contactos
     * @param activity
     */
    public void show(Activity activity){
        // Creamos intent para abrir la activity
        Intent intent = new Intent(activity, SelectContactActivity.class);
        // Iniciamos activity
        activity.startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    /**
     * Funcion que se encarga de procesar los datos de la activity devueltos
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // Chequeamos si es la peticion correcta
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Verificamos si la respuesta fue correcta
            if (resultCode == Activity.RESULT_OK) {
                // Obtener listado de seleccionados
                ArrayList<Person> list = data.getParcelableArrayListExtra(SelectContactActivity.EXTRA_CONTACT_SELECTED);
                // Enviamos contactos al listener
                mListener.onSelectContacts(list);
            }else if(resultCode == Activity.RESULT_CANCELED){
                // enviamos al listener la cancelacion
                mListener.onSelectContactsCanceled();
            }
        }
    }

    /**
     * Setea el manejador
     * @param listener
     */
    public void setOnSelectContact(OnSelectContactListener listener){
        mListener = listener;
    }
}
