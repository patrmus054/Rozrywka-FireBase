package com.example.rozrywka_firebase.data.remote

import android.graphics.ColorSpace
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.rozrywka_firebase.data.Model
import com.example.rozrywka_firebase.ui.home.HomeActivity
import com.example.rozrywka_firebase.ui.home.HomeViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition

class RealtimeDatabase{
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("message")


    fun addItem(item: Model){
        myRef.child("test").child((HomeActivity.lastItemId+1).toString()).setValue(item)
    }

    fun updateIds(position: Int, lastItemId: Int){
        for(i in position until lastItemId){
            val item: Model = HomeActivity.homeViewModel.getModelById(position+1)
            myRef.child("test").child(position.toString()).setValue(Model(position.toLong(),item.title, item.date,item.kind,item.seen))
        }
        deleteAt(lastItemId)
    }

    fun deleteAt(id: Int){
        Log.d(TAG, "Deleted at: ${id.toString()}")
        myRef.child("test").child(id.toString()).setValue(null)
    }

}