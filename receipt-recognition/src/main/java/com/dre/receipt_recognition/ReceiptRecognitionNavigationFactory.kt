package com.dre.receipt_recognition

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dre.core.navigation.NavigationDestination.ReceiptRecognition.route
import com.dre.core.navigation.NavigationFactory
import com.dre.core.navigation.NavigationManager
import javax.inject.Inject

class ReceiptRecognitionNavigationFactory @Inject constructor(
    private val navigationManager: NavigationManager
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(route) {
            ReceiptRecognitionGateway(navigationManager = navigationManager)
        }
    }
}