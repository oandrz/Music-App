package com.dre.receipt_recognition

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dre.core.navigation.NavigationDestination.ReceiptRecognition.route
import com.dre.core.navigation.NavigationFactory
import javax.inject.Inject

class ReceiptRecognitionNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(route) {
            ReceiptRecognitionGateway()
        }
    }
}