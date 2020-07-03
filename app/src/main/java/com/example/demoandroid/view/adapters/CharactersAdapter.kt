package com.example.demoandroid.view.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoandroid.R
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.persistence.databases.CharacterRepository
import com.example.demoandroid.utils.inflate
import kotlinx.android.synthetic.main.list_view_row.view.*
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

class CharactersAdapter(private val characterViewHolderListener: CharacterViewHolderListener? = null) :
    RecyclerView.Adapter<CharactersAdapter.RowHolder>(), KoinComponent {

    private val values: MutableList<CharacterResult> = mutableListOf()

    private val characterRepository: CharacterRepository by inject()

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersAdapter.RowHolder {
        val inflatedView = parent.inflate(R.layout.list_view_row, false)
        return RowHolder(inflatedView)
    }

    fun updateView(values: List<CharacterResult>) {
        this.values.clear()
        this.values.addAll(values)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        val character = values[position]

        checkIsPresent(character.id) {
            if (it) {
                holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        android.R.color.holo_blue_light
                    )
                )
                holder.itemView.setOnClickListener(null)

            } else {
                holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        android.R.color.white
                    )
                )
                holder.itemView.setOnClickListener {
                    characterViewHolderListener?.onCharacterItemClicked(character)
                }
            }
        }

    }

    private fun checkIsPresent(id: Int, check : (character: Boolean)-> Unit) =
        CoroutineScope(Dispatchers.IO).launch {
        val character = characterRepository.getById(id)
        withContext(Dispatchers.Main) {
            check.invoke(character)
        }
    }

    override fun getItemCount() = values.size

    interface CharacterViewHolderListener {
        fun onCharacterItemClicked(characterResult: CharacterResult)
    }

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
            Glide.with(view).load(value.thumbnail.path + "." + value.thumbnail.extension)
                .into(view.icon)
        }
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bindHolder(values[position])
    }
}