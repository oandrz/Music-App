package com.dre.money.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dre.core.navigation.NavigationDestination
import com.dre.core.navigation.NavigationFactory

@Composable
fun NavigationHost(
    navController: NavHostController,
    factories: Set<NavigationFactory>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Rockets.route,
        modifier = modifier
    ) {
        factories.forEach {
            it.create(this)
        }
    }
}
