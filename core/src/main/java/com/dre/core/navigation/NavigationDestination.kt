package com.dre.core.navigation

sealed class NavigationDestination(
    val route: String
) {
    object Rockets : NavigationDestination("rocketsDestination")
}
