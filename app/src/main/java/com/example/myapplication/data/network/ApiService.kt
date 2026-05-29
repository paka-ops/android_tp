package com.example.myapplication.data.network

import com.example.myapplication.data.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
// Débutant : singleton global mal fait
object RetrofitClient {
    private const val BASE_URL = "https://fakestoreapi.com/" // hardcodé
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product
    @GET("products/categories")
    suspend fun getCategories(): List<String>
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>
}