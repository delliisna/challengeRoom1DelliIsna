package com.example.challengeroom1delliisna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom1delliisna.room.Constant
import com.example.challengeroom1delliisna.room.dbsmksa
import com.example.challengeroom1delliisna.room.tbSiswa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val db by lazy { dbsmksa(this) }
    private lateinit var adapterActivity: AdapterActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        onStart()

    }

    override fun onStart() {
        super.onStart()
        loadSiswa()

    }
    private fun loadSiswa() {
        CoroutineScope(Dispatchers.IO).launch {
            val bisa = db.tbsiswaDao().getsiswaA()
            Log.d("MainActivity", "dbResponse:$bisa")
            withContext(Dispatchers.Main) {
                adapterActivity.setData(bisa)
            }
        }
    }
        fun createsiswa() {
        val change = Intent (this,ubahActivity::class.java)
        startActivity(change)
        intentEdit(0,Constant.TYPE_CREATE)
    }

    fun intentEdit(tbsiswaNis: Int,intentType: Int){
        startActivity(
            Intent(applicationContext,ubahActivity::class.java)
                .putExtra("intent_nis",tbsiswaNis)
                .putExtra("intent_nis",intentType)
        )
    }


    private fun  setupRecyclerView(){
        adapterActivity= AdapterActivity(arrayListOf(),object : AdapterActivity.OnAdapterListener {
            override fun OnClick(tbsiswa: tbSiswa) {
                intentEdit(tbsiswa.nis, Constant.TYPE_READ)
            }

            override fun OnUpdate(tbsiswa: tbSiswa) {
                intentEdit(tbsiswa.nis, Constant.TYPE_UPDATE)
            }

            override fun OnDelete(tbsiswa: tbSiswa) {
              deleteDialog(tbsiswa)
            }
            })

        list_siswa.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = adapterActivity
        }
    }

    private fun deleteDialog(tbsiswa:tbSiswa){
        val alertDialog= AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("konfirmasi")
            setMessage("Yakin hapus ${tbsiswa.nama}?")
            setNegativeButton("Batal"){ dialogInterface, i ->
             dialogInterface.dismiss()
            }

            setPositiveButton("Hapus"){ dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbsiswaDao().deletesiswa(tbsiswa)
                    loadSiswa()
                }
            }
        }
        alertDialog.show()
    }
}
