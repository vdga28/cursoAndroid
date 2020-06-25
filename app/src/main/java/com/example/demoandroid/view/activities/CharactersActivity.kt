package com.example.demoandroid.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoandroid.R
import com.example.demoandroid.view.adapters.CharactersAdapter
import com.example.demoandroid.viewmodels.CharactersViewModel
import kotlinx.android.synthetic.main.activity_comic.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CharactersAdapter

    private val viewModel: CharactersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        linearLayoutManager = LinearLayoutManager(this)
        initViewModel()
        initRecycler()
    }

    private fun initRecycler() {
        characters_view.layoutManager = linearLayoutManager
        adapter = CharactersAdapter()
        characters_view.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.characters.observe(this, Observer {
            adapter.updateView(it)
        })
        viewModel.getComicList()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CharactersActivity::class.java)
        }
    }
}