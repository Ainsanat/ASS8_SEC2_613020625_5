package com.example.assignment8

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomersAdapter(val item: List<Customer>, val context: Context) : RecyclerView.Adapter<VHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view_item = LayoutInflater.from(parent.context).inflate(R.layout.customer_item_layout, parent, false)
        return VHolder(view_item)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.tvName.text = item[position].name
        holder.tvGender.text = item[position].gender
        holder.tvEmail.text = item[position].email
        holder.tvSalary.text = item[position].salary
    }

    override fun getItemCount(): Int = item.size

}