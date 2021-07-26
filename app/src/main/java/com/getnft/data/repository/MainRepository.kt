package com.getnft.data.repository

import com.getnft.data.model.response.NftResponse
import com.getnft.data.network.RemoteDataSource
import com.getnft.data.util.BaseApiResponse
import com.getnft.data.util.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityRetainedScoped
class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getNft(filters: Map<String, String>): Flow<NetworkResult<NftResponse>> {
        return flow<NetworkResult<NftResponse>> {
            emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.getNft(filters) })
        }.flowOn(Dispatchers.IO).map {
           return@map when (it) {
                is NetworkResult.Success -> {
                    it.data?.data?.nfts = validateNftList(it)
                    it
                }
                else -> it
            }

        }
    }

    private fun validateNftList(
        result: NetworkResult<NftResponse>
    ) = result.data?.data?.nfts?.filter { nft ->
                 nft.image != null &&
                !nft.image.endsWith(".svg") &&
                !nft.image.endsWith(".txt") &&
                !nft.name.isNullOrEmpty()
    }

}