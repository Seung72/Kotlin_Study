package com.cholee.e14_mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// SQLite 버전
@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if(INSTANCE == null) {
                synchronized(ContactDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ContactDatabase::class.java, "contact")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}