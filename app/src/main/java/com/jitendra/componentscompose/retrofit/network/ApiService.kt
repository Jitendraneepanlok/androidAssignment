package com.developersmarket.componentscompose.retrofit.network

import com.developersmarket.componentscompose.retrofit.Post
import com.jitendra.componentscompose.retrofit.ui.ProductDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }

    @GET("products")
    suspend fun getPosts(): Post


    @GET("products")
    suspend fun getProductData(@Query("id") id : String): ProductDetails

}