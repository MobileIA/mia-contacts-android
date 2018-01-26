package com.mobileia.contacts.helper;

import android.Manifest;
import android.app.Activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by matiascamiletti on 22/1/18.
 */

public class PermissionHelper {
    /**
     * Funcion para pedir los permisos para poder obtener los contactos
     * @param activity
     * @param listener
     */
    public static void readContacts(final Activity activity, final OnReadContacts listener){
        // Pedimos permisos
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        listener.success();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // Cerrar activity
                        //activity.finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    }
                })
                .check();
    }

    public interface OnReadContacts{
        void success();
        void denied();
    }
}
