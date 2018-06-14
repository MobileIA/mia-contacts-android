package com.mobileia.contacts.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mobileia.contacts.MobileiaContacts;
import com.mobileia.contacts.entity.Person;
import com.mobileia.contacts.listener.OnSelectContactListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnSelectContactListener {

    protected MobileiaContacts mMiaContacts = new MobileiaContacts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configuramos manejador
        mMiaContacts.setOnSelectContact(this);
    }

    @Override
    public void onSelectContacts(ArrayList<Person> persons) {
        for(Person p : persons){
            System.out.println("Persona: " + p.fullname + " Tel: " + p.phone);
        }
    }

    @Override
    public void onSelectContactsCanceled() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMiaContacts.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v){
        // Abrimos activity de contactos
        mMiaContacts.show(this);
    }

}
