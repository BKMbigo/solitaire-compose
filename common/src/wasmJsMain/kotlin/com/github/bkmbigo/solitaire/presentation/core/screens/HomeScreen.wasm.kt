package com.github.bkmbigo.solitaire.presentation.core.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.SolitaireScreen

actual object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        HomeScreenContent(
            navigateToSolitaire = {
                navigator?.push(SolitaireScreen)
            }
        )
    }
}

@Composable
actual fun StartScreen() {
    Navigator(HomeScreen)
}
