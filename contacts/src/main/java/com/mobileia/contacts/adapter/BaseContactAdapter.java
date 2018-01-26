package com.mobileia.contacts.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mobileia.contacts.R;
import com.mobileia.contacts.view.holder.ContactViewHolder;

import java.util.ArrayList;
import java.util.stream.Collectors;

import jagerfield.mobilecontactslibrary.Contact.Contact;

/**
 * Created by matiascamiletti on 22/1/18.
 */

public class BaseContactAdapter extends RecyclerView.Adapter<ContactViewHolder> implements ContactViewHolder.OnContactViewHolder {
    /**
     * Determina el tipo de vista que se va a mostrar, estilo normal
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * Determina el tipo de vista que se va a mostrar, estilo mini
     */
    public static final int TYPE_MINI = 1;

    /**
     * Almacena el tipo de listado a mostrar
     */
    protected int mType = 0;
    /**
     * Almacena el listado con todos los contactos
     */
    protected ArrayList<Contact> mAll = new ArrayList<Contact>();
    /**
     * Almacena el listado de los contactos filtrados
     */
    protected ArrayList<Contact> mFiltered = new ArrayList<Contact>();
    /**
     * Almacena el listener
     */
    protected OnContactAdapter mListener = null;

    /**
     * Función que se ejecuta al momento de hacer click en un contacto
     * @param contact
     */
    @Override
    public void onClickContact(Contact contact) {
        // llamar al listener si existe
        if(mListener != null){
            mListener.onClickContact(contact);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout predeterminado
        int layout = R.layout.item_contact;
        // Cambiar si es de otro tipo
        if(mType == TYPE_MINI){
            layout = R.layout.item_contact_mini;
        }
        // Creamos view Holder
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        // Bindeamos el contacto
        holder.bindContact(mFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return mFiltered.size();
    }

    /**
     * Funcion que se encarga de cargar los contactos
     * @param list
     */
    public void loadContacts(ArrayList<Contact> list){
        // Asignamos todos los contactos
        mAll.addAll(list);
        mFiltered.addAll(list);
        // Refrecamos adapter
        notifyDataSetChanged();
    }

    /**
     * Funcion que permite agregar un contacto individual
     * @param c
     */
    public void addContact(Contact c){
        // Agregamos al listado
        mFiltered.add(c);
        // Refrecamos adapter
        notifyDataSetChanged();
    }

    /**
     * Funcion que se encarga de filtrar los contactos
     * @param query
     */
    public void filterByQuery(String query){
        // Limpiamos contactos mostrados
        mFiltered.clear();
        // Recorremos todos los contactos
        for (Contact c: mAll) {
            if(c.getDisplaydName().toLowerCase().contains(query.toLowerCase())){
                mFiltered.add(c);
            }
        }
        // Refrescar listado
        notifyDataSetChanged();
    }

    /**
     * Limpia las busquedas y carga nuevamente todos los contactos
     */
    public void clearFilter(){
        // Limpiamos contactos mostrados
        mFiltered.clear();
        // Cargamos todos los contactos nuevamente
        mFiltered.addAll(mAll);
        // Refrescar listado
        notifyDataSetChanged();
    }

    /**
     * Funcion para eliminar un contacto del listado
     * @param c
     */
    public void removeContact(Contact c){
        // Eliminamos contacto del listado
        mFiltered.remove(c);
        // Actualizamos el listado
        notifyDataSetChanged();
    }

    /**
     * Valida si ya existe el contacto en el listado filtrado
     * @param c
     * @return
     */
    public boolean isExist(Contact c){
        for (Contact attend : mFiltered) {
            if(attend.getId() == c.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el listado que se muestra
     * @return
     */
    public ArrayList<Contact> getItems(){
        return mFiltered;
    }

    /**
     * Setea el listener para el listado
     * @param listener
     */
    public void setOnClickListener(OnContactAdapter listener){
        mListener = listener;
    }

    /**
     * Interface para enlazar las acciones del adapter
     */
    public interface OnContactAdapter{
        void onClickContact(Contact contact);
    }
}
