package com.victorhbo.technicalchallengecatspictures.retrofit.api

import com.victorhbo.technicalchallengecatspictures.models.ImageResponse
import com.victorhbo.technicalchallengecatspictures.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImgurApi {
    @Headers(Constants.HEADERS)
    @GET(Constants.GET_PARAMETER)
    suspend fun searchCats(@Query(Constants.QUERY) query: String): ImageResponse
}

object RetrofitInstance {
    private const val BASE_URL = Constants.URL
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ImgurApi = retrofit.create(ImgurApi::class.java)
}