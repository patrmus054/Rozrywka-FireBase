package com.example.rozrywka_firebase.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rozrywka_firebase.data.ItemTypes
import com.example.rozrywka_firebase.data.Model
import com.example.rozrywka_firebase.data.remote.RealtimeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.FieldPosition
import javax.inject.Inject

class HomeViewModel @Inject constructor(): ViewModel() {
    private val _item: MutableLiveData<List<Model>> = MutableLiveData()
    val item: LiveData<List<Model>> get() = _item



    companion object{
        val db: RealtimeDatabase = RealtimeDatabase()
        var currentUser: String = ""
        var count: Long = 0
        var itemSeen: Boolean = false
        var itemType: ItemTypes = ItemTypes.GAME
        var itemSelected: Boolean = false

    }

    fun setItems(itemList: List<Model>){
        _item.value = itemList
    }
    fun addItem(item: Model) {
        db.addItem(item)
    }
    fun getModelById(position: Int): Model{
        return _item.value!![position]
    }

    fun converter(str: String): List<Model>{
        var list: List<Model> = emptyList()
        for (i in 1..str.length-2) {

            println(str[i])
        }
        return list
    }


}
