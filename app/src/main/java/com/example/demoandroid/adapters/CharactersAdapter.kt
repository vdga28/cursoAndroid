package com.example.demoandroid.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoandroid.R
import com.example.demoandroid.utils.inflate
import com.example.demoandroid.data.models.CharacterResult
import kotlinx.android.synthetic.main.list_view_row.view.*

class CharactersAdapter(private val values: List<CharacterResult>) :
    RecyclerView.Adapter<CharactersAdapter.RowHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersAdapter.RowHolder {
        val inflatedView = parent.inflate(R.layout.list_view_row, false)
        return RowHolder(inflatedView)
    }

    override fun getItemCount() = values.size

    class RowHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var value: CharacterResult? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(v?.context, "Hola $value", Toast.LENGTH_LONG).show()
        }

        fun bindHolder(value: CharacterResult) {
            this.value = value
            view.firstLine.text = this.value?.name
            Glide.with(view).load(value.thumbnail.path + "." + value.thumbnail.extension).into(view.icon)
        }
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bindHolder(values[position])
    }
}