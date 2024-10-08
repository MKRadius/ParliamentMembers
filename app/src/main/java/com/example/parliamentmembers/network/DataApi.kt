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