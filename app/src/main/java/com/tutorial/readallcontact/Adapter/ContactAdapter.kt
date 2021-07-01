package com.tutorial.readallcontact.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.readallcontact.Helper.DataClass
import com.tutorial.readallcontact.R

internal class ContactAdapter(private var moviesList: List<DataClass>) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txt_name: AppCompatTextView = view.findViewById(R.id.txt_name)
        var txt_number: AppCompatTextView = view.findViewById(R.id.txt_number)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_layout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataClass = moviesList[position]
        holder.txt_name.text = dataClass.name
        holder.txt_number.text = dataClass.phone
    }
    override fun getItemCount(): Int {
        return moviesList.size
    }
}