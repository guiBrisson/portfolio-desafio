package me.brisson.g1.navigation

import kotlinx.serialization.Serializable

@Serializable
data object FeedRoute

@Serializable
data class WebRoute(val url: String)
