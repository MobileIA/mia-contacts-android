package com.mobileia.contacts.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mobileia.contacts.R;
import com.mobileia.contacts.adapter.BaseContactAdapter;
import com.mobileia.contacts.adapter.MiniContactAdapter;
import com.mobileia.contacts.adapter.NormalContactAdapter;
import com.mobileia.contacts.helper.PermissionHelper;
import com.mobileia.contacts.helper.ToolbarHelper;
import com.mobileia.recyclerview.MobileiaRecyclerView;

import java.util.ArrayList;

import jagerfield.mobilecontactslibrary.Contact.Contact;
import jagerfield.mobilecontactslibrary.ImportContactsAsync;

public class SelectContactActivity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener, BaseContactAdapter.OnContactAdapter {

    /**
     * Almacena la barra de busqueda
     */
    protected MaterialSearchView mSearchView;
    /**
     * Almacena el adapter
     */
    protected NormalContactAdapter mAdapter;
    /**
     * Almacena el adapter
     */
    protected MiniContactAdapter mAdapterSelect;
    /**
     * Almacena el view del listado
     */
    protected MobileiaRecyclerView mRecyclerView;
    /**
     * Almacena el listado de los seleccionados
     */
    protected RecyclerView mRecyclerSelect;
    /**
     * Almacena box de los seleccionados
     */
    protected LinearLayout mContainerSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);
        // Configurar toolbar
        ToolbarHelper.setUp(this, "Seleccionar contactos:");
        // Configurar vista
        setUpViews();
        // Configurar listado
        setUpRecyclerView();
        // Pedir permisos
        requestPermission();
    }
    /**
     * Se configura el menu para agregar el icono de busqueda a la toolbar
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menu
        getMenuInflater().inflate(R.menu.select_contact, menu);
        // Obtenemos el item del menu
        MenuItem item = menu.findItem(R.id.navigation_search);
        // Lo configuramos a la barra de busqueda
        mSearchView.setMenuItem(item);
        // Seteamos true
        return true;
    }
    /**
     * Manejador del menu de la toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Funcion que se ejecuta cuando se toca la tecla para atras
     */
    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Funcion que se ejecuta al seleccionar un contacto
     * @param contact
     */
    @Override
    public void onClickContact(Contact contact) {
        // Mostrar listado
        mContainerSelect.setVisibility(View.VISIBLE);
        // Verificar si ya existe
        if(mAdapterSelect.isExist(contact)){
            return;
        }
        // Agregar al listado de seleccionados
        mAdapterSelect.addContact(contact);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("Busqueda1: " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText != null && newText.length() > 2) {
            mAdapter.filterByQuery(newText);
        }else if(newText == null ||newText.length() == 0){
            mAdapter.clearFilter();
        }
        return false;
    }

    /**
     * Funcion que se encarga de inciar el listado y el adaptador
     */
    protected void setUpRecyclerView(){
        // Configuramos el Adapter
        setUpAdapter();
        // Obtenemos el listado
        mRecyclerView = findViewById(R.id.list);
        // Configuramos listado
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Asignamos adapter
        mRecyclerView.setAdapter(mAdapter);
        // Cargar los seleccionados
        //mAdapterSelect.addAll(new AttendRealm().fetchAllByEvent(mEventId));
        // Asignamos listener del click
        //mAdapterSelect.setListener(this);
        // Obtenemos listado de contactos seleccionados
        mRecyclerSelect = (RecyclerView)findViewById(R.id.list_select);
        // Configuramos listado
        mRecyclerSelect.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Asignamos adapter
        mRecyclerSelect.setAdapter(mAdapterSelect);
        // Verificar si ya tiene guardados
        /*if(mAdapterSelect.getItemCount() > 0){
            // Mostrar listado
            findViewById(R.id.container_select).setVisibility(View.VISIBLE);
        }*/
        // Funcionalidad para busqueda rapida
        //VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller);
        // Connect the recycler to the scroller (to let the scroller scroll the list)
        //fastScroller.setRecyclerView(recyclerView);
        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        //recyclerView.addOnScrollListener(fastScroller.getOnScrollListener());
        // Obtenemos contenedor de los seleccionados
        mContainerSelect = findViewById(R.id.container_select);
    }

    /**
     * inicializa los adapters requeridos
     */
    protected void setUpAdapter(){
        // Creamos el adaptador
        mAdapter = new NormalContactAdapter();
        // Asignamos listener del click
        mAdapter.setOnClickListener(this);
        // Configurar para que se pueda agregar nuevos contactos
        //mAdapter.activateNewContacts();
        // Creamos el adaptador seleccionado
        mAdapterSelect = new MiniContactAdapter();
    }

    /**
     * Funcion que se encarga de pedir los permisos necesarios
     */
    protected void requestPermission(){
        PermissionHelper.readContacts(this, new PermissionHelper.OnReadContacts() {
            @Override
            public void success() {
                new ImportContactsAsync(SelectContactActivity.this, new ImportContactsAsync.ICallback(){
                    @Override
                    public void mobileContacts(ArrayList<Contact> contactList){
                        // Setear en el adapter
                        mAdapter.loadContacts(contactList);
                    }
                }).execute();
            }

            @Override
            public void denied() {

            }
        });
    }
    /**
     * Funcion qie muestra los datos de las vistas
     */
    protected void setUpViews(){
        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);
    }



}
