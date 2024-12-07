package me.brisson.g1.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.brisson.g1.ui.utils.shimmerEffect

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier then Modifier
            .clip(RoundedCornerShape(4.dp))
            .shimmerEffect(),
    )
}
