package me.brisson.g1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.brisson.g1.R
import me.brisson.g1.ui.theme.G1Theme

@Composable
fun AppBarComponent(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier then Modifier
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 8.dp)
                .size(24.dp),
            painter = painterResource(R.drawable.ic_g1),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewAppBarComponent() {
    G1Theme {
        Column(modifier = Modifier.fillMaxSize()) {
            AppBarComponent()
        }
    }
}