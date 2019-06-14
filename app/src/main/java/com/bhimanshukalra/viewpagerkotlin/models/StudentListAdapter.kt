package com.bhimanshukalra.viewpagerkotlin.models

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bhimanshukalra.viewpagerkotlin.R

class StudentListAdapter(
    private var mStudentList: List<Student>,
    private var mClickListener: RecyclerClickListener
) : RecyclerView.Adapter<StudentListAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val cardView: CardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false) as CardView
        return RecyclerViewHolder(cardView)
    }

    override fun getItemCount(): Int {
        return mStudentList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.tvName.text = mStudentList.get(position).name
        holder.tvClass.text = mStudentList.get(position).className
        holder.tvRollNumber.text = mStudentList.get(position).rollNumber.toString()
    }

    inner class RecyclerViewHolder(val cardView: CardView) :
        RecyclerView.ViewHolder(cardView) {
        var tvName: TextView = cardView.findViewById(R.id.item_student_tv_name)
        var tvClass: TextView = cardView.findViewById(R.id.item_student_tv_class)
        var tvRollNumber: TextView = cardView.findViewById(R.id.item_student_tv_roll_num)

        init {
            cardView.setOnClickListener {
                mClickListener.listItemClicked(adapterPosition)
            }
        }
    }

    interface RecyclerClickListener {
        fun listItemClicked(position: Int)
    }
}




















