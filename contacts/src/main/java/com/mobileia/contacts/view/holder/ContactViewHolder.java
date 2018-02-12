package com.mobileia.contacts.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobileia.contacts.R;
import com.mobileia.contacts.adapter.BaseContactAdapter;

import jagerfield.mobilecontactslibrary.Contact.Contact;

/**
 * Created by matiascamiletti on 22/1/18.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    /**
     * Instancia del elemento que muestra el nombre del contact
     */
    public final TextView title;
    /**
     * Instancia del elemento de subtitulo
     */
    public final TextView subtitle;
    /**
     * Instancia de la imagen del contacto
     */
    public final ImageView image;
    /**
     * Alamacena el contacto que se encuentra en vista
     */
    public Contact contact;
    /**
     * Almacena el listener que ejecuta las acciones
     */
    protected OnContactViewHolder mListener;

    /**
     * Constructor que recibe los parametros obligatorios
     * @param itemView
     * @param listener
     */
    public ContactViewHolder(View itemView, OnContactViewHolder listener) {
        super(itemView);
        // Guardamos el listener
        mListener = listener;
        // Asignamos el evento del click
        itemView.setOnClickListener(this);
        // Buscamos elementos de la vista
        title = itemView.findViewById(R.id.text_name);
        subtitle = itemView.findViewById(R.id.text_data);
        image = itemView.findViewById(R.id.image);
    }

    /**
     * Funcion que se ejecuta al hacer click en un contacto
     * @param view
     */
    @Override
    public void onClick(View view) {
        // Llamamos la listener
        mListener.onClickContact(contact);
    }

    /**
     * Setea el contacto y muestra sus datos
     * @param c
     */
    public void bindContact(Contact c){
        // Almacenamos el contacto
        contact = c;
        // Setear el titulo del contacto
        title.setText(c.getDisplaydName());
        // verificamos si existe el segundo texto
        if(subtitle != null){
            // Setear el numero de telefono
            if(c.getNumbers().size() > 0){
                bindPhone(c);
            }else{
                bindEmail(c);
            }
        }
        // Cargar imagen
        String photo = c.getPhotoUri();
        if(photo != null && photo.length() > 0){
            Glide.with(image).load(photo).apply(RequestOptions.circleCropTransform()).into(image);
        }else{
            Glide.with(image).load(R.drawable.avatar_one).apply(RequestOptions.circleCropTransform()).into(image);
        }
    }

    /**
     * Funcion para mostrar el telefono del contacto
     * @param c
     */
    protected void bindPhone(Contact c){
        subtitle.setText(c.getNumbers().get(0).getNormalizedNumber());
    }

    /**
     * Funcion para mostrar el email si no tiene telefono
     * @param c
     */
    protected void bindEmail(Contact c){
        if(c.getEmails().size() > 0){
            subtitle.setText(c.getEmails().get(0).getEmail());
        }else{
            subtitle.setText("");
        }
    }

    /**
     * Interface para enlazar las acciones del Adapter
     */
    public interface OnContactViewHolder{
        void onClickContact(Contact contact);
    }
}
