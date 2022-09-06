package com.example.test

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.RecyclerviewItemBinding

class StudentAdapter: RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem: ((StudentModel) -> Unit)? = null
    private var onClickDeleteItem: ((StudentModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(items: ArrayList<StudentModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (StudentModel) -> Unit){
        this.onClickDeleteItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener {onClickDeleteItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder(private var binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        var btnDelete = binding.btnDelete

        fun bindView(std: StudentModel) = with(binding){
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email
        }
    }
}