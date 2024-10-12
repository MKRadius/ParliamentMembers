/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * DataApi is a Retrofit interface that defines the endpoints for fetching
 * data related to parliament members. It contains two GET requests:
 *
 * 1. `getParliamentMembers()`: Fetches a list of `ParliamentMember`
 * objects from the "seating.json" endpoint.
 *
 * 2. `getParliamentMembersExtras()`: Retrieves a list of
 * `ParliamentMemberExtra` objects from the "extras.json" endpoint.
 *
 * This interface is utilized for network operations to retrieve data
 * from a remote API, enabling the application to present parliament
 * members and their associated information.
 */

package com.example.parliamentmembers.network

import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import retrofit2.http.GET

interface DataApi {
    @GET("seating.json")
    suspend fun getParliamentMembers(): List<ParliamentMember>

    @GET("extras.json")
    suspend fun getParliamentMembersExtras(): List<ParliamentMemberExtra>
}