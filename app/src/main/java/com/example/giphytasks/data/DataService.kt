package com.example.giphytasks.data

import retrofit2.http.GET

interface DataService {

    @GET("gifs/trending?api_key=XwJ651o6XPveOir4GFZRs9OukOsfpmeS")
    fun getGifs(): retrofit2.Call<DataResult>
}