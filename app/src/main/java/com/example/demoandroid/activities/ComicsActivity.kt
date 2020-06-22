package com.example.demoandroid.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoandroid.R
import com.example.demoandroid.adapters.ComicsAdapter
import com.example.demoandroid.data.api.ApiClient
import com.example.demoandroid.data.models.ComicResult
import com.example.demoandroid.data.models.ComicsResponse
import com.example.demoandroid.utils.Constants
import kotlinx.android.synthetic.main.activity_comics2.*
import kotlinx.coroutines.*

class ComicsActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val apiClient = ApiClient()
    private lateinit var adapter: ComicsAdapter
    private var myJob: Job? = null
    private var comicsList: List<ComicResult>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics2)
        linearLayoutManager = LinearLayoutManager(this)

        myJob = CoroutineScope(Dispatchers.IO).launch {
            val result = getComics()

            withContext(Dispatchers.Main) {
                drawComics(result?.comicsData?.comicResults)
            }
        }

    }

    private suspend fun getComics(): ComicsResponse? {
        return apiClient.getService(this).getComics(
            Constants.apikey,
            Constants.hash,
            Constants.ts
        )
    }

    fun drawComics(comicList: List<ComicResult>?) {
        comics_view.layoutManager = linearLayoutManager
        adapter = ComicsAdapter(comicList.orEmpty())
        comics_view.adapter = adapter
    }

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, ComicsActivity::class.java)
        }
    }
}