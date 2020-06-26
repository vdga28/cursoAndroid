package com.example.demoandroid.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demoandroid.R
import com.example.demoandroid.view.adapters.SimpleArrayAdapter
import kotlinx.android.synthetic.main.activity_list_view.*


class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        val values = mutableListOf(
            "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Android", "iPhone", "WindowsMobile"
        )

       /* val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, values
        ) */

        val adapter = SimpleArrayAdapter(
            this, values.toTypedArray()
        )

        list_view.adapter = adapter
        list_view.setOnItemClickListener { parent, view, position, id ->
            view.animate().setDuration(2000).alpha(0F)
                .withEndAction {
                    values.removeAt(position)
                    adapter.notifyDataSetChanged()
                    view.setAlpha(1F)
                }
        }
    }

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, ListViewActivity::class.java)
        }
    }
}