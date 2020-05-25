package com.example.rozrywka_firebase.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.data.ItemTypes
import com.example.rozrywka_firebase.data.Model
import com.example.rozrywka_firebase.ui.home.HomeActivity.Companion.homeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_item_fragment.*
import kotlinx.android.synthetic.main.edit_item_fragment.view.*

class EditItemFragment : Fragment() {

//    var editTextTitle: EditText = EditText(Activity())
//    var editTextDate: EditText = EditText(Activity())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.edit_item_fragment, container, false)
        if(HomeViewModel.itemSelected){
            et_title.setText(editModel.title)
            et_date.setText(editModel.date)

        }
//        editTextTitle = root.et_title
//        editTextDate = root.et_date
        return root
    }
    companion object{
        var editModel: Model = Model()
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_submit.setOnClickListener{
//            if(HomeViewModel.itemSelected){
//                if(et_title.text.toString() != "" && et_date.text.toString() != ""){
//                    val item: Model = Model(editModel.id,et_title.text.toString(), et_date.text.toString(),
//                        HomeViewModel.itemType, HomeViewModel.itemSeen)
//
//                    homeViewModel.addItem(item)
//                    HomeViewModel.db.addItem(item)
//                    HomeViewModel.itemSelected = false
//                    fragmentManager?.beginTransaction()?.remove(this)?.commit()
//                }else{
//                    Toast.makeText(requireContext(), "Unable to create Item", Toast.LENGTH_SHORT).show()
//                }
//            }
            if(et_title.text.toString() != "" && et_date.text.toString() != ""){
                val item: Model = Model(HomeViewModel.count,et_title.text.toString(), et_date.text.toString(),
                    HomeViewModel.itemType, HomeViewModel.itemSeen)

                homeViewModel.addItem(item)
                HomeViewModel.db.addItem(item)
                HomeViewModel.count++
                HomeViewModel.itemSelected = false
                fragmentManager?.beginTransaction()?.remove(this)?.commit()
            }else{
                Toast.makeText(requireContext(), "Unable to create Item", Toast.LENGTH_SHORT).show()
            }
        }
        bt_cancel.setOnClickListener{
            fragmentManager?.beginTransaction()?.remove(this)?.commit()

        }
        val options = arrayOf(
            ItemTypes.BOOK,
            ItemTypes.GAME, ItemTypes.MOVIE, ItemTypes.SERIES)
        //val spinner = findViewById<Spinner>(R.id.spinner)
        val arrayAdapter =
            context?.let { ArrayAdapter.createFromResource(it, R.array.rgb_mode_options, R.layout.spinner_item) }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = arrayAdapter
//        spinner.adapter = ArrayAdapter<ItemTypes>(this, R.layout.spinner_item, options)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(activity, "Please Select an Option",Toast.LENGTH_SHORT).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                HomeViewModel.itemType = options[position]
            }

        }
        switch_seen.setOnCheckedChangeListener{ _ : CompoundButton, b: Boolean ->
            Log.v("changed", if(b){"zmiana"}else{"nic"} )
            HomeViewModel.itemSeen = b
        }
//        if(HomeViewModel.itemSelected){
//            editTextTitle.setText(editModel.title)
//            editTextDate.setText(editModel.date)
//        }
    }

}