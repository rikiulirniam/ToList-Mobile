package com.example.tolist_final.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tolist_final.R
import com.example.tolist_final.data_class.Label
import com.example.tolist_final.TaskDetailActivity

class LabelAdapter(private var labels: List<Label>) :
    RecyclerView.Adapter<LabelAdapter.LabelViewHolder>() {

    class LabelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val labelName: TextView = view.findViewById(R.id.label_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return LabelViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        val label = labels[position]
        holder.labelName.text = label.name

        // Tambahkan listener untuk item klik
        holder.itemView.setOnClickListener { view ->
            // Intent untuk berpindah ke ActivityTaskDetail
            val context = view.context
            val intent = Intent(context, TaskDetailActivity::class.java)

            // Kirim data (opsional)
            intent.putExtra("LABEL_NAME", label.name)

            context.startActivity(intent)
        }
    }

    override fun getItemCount() = labels.size

    fun updateData(newLabels: List<Label>) {
        labels = newLabels
        notifyDataSetChanged()
    }
}
