package com.example.demoandroid.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoandroid.R
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.view.adapters.CharactersAdapter
import com.example.demoandroid.viewmodels.FavoriteCharactersViewModel
import kotlinx.android.synthetic.main.activity_comic.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteCharactersActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CharactersAdapter

    private val viewModel: FavoriteCharactersViewModel by viewModel()

    private val characterViewHolderListener =
        object : CharactersAdapter.CharacterViewHolderListener {
            override fun onCharacterItemClicked(characterResult: CharacterResult) {
                viewModel.delete(characterResult)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        linearLayoutManager = LinearLayoutManager(this)
        initViewModel()
        initRecycler()
    }

    private fun initRecycler() {
        characters_view.layoutManager = linearLayoutManager
        adapter = CharactersAdapter(characterViewHolderListener)
        characters_view.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.characters?.observe(this, Observer {
            it?.let {
                adapter.updateView(it)
            }
        })
        viewModel.getAll()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FavoriteCharactersActivity::class.java)
        }
    }
}