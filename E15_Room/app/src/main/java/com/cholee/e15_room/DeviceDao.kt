package com.cholee.e15_room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices")
    fun getAll(): List<Device>

    @Insert(onConflict = REPLACE)
    fun insert(device: Device)

    @Query("DELETE FROM devices")
    fun deleteAll()

}