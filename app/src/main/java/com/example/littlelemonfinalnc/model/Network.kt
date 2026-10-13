package com.example.littlelemonfinalnc.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 1. Represents the entire JSON response
@Serializable
data class MenuNetworkData(
    val menu: List<MenuItemNetwork>
)

// 2. Represents an individual item from the internet
@Serializable
data class MenuItemNetwork(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("price") val price: String, // The internet sends price as a String
    @SerialName("description") val description: String,
    @SerialName("category") val category: String,
    @SerialName("image") val imageUrl: String
) {
    // 3. Helper function to convert this network item into a database item
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        price = price.toDouble(), // Convert String to Double for math/sorting later
        description = description,
        category = category,
        imageUrl = imageUrl
    )
}