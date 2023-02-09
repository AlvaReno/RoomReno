package com.example.penjualan_mfarrelalvareno.Room

import androidx.room.*

@Dao
interface tbbarangDao{
    @Insert
    fun addtbbarang(tbbar:tbbarang)
    @Update
    fun updatetbbarang(tbbar: tbbarang)
    @Delete
    fun deltbbarang(tbbar: tbbarang)
    @Query("SELECT * FROM tbbarang")
    fun tampilsemua():List<tbbarang>
    @Query("SELECT * FROM tbbarang WHERE id_brg = :barang_id")
    fun tampilsemuah(barang_id:Int): List <tbbarang>
}