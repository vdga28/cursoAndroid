package com.example.demoandroid.view.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoandroid.R
import com.example.demoandroid.utils.inflate
import com.example.demoandroid.data.models.ComicResult
import kotlinx.android.synthetic.main.list_view_row.view.*

class ComicsAdapter() :
    RecyclerView.Adapter<ComicsAdapter.RowHolder>() {

    private val values: MutableList<ComicResult> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsAdapter.RowHolder {
        val inflatedView = parent.inflate(R.layout.list_view_row, false)
        return RowHolder(inflatedView)
    }

    fun refreshItems(comics: List<ComicResult>) {
        values.addAll(comics)
        notifyDataSetChanged()
    }

    override fun getItemCount() = values.size

    class RowHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var value: ComicResult? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(v?.context, "Hola $value", Toast.LENGTH_LONG).show()
        }

        fun bindHolder(value: ComicResult) {
            this.value = value
            view.firstLine.text = this.value?.title
            view.secondLine.text = this.value?.variantDescription
            Glide.with(view).load(value.thumbnail.path + "." + value.thumbnail.extension).into(view.icon)
        }
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bindHolder(values[position])
    }
}