package com.getnft.data.network

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val baseApiService: BaseApiService) {
    suspend fun getNft(filters : Map<String,String>) = baseApiService.getNft(filters)
}