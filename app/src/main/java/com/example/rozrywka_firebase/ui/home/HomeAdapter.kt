package com.example.rozrywka_firebase.ui.home

import android.content.ClipData
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Constraints
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.data.ItemTypes
import com.example.rozrywka_firebase.data.Model
import com.example.rozrywka_firebase.databinding.ListItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.list_item.view.*

class HomeAdapter (private val list: MutableList<Model>, val fm: FragmentManager):RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    companion object {
        val TAG: String = "HomeAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding = DataBindingUtil.inflate<ListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item, parent, false
        )

        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val model: Model = list[position]
        holder.bind(model, object : HomeListener{
            override fun onItemSelected(item: Model) {

                HomeViewModel.itemSelected = true
                EditItemFragment.editModel = HomeActivity.homeViewModel.getModelById(item.id.toInt())
//                fm.beginTransaction().add(R.id.edit_item_content_frame, EditItemFragment()).commit()
            }

        })
        holder.itemView.setOnClickListener {
        }
    }
    fun setList(newList: List<Model>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
    fun removeAt(position: Int) {
        Log.d(Constraints.TAG, "Added: ${position}")
        list.removeAt(position)
        notifyItemRemoved(position)
    }
    class HomeViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        private var title: TextView? = null
        private var date: TextView? = null

        init {
            title = itemView.findViewById(R.id.tv_title)
            date = itemView.findViewById(R.id.tv_data)
        }
        fun bind(model: Model, homeListener: HomeListener){
            with(binding){
                modelHome = model
                listener = homeListener
                title?.text = model.title
                date?.text = model.date
                when(model.kind){
                        ItemTypes.GAME -> imageView.setImageResource(R.drawable.game)
                        ItemTypes.BOOK ->imageView.setImageResource(R.drawable.book)
                        ItemTypes.SERIES -> imageView.setImageResource( R.drawable.series)
                        ItemTypes.MOVIE -> imageView.setImageResource(R.drawable.movie)
                    }
                when(model.seen){
                    true -> imgSeen.setImageResource(R.drawable.check)
                    false -> imgSeen.setImageResource(R.drawable.not_seen)
                }

            }
        }

    }

    interface HomeListener {
        fun onItemSelected(item: Model)
    }
}