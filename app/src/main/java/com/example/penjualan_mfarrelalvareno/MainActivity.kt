package com.example.penjualan_mfarrelalvareno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_mfarrelalvareno.Room.Constant
import com.example.penjualan_mfarrelalvareno.Room.dbpenjualan
import com.example.penjualan_mfarrelalvareno.Room.tbbarang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val db by lazy { dbpenjualan(this) }
    lateinit var adapterActivity: AdapterActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadbarang()
    }

    fun loadbarang() {
        CoroutineScope(Dispatchers.IO).launch {
            val barangs = db.tbbarDao().tampilsemua()
            Log.d("MainActivity", "dbResponse:$barangs")
            withContext(Dispatchers.Main) {
                adapterActivity.setData(barangs)
            }

        }
    }

    fun setupListener() {
        button_create.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(barangId: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", barangId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        adapterActivity =
            AdapterActivity(arrayListOf(), object : AdapterActivity.OnAdapterListener {

                override fun onClick(barang: tbbarang) {
                    //read detail note
                    intentEdit(barang.id_brg, Constant.TYPE_READ)

                }

                override fun onUpdate(barang: tbbarang) {
                    intentEdit(barang.id_brg, Constant.TYPE_UPDATE)

                }

                override fun onDelete(barang: tbbarang) {
                        deleteAlert(barang)

                }
            })
        listdata.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterActivity
        }
    }

    private fun deleteAlert(tbbar: tbbarang) {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${tbbar.nm_brg}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbbarDao().deltbbarang(tbbar)
                    loadbarang()
                }
            }
        }
        dialog.show()
    }
}