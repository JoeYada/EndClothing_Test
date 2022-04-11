package com.example.endclothing.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val identification: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String
):Serializable
