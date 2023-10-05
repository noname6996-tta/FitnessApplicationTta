package com.tta.fitnessapplication.data.model.noti

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "noti_table")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String?,
    var text: String?,
    var icon: Int,
    var value: String?,
    var hour: Int,
    var min: Int,
    var type: Int,
    var enable : Boolean = true
): Serializable
