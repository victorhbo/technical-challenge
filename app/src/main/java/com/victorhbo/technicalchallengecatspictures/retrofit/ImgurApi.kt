package com.victorhbo.technicalchallengecatspictures.retrofit

import com.victorhbo.technicalchallengecatspictures.models.ImageResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImgurApi {
    @Headers("Authorization: Client-ID 1ceddedc03a5d71")
    @GET("gallery/search/")
    suspend fun searchCats(@Query("q") query: String): ImageResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.imgur.com/3/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ImgurApi = retrofit.create(ImgurApi::class.java)
}