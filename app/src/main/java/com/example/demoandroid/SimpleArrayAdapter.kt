package com.example.demoandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class SimpleArrayAdapter(
    context: Context,
    private val values: Array<String>
) : ArrayAdapter<String?>(context, -1, values) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.list_view_row, parent, false)
        val textView = rowView.findViewById<View>(R.id.firstLine) as TextView
        val imageView =
            rowView.findViewById<View>(R.id.icon) as ImageView
        textView.text = values[position]
        // change the icon for Windows and iPhone
        val s = values[position]
        if (s.startsWith("iPhone")) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
        }
        return rowView
    }

}