package com.cholee.e14_mvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData

class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>>{
        return contacts
    }

    fun insert(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.insert(contact)
            })
            thread.start()
        } catch (e: Exception) {
            Log.e("insert ERROR", e.toString())
        }
    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e: Exception) {
            Log.e("delete ERROR", e.toString())
        }
    }
}