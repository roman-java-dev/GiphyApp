package com.example.giphytasks

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.giphytasks.adapter.GifsAdapter
import com.example.giphytasks.const.Layout
import com.example.giphytasks.data.DataObject
import com.example.giphytasks.data.DataResult
import com.example.giphytasks.data.DataService
import com.example.giphytasks.databinding.ActivityGridListBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GridListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGridListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gifs = mutableListOf<DataObject>()

        val adapter = GifsAdapter(this, gifs, Layout.GRID)

        binding.gridRecyclerView.adapter = adapter

        binding.gridRecyclerView.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        adapter.setOnItemClickListener(object : GifsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@GridListActivity, SecondActivity::class.java)

                intent.putExtra("url", gifs[position].images.ogImage.url)
                startActivity(intent)
            }

        })

        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroService = retrofit.create(DataService::class.java)
        retroService.getGifs().enqueue(object : Callback<DataResult?> {
            override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>) {
                val body = response.body()
                if (body == null) {
                    Log.d(ContentValues.TAG, "onResponse: No response... ")
                }
                gifs.addAll(body!!.res)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<DataResult?>, t: Throwable) {
            }

        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()){
                    retroService.searchGifs(query).enqueue(object : Callback<DataResult?> {
                        override fun onResponse(
                            call: Call<DataResult?>,
                            response: Response<DataResult?>
                        ) {
                            val body = response.body()
                            if (body == null) {
                                Log.d(ContentValues.TAG, "onResponse: No response... ")
                            }
                            gifs.clear()
                            gifs.addAll(body!!.res)
                            if (gifs.isEmpty()){
                                Snackbar.make(
                                    binding.gridRecyclerView,
                                    "No image was found for your request",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            adapter.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                        }
                    }
                    )
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

    }
}