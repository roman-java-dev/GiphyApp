package com.example.giphytasks.data

import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {

    @GET("gifs/trending?api_key=XwJ651o6XPveOir4GFZRs9OukOsfpmeS")
    fun getGifs(): retrofit2.Call<DataResult>

    @GET("gifs/search?api_key=6uGTMfdu5AHnLHozRgBbldVaDJSwjfe8")
    fun searchGifs(@Query("q") query: String): retrofit2.Call<DataResult>
}