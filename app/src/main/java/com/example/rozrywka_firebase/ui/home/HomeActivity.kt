package com.example.rozrywka_firebase.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.data.ItemTypes
import com.example.rozrywka_firebase.data.Model
import com.example.rozrywka_firebase.utility.SwipeToDeleteCallback
import com.example.rozrywka_firebase.utility.addFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_item_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*

class HomeActivity : AppCompatActivity() {

    lateinit var itemList: MutableList<Model>
    lateinit var homeAdapter: HomeAdapter

    companion object{
        lateinit var homeViewModel: HomeViewModel
        var lastItemId: Int = 0
    }
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = mutableListOf()
        homeAdapter = HomeAdapter(itemList, supportFragmentManager)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


        HomeViewModel.db.myRef.child("test").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("RealtimeDatabase", "Failed to read value", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
//                Log.v("JESTES","JESTEM")
                val itemList: MutableList<Model> =  mutableListOf<Model>()
                for((index, i) in p0.children.withIndex()){
                    val j = i.getValue(Model::class.java)
                    if (j != null) {
                        itemList.add(Model(index.toLong(), j.title, j.date, j.kind,j.seen))
                    }
                }
                homeViewModel.setItems(itemList)
                lastItemId = p0.childrenCount.toInt() - 1
//                val list = p0.children.map { it.getValue(Model::class.java)!! }
//                for((j, i) in list.withIndex()){
//                    HomeViewModel.db.addItem(Model(j.toLong(), i.title, i.date, i.kind,i.seen))
//                }

//                for ( i in itemList){
//                    HomeViewModel.db.addItem(i)
//                }
//                val list = p0.children.map { it.getValue(Model::class.java)!! }
//
//                homeViewModel.setItems(itemList)
                floating_action_button.visibility = View.VISIBLE
            }
        })

        floating_action_button.setOnClickListener {
            addFragment(R.id.edit_item_content_frame, ::EditItemFragment)
            floating_action_button.visibility = View.INVISIBLE
        }

        homeViewModel.item.observe(this, Observer {
            itemList = it as MutableList<Model>
            homeAdapter.setList(it)
        })

        rv_home.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = homeAdapter

        }


        val swipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapt = rv_home.adapter as HomeAdapter
                    HomeViewModel.db.deleteAt(viewHolder.adapterPosition)
                    HomeViewModel.db.updateIds(viewHolder.adapterPosition,lastItemId)
                    adapt.removeAt(viewHolder.adapterPosition)
                    Log.v("TAG", lastItemId.toString())
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv_home)

        fun addItem(item: Model){
            itemList.add(item)
        }
    }
}

