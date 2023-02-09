package com.example.challengeroom1delliisna.room

import androidx.room.*

@Dao

interface tbSiswaDao {

    @Insert
      fun addsiswa (tbsiswa: tbSiswa)

    @Update
     fun updatesiswa (tbsiswa: tbSiswa)

    @Delete
     fun deletesiswa (tbsiswa: tbSiswa)

    @Query("SELECT * FROM  tbsiswa")
     fun getsiswaA():List<tbSiswa>

    @Query("SELECT * FROM  tbsiswa WHERE nis=:tbsiswa_nis")
    fun getsiswa(tbsiswa_nis:Int):List<tbSiswa>

}