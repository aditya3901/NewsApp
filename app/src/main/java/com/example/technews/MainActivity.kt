package com.example.technews

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {

    private lateinit var mAdapter: NewsAdapter
    private lateinit var list: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        list = ArrayList()
        mAdapter = NewsAdapter(list, this)
        recyclerView.adapter = mAdapter

        fetchData()
    }

    private fun fetchData() {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/science/in.json"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val array = response.getJSONArray("articles")
                for(i in 0 until array.length()){
                    val jsonObject: JSONObject = array.getJSONObject(i)
                    val news = News(
                        jsonObject.getString("title"),
                        jsonObject.getString("author"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage")
                    )
                    list.add(news)
                }
                mAdapter.notifyDataSetChanged()
                val loader = findViewById<ProgressBar>(R.id.loader)
                loader.visibility = View.GONE
            },
            { error ->
                Log.d("Error", error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }

    override fun onItemClick(position: Int) {
        val url = list[position].url
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}