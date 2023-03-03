package com.cholee.e15_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class Device(

    @PrimaryKey(autoGenerate = true) val uid: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "os") var os: String,
    @ColumnInfo(name = "version") var version: Int

) {
    constructor(): this(null, "", "", 0)
}
