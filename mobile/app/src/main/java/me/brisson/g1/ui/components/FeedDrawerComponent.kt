package me.brisson.g1.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.brisson.g1.R
import me.brisson.g1.screen.feed.FeedTab
import me.brisson.g1.ui.theme.interFontFamily


@Composable
fun FeedDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    selectedTab: FeedTab,
    onSelectTab: (FeedTab) -> Unit,
    onMenuItem: (url: String) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.statusBarsPadding()) {
                Spacer(modifier = Modifier.padding(top = 16.dp))

                FeedNavigationDrawerItem(
                    labelText = stringResource(R.string.g1_tab_title),
                    selected = selectedTab == FeedTab.G1_TAB,
                    onClick = { onSelectTab(FeedTab.G1_TAB) },
                )

                FeedNavigationDrawerItem(
                    labelText = stringResource(R.string.agro_tab_title),
                    selected = selectedTab == FeedTab.AGRO_TAB,
                    onClick = { onSelectTab(FeedTab.AGRO_TAB) },
                )

                Text(
                    modifier = Modifier.padding(16.dp).padding(top = 16.dp),
                    text = stringResource(R.string.menu_tab_title),
                    fontFamily = interFontFamily,
                )

                HorizontalDivider()

                setOf(
                    "agro" to "https://g1.globo.com/economia/agronegocios/",
                    "bem estar" to "https://g1.globo.com/bemestar/",
                    "carros" to "https://g1.globo.com/carros/",
                    "ciência e saúde" to "https://g1.globo.com/ciencia-e-saude/",
                    "economia" to "https://g1.globo.com/economia/",
                    "educação" to "https://g1.globo.com/educacao/",
                    "fato ou fake" to "https://g1.globo.com/fato-ou-fake/",
                ).forEach { (title, url) ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = title,
                                fontFamily = interFontFamily,
                            )
                        },
                        selected = false,
                        onClick = { onMenuItem(url) },
                    )
                }
            }
        },
        content = content,
    )
}

@Composable
fun FeedNavigationDrawerItem(
    modifier: Modifier = Modifier,
    labelText: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        modifier = modifier,
        label = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = labelText,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
            )
        },
        selected = selected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color.Transparent,
            selectedTextColor = MaterialTheme.colorScheme.primary,
        ),
    )
}
