package com.victorhbo.technicalchallengecatspictures.retrofit.api

import com.victorhbo.technicalchallengecatspictures.models.ImageResponse
import com.victorhbo.technicalchallengecatspictures.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImgurApi {
    @Headers(Constants.RETROFIT.HEADERS)
    @GET(Constants.RETROFIT.GET_PARAMETER)
    suspend fun searchCats(
        @Query(Constants.RETROFIT.QUERY) query: String,
        @Query(Constants.RETROFIT.QUERY_PAGE) page: Int
    ): ImageResponse
}

object RetrofitInstance {
    private const val BASE_URL = Constants.RETROFIT.URL
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ImgurApi = retrofit.create(ImgurApi::class.java)
}