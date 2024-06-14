package com.example.aboolean


import android.content.res.Resources
import android.icu.text.MeasureFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.aboolean.databinding.TruthItemBinding

class truthTableAdapter: RecyclerView.Adapter<truthTableAdapter.truthTableHolder>() {
    val truthList=ArrayList<String>()

    var width:Int=0

    class truthTableHolder(item: View):RecyclerView.ViewHolder(item)
    {
        val bindingTruth=TruthItemBinding.bind(item)
        fun bind(elem: String)= with(bindingTruth) {
            value.text = elem

        }
        fun adapWidth(width: Int)= with(bindingTruth){
            val itemWidth=android.content.res.Resources.getSystem().getDisplayMetrics().widthPixels/(width+1)
            if(itemWidth>50){
                value.minWidth =itemWidth
            }
            else{
                value.minWidth=50
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): truthTableHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.truth_item,parent,false)

        return truthTableHolder(view)
    }

    override fun onBindViewHolder(holder: truthTableHolder, position: Int) {
        holder.bind(truthList[position])
        holder.adapWidth(width)

    }
    override fun getItemCount(): Int {
        return truthList.size
    }
    fun allRemove(){
        truthList.clear()
    }
    fun addTruth(elem: String){

        truthList.add(elem)
        notifyDataSetChanged()

    }

}