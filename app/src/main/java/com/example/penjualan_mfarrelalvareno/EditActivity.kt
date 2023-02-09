package com.example.penjualan_mfarrelalvareno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.penjualan_mfarrelalvareno.Room.Constant
import com.example.penjualan_mfarrelalvareno.Room.dbpenjualan
import com.example.penjualan_mfarrelalvareno.Room.tbbarang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private val db by lazy {dbpenjualan(this) }
    private var barangId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupLstener()
        setupView()
        barangId = intent.getIntExtra("intent_id",0)
    }
    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intenType = intent.getIntExtra("intent_type",0)
        when (intenType){
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_simpan.visibility = View.GONE
                btn_update.visibility = View.GONE
                getbarang()
            }
            Constant.TYPE_UPDATE -> {
                btn_simpan.visibility = View.GONE
                getbarang()
            }

        }
    }
    fun setupLstener() {
        btn_simpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbarDao().addtbbarang(
                    tbbarang(
                        ETid.text.toString().toInt(),
                        ETnamabarang.text.toString(),
                        EThargabarang.text.toString().toInt(),
                        ETjumlahbarang.text.toString().toInt()
                    )
                )
                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbarDao().updatetbbarang(
                    tbbarang(
                        barangId,
                        ETnamabarang.text.toString(),
                        EThargabarang.text.toString().toInt(),
                        ETjumlahbarang.text.toString().toInt()
                    )
                )
                finish()
            }
        }
    }

    fun getbarang(){
        barangId = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val barangs = db.tbbarDao().tampilsemuah(barangId)[0]
            val id  : String = barangs.id_brg.toString()
            val harga : String = barangs.hrg_brg.toString()
            val jumlah   : String = barangs.jml_brg.toString()
            ETid.setText(id)
            ETnamabarang.setText(barangs.nm_brg)
            EThargabarang.setText(harga)
            ETjumlahbarang.setText(jumlah)

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}