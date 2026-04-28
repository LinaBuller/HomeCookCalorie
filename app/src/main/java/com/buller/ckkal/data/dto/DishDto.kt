package com.buller.ckkal.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "dishes")
data class DishDto(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var allKcal: Double = 0.0,
    var allFats: Double = 0.0,
    var allCarbs: Double = 0.0,
    var allProteins: Double = 0.0,
    var allWeight: Double = 0.0,
    @ColumnInfo(defaultValue = "")
    var createdAt: String = ""
)