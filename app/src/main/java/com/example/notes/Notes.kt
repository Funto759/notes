package com.example.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "notes")
data class Notes(
    val title:String?,
    val content:String?,
    val date : String?,
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
):Serializable
