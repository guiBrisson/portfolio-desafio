package me.brisson.g1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.brisson.g1.R
import me.brisson.g1.ui.theme.G1Theme
import me.brisson.g1.ui.theme.interFontFamily

@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier,
    title: String,
    error: String,
) {
    Column(
        modifier = modifier then Modifier
            .fillMaxWidth()
            .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF1F1F1)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
            )

            Text(text = title, fontFamily = interFontFamily, fontWeight = FontWeight.Medium)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.LightGray)
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = error,
            fontSize = 14.sp,
            fontFamily = interFontFamily
        )
    }
}

@Preview
@Composable
private fun PreviewErrorComponent() {
    G1Theme {
        ErrorComponent(
            modifier = Modifier,
            title = stringResource(R.string.error_while_fetching_feed_data),
            error = stringResource(R.string.preview_error_component_message)
        )
    }
}