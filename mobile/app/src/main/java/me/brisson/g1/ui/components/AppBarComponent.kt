package me.brisson.g1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.brisson.g1.R
import me.brisson.g1.screen.feed.FeedTab
import me.brisson.g1.ui.theme.G1Theme
import me.brisson.g1.ui.theme.interFontFamily

@Composable
fun FeedAppBar(
    modifier: Modifier = Modifier,
    tab: FeedTab,
    onMenu: () -> Unit,
) {
    AppBarComponent(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(onClick = onMenu) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.ic_menu_description),
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

        val tabName: String = when (tab) {
            FeedTab.G1_TAB -> stringResource(R.string.g1_tab_title)
            FeedTab.AGRO_TAB -> stringResource(R.string.agro_tab_title)
        }

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = tabName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
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
                contentDescription = stringResource(R.string.ic_arrow_back_description),
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
private fun PreviewWebAppBar() {
    G1Theme {
        WebAppBar(
            onBack = { },
        )
    }
}

@Preview
@Composable
private fun PreviewG1AppBar() {
    G1Theme {
        FeedAppBar(
            tab = FeedTab.AGRO_TAB,
            onMenu = { },
        )
    }
}
