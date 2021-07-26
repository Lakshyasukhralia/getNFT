package com.getnft.data.network

import com.getnft.data.model.response.NftResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BaseApiService {

    @GET("v2/nfts")
    suspend fun getNft(@QueryMap filters : Map<String,String>): Response<NftResponse>

}