package com.getnft.data.model.response

data class NftResponse(
    val `data`: Data? = null,
    val status: String? = null
)

data class Data(
    var nfts: List<Nft>? = null,
    val stats: Stats? = null
)

data class Nft(
    val auctionCreatedAt: Int? = null,
    val backgroundColor: Any? = null,
    val coinrankingUrl: String? = null,
    val createdAt: Int? = null,
    val dappName: String? = null,
    val dappSlug: String? = null,
    val externalUrl: String? = null,
    val id: String? = null,
    val image: String? = null,
    val name: String? = null,
    val price: String? = null,
    val priceInDollar: String? = null,
    val registryId: String? = null,
    val tokenId: String? = null,
    val video: Any? = null
)

data class Stats(
    val firstSeen: Int,
    val total: Int
)