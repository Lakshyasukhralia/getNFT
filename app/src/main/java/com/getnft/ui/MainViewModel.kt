package com.getnft.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getnft.data.model.response.NftResponse
import com.getnft.data.repository.MainRepository
import com.getnft.data.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    companion object{
        private const val SORT_QUERY = "orderBy"
        private const val SORT_TYPE_1 = "priceInDollar"
        private const val SORT_TYPE_2 = "createdAt"
    }

    private val _nftStateFlow: MutableStateFlow<NetworkResult<NftResponse>> = MutableStateFlow(NetworkResult.Empty())
    val nftStateFlow: StateFlow<NetworkResult<NftResponse>> = _nftStateFlow

    fun getNftList(filters : Map<String,String> = mapOf(SORT_QUERY to SORT_TYPE_1)) = viewModelScope.launch {
        mainRepository.getNft(filters).collect { response -> _nftStateFlow.value = response }
    }

}
