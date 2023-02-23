package com.cholee.e14_mvvm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {

    // LiveData 타입으로 반환해줌
    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}