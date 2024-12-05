package me.brisson.g1.navigation

import me.brisson.g1.core.data.repository.FeedRepository
import me.brisson.g1.core.data.repository.feedRepository

data class Dependencies(
    val feedRepository: FeedRepository,
)

fun dependencies(): Dependencies = Dependencies(
    feedRepository = feedRepository()
)
