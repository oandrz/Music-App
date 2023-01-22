package com.dre.core.navigation

sealed class NavigationDestination(
    val route: String
) {
    object Rockets : NavigationDestination("rocketsDestination")
    object ReceiptRecognition : NavigationDestination("receiptRecognition")

    object Back : NavigationDestination("navigationBack")
}
