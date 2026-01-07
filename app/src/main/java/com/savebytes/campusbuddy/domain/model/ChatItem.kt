package com.savebytes.campusbuddy.domain.model

data class ChatItem(
    val id: String,
    val communityId: String,
    val communityName: String,
    val communityAvatar: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int
)