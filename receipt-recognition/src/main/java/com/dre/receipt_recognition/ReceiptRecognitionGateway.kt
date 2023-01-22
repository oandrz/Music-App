package com.dre.receipt_recognition

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dre.core.navigation.NavigationManager
import com.huhx.picker.constant.AssetPickerConfig
import com.huhx.picker.data.PickerPermissions
import com.huhx.picker.view.AssetPicker

@Composable
fun ReceiptRecognitionGateway(
    viewModel: ReceiptRecognitionViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {
    PickerPermissions(permissions = listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
        AssetPicker(
            assetPickerConfig = AssetPickerConfig(gridCount = 3, maxAssets = 1),
            onPicked = { },
            onClose = { navigationManager.back() }
        )
    }
}