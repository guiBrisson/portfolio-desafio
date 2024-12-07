package me.brisson.g1

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import me.brisson.g1.navigation.G1NavHost
import me.brisson.g1.navigation.dependencies
import me.brisson.g1.ui.theme.G1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(scrim = Color.BLACK),
        )

        setContent {
            G1Theme {
                G1NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    dependencies = dependencies(),
                )
            }
        }
    }
}
