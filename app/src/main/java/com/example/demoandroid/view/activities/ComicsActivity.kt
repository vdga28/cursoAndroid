package com.example.demoandroid.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoandroid.R
import com.example.demoandroid.view.adapters.ComicsAdapter
import com.example.demoandroid.viewmodels.ComicsViewModel
import kotlinx.android.synthetic.main.activity_comics2.*
import org.koin.android.viewmodel.ext.android.viewModel

class ComicsActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ComicsAdapter

    private val viewModel: ComicsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics2)
        linearLayoutManager = LinearLayoutManager(this)
        initRecycle()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.comicsList.observe(this, Observer {
            it?.let { comics -> adapter.refreshItems(comics) }
        })
        viewModel.getComics()
    }


    private fun initRecycle() {
        comics_view.layoutManager = linearLayoutManager
        adapter = ComicsAdapter()
        comics_view.adapter = adapter
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ComicsActivity::class.java)
        }
    }
}