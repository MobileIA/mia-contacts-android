package com.mobileia.contacts.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import jagerfield.mobilecontactslibrary.Contact.Contact;

/**
 * Created by matiascamiletti on 24/1/18.
 */

public class Person implements Parcelable {

    public long id;

    public String fullname;

    public String phone;

    public String photo;

    public int has_account;

    public Person(){}

    public Person(Parcel in){
        id = in.readLong();
        fullname = in.readString();
        phone = in.readString();
        photo = in.readString();
        has_account = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(fullname);
        parcel.writeString(phone);
        parcel.writeString(photo);
        parcel.writeInt(has_account);
    }

    /**
     * Convierte un contacto en esta entidad
     * @param contact
     * @return
     */
    public static Person fromContact(Contact contact){
        Person person = new Person();
        person.id = contact.getId();
        person.fullname = contact.getDisplaydName();
        if(contact.getNumbers().size() > 0){
            person.phone = contact.getNumbers().get(0).getNormalizedNumber();
        }
        person.photo = contact.getPhotoUri();
        return person;
    }

    /**
     * Convierte un listado de Contactos a personas
     * @param contacts
     * @return
     */
    public static ArrayList<Person> fromContacts(ArrayList<Contact> contacts){
        ArrayList<Person> list = new ArrayList<>();
        for(Contact c : contacts){
            list.add(fromContact(c));
        }
        return list;
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
