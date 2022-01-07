package com.jgiovannysn.movies.data.entities

data class NotificationContent(
    val channel: String,
    val title: String,
    val text: String,
    val icon: Int
)