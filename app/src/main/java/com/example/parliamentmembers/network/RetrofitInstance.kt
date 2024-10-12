/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * RetrofitInstance is an object that provides a singleton instance of the Retrofit
 * library for making network requests. It defines the base URL for the API and
 * initializes the DataApi interface using lazy delegation.
 *
 * The Retrofit instance is configured with a Gson converter factory to handle
 * JSON serialization and deserialization. This object facilitates access to
 * the API endpoints defined in the DataApi interface, allowing the application
 * to fetch data related to parliament members efficiently.
 */

package com.example.parliamentmembers.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://users.metropolia.fi/~khaica/parliament_members/"

    val api: DataApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataApi::class.java)
    }
}