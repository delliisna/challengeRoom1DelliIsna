package com.example.challengeroom1delliisna

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.challengeroom1delliisna.room.Constant
import com.example.challengeroom1delliisna.room.dbsmksa
import com.example.challengeroom1delliisna.room.tbSiswa
import kotlinx.android.synthetic.main.activity_ubah.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class ubahActivity : AppCompatActivity() {

    private val db by lazy { dbsmksa(this) }
    private var NisSiswa: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah)
        setupView()
        setupListener()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when(intent.getIntExtra("intent_type",0)){
            Constant.TYPE_CREATE ->{
                button_update.visibility=View.GONE
            }

            Constant.TYPE_READ->{
                save_button.visibility=View.GONE
                button_update.visibility=View.GONE
                getsiswa()
            }

            Constant.TYPE_UPDATE->{
                save_button.visibility=View.GONE
                getsiswa()
            }

            }
        }


    private fun  setupListener() {
        save_button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsiswaDao().addsiswa(
                    tbSiswa(0,edit_nama.text.toString(),
                        edit_kelas.text.toString(),edit_alamat.text.toString())
                )
                finish()
            }
        }

        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsiswaDao().updatesiswa(
                    tbSiswa(NisSiswa,edit_nama.text.toString(),
                        edit_kelas.text.toString(),edit_alamat.text.toString())
                )
                finish()
            }
        }

    }

    private fun getsiswa(){
    NisSiswa= intent.getIntExtra("intent_nis",0)
    CoroutineScope(Dispatchers.IO).launch {
       val siswa = db.tbsiswaDao().getsiswa( NisSiswa )[0]
        edit_nama.setText(siswa.nama)
        edit_kelas.setText(siswa.kelas)
        edit_alamat.setText(siswa.alamat)
    }
}

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}