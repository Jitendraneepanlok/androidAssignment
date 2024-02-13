package com.developersmarket.componentscompose.retrofit

import com.developersmarket.componentscompose.retrofit.network.ApiService
import com.jitendra.componentscompose.retrofit.ui.ProductDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiService: ApiService){
    fun getPost(): Flow<Post> = flow {
        emit(apiService.getPosts())
    }.flowOn(Dispatchers.IO)


    fun getProductdeatils(): Flow<ProductDetails> = flow {
        emit(apiService.getProductData("1"))
    }.flowOn(Dispatchers.IO)
}