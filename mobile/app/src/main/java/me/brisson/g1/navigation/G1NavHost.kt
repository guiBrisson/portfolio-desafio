package me.brisson.g1.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.brisson.g1.screen.feed.FeedRouter
import me.brisson.g1.screen.web.WebRouter

@Composable
fun G1NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    dependencies: Dependencies,
) {
    NavHost(
        navController = navController,
        startDestination = FeedRoute,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable<FeedRoute> {
            FeedRouter(
                modifier = modifier,
                feedRepository = dependencies.feedRepository,
                onFeedItem = { url ->
                    navController.navigate(WebRoute(url))
                }
            )
        }

        composable<WebRoute> {
            WebRouter(
                modifier = modifier,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
