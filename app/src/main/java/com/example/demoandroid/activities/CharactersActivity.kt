package com.example.demoandroid.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoandroid.utils.Constants
import com.example.demoandroid.R
import com.example.demoandroid.adapters.CharactersAdapter
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.models.CharactersResponse
import com.example.demoandroid.data.api.ApiClient
import com.example.demoandroid.data.models.ComicsResponse
import kotlinx.android.synthetic.main.activity_comic.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CharactersAdapter
    private var characters: List<CharacterResult>? = null
    private val apiClient = ApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        linearLayoutManager = LinearLayoutManager(this)
        getComicList()
    }


    private fun drawComicList(characterList: List<CharacterResult>?) {
        characters_view.layoutManager = linearLayoutManager
        adapter = CharactersAdapter(characterList.orEmpty())
        characters_view.adapter = adapter
    }

    private fun getComicList() {

        apiClient.getService(this)
            .getCharacters(
                Constants.apikey,
                Constants.hash,
                Constants.ts
            ).enqueue(object : Callback<CharactersResponse?> {
                override fun onFailure(call: Call<CharactersResponse?>, t: Throwable) {
                    //Mostrar Dialogo de error
                }

                override fun onResponse(
                    call: Call<CharactersResponse?>,
                    response: Response<CharactersResponse?>
                ) {
                    characters = response.body()?.charactersData?.characterResults
                    drawComicList(characters)
                }

            })
    }

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, CharactersActivity::class.java)
        }
    }
}