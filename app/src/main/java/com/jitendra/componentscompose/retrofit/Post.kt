package com.developersmarket.componentscompose.retrofit

import com.developersmarket.componentscompose.retrofit.ui.Product

data class Post(
        /* val id: Int,
         val body: String*/
       val limit: Int,
        val products: List<Product>,
        val skip: Int,
        val total: Int
)
