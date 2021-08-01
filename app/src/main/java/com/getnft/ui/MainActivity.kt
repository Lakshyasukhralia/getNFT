package com.getnft.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getnft.data.util.NetworkResult
import com.getnft.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CheckoutListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val nftAdapter = NftAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleUI()
        viewModel.getNftList()
        handleNetworkResponse()
    }

    private fun handleNetworkResponse() {

        lifecycleScope.launchWhenStarted {
            viewModel.nftStateFlow.collect {
                when (it) {
                    is NetworkResult.Loading -> {
                    }
                    is NetworkResult.Success -> {
                        it.data?.data?.nfts?.let { list -> nftAdapter.submitData(list) }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun handleUI() {

        with(binding.rvNft){
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            adapter = nftAdapter
        }
        binding.rvNft.adapter = nftAdapter
    }

    override fun onCheckoutClick(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

}