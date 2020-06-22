package com.example.demoandroid.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.R
import com.example.demoandroid.utils.inflate
import kotlinx.android.synthetic.main.list_view_row.view.*

class RecyclerAdapter(private val values: List<String>) :
    RecyclerView.Adapter<RecyclerAdapter.RowHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val inflatedView = parent.inflate(R.layout.list_view_row, false)
        return RowHolder(
            inflatedView
        )
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bindHolder(values[position])
    }

    class RowHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var value: String = ""

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(v?.context, "Hola $value", Toast.LENGTH_LONG).show()
        }

        fun bindHolder(value: String) {
            this.value = value
            view.firstLine.text = this.value
        }
    }
}