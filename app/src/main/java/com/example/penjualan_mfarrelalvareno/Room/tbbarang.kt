package com.example.penjualan_mfarrelalvareno.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbbarang(
    @PrimaryKey
    val id_brg:Int,
    val nm_brg:String,
    val hrg_brg:Int,
    val jml_brg:Int

)