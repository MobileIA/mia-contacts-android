package com.mobileia.contacts.listener;

import com.mobileia.contacts.entity.Person;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 24/1/18.
 */

public interface OnSelectContactListener {
    void onSelectContacts(ArrayList<Person> persons);
}
