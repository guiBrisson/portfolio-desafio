package me.brisson.g1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import me.brisson.g1.navigation.G1NavHost
import me.brisson.g1.navigation.dependencies
import me.brisson.g1.ui.theme.G1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            G1Theme {
                G1NavHost(
                    modifier = Modifier.safeContentPadding(),
                    dependencies = dependencies(),
                )
            }
        }
    }
}
