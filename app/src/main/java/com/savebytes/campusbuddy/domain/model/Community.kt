package com.savebytes.campusbuddy.domain.model

// Data Classes
data class Community(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val avatarUrl: String,
    val tags: List<String>,
    val memberCount: Int,
    val isJoined: Boolean
)

// Sealed class for different view types
sealed class CommunityItem {
    data class Header(val title: String) : CommunityItem()
    data class SearchBar(val query: String = "") : CommunityItem()
    data class CommunityCard(val community: Community) : CommunityItem()
}