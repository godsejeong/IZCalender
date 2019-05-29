package com.jjmin.izcalendar.adapter

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjmin.izcalendar.R
import com.jjmin.izcalendar.data.DetailPlanItem
import com.jjmin.izcalendar.data.ListDataInterface
import com.jjmin.izcalendar.data.PlanningItem
import com.jjmin.izcalendar.data.TodayItem
import com.jjmin.izcalendar.ui.detailplan.DetailViewModel
import com.jjmin.izcalendar.databinding.ItemDetailBinding
import com.jjmin.izcalendar.databinding.ItemPlanningBinding
import com.jjmin.izcalendar.databinding.ItemPlanningTodayBinding
import com.jjmin.izcalendar.ui.detailplan.DetailPlanActivity
import kotlin.reflect.KType


class ItemListAdapter(private val vm: ViewModel) :
    ListAdapter<ListDataInterface, RecyclerView.ViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            1 -> {
                MainViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_planning,
                        parent,
                        false
                    )
                )
            }
            2->{
                TodayViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_planning_today,
                        parent,
                        false
                    )
                )
            }

            3 -> {
                DetailViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_detail,
                        parent,
                        false
                    )
                )
            }

            else -> DetailViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_detail,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            1 -> {
                val item = getItem(position) as PlanningItem
                (holder as MainViewHolder).binding.item = item
                holder.itemView.setOnClickListener {

                    var pos: Int?
                    var namelist = ArrayList<String>()
                    var subtitleList = ArrayList<String>()
                    var day = holder.binding.item!!.day

//                    pos = getTopPosition(day!!,itemCount,item)
                    (0 until itemCount).forEach { position ->
                        var _item = getItem(position) as PlanningItem
                        if(_item.day == day){
                            Log.e("title",_item.title!!)
                            Log.e("subtitle",_item.subtitle!!)
                            namelist.add(_item.title!!)
                            subtitleList.add(_item.subtitle!!)
                        }
                    }

//                    Log.e("pos", pos.toString())
                    var intent = Intent(holder.itemView.context, DetailPlanActivity::class.java)
                    intent.putExtra("position",item.day)
                    intent.putExtra("date",item.day)
                    intent.putExtra("dow",item.dow)
                    intent.putExtra("title",namelist)
                    intent.putExtra("subtitle",subtitleList)
                    holder.itemView.context.startActivity(intent)
                }
            }
            2->{
                val item = getItem(position) as TodayItem
                (holder as TodayViewHolder).binding.item = item
            }
            3 -> {
                val item = getItem(position) as DetailPlanItem
                (holder as DetailViewHolder).binding.item = item
            }
        }
    }

    fun getTopPosition(day: String,size : Int,item : PlanningItem): Int {
        var pos = 0
        var _item : PlanningItem
        (0 until size).forEach {
            _item = getItem(it) as PlanningItem
            if (_item.day == day) {
                pos = it
                return@forEach
            }
        }
        return pos
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position).hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getitem()
    }

    class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: ItemDetailBinding = DataBindingUtil.bind(view)!!
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: ItemPlanningBinding = DataBindingUtil.bind(view)!!
    }

    class TodayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: ItemPlanningTodayBinding = DataBindingUtil.bind(view)!!
    }

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<ListDataInterface>() {
            override fun areItemsTheSame(oldItem: ListDataInterface, newItem: ListDataInterface): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListDataInterface, newItem: ListDataInterface): Boolean {
                return oldItem == newItem
            }
        }
    }
}