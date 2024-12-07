package me.brisson.g1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.brisson.g1.R
import me.brisson.g1.ui.theme.G1Theme

@Composable
fun G1AppBar(modifier: Modifier = Modifier) {
    AppBarComponent(modifier = modifier) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(R.drawable.ic_g1),
            contentDescription = stringResource(R.string.ic_g1_logo_description),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun WebAppBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    AppBarComponent(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun AppBarComponent(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier then Modifier
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .height(44.dp)
            .fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content,
    )
}

@Preview
@Composable
private fun PreviewG1AppBar() {
    G1Theme {
        G1AppBar()
    }
}

@Preview
@Composable
private fun PreviewWebAppBar() {
    G1Theme {
        WebAppBar(
            onBack = { },
        )
    }
}
