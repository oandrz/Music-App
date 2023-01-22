package com.dre.receipt_recognition

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.dre.core.extensions.collectAsStateWithLifecycle
import com.dre.core.extensions.collectWithLifecycle
import com.dre.core.navigation.NavigationManager
import com.dre.receipt_recognition.ReceiptRecognitionIntent.ImagePicked
import com.dre.receipt_recognition.ReceiptRecognitionIntent.NavigationBack
import com.huhx.picker.constant.AssetPickerConfig
import com.huhx.picker.data.AssetInfo
import com.huhx.picker.data.PickerPermissions
import com.huhx.picker.view.AssetPicker
import kotlinx.coroutines.flow.Flow

@Composable
fun ReceiptRecognitionGateway(
    viewModel: ReceiptRecognitionViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {
    HandleEvents(viewModel.event, navigationManager)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReceiptRecognitionScreen(
        uiState = uiState,
        onImagePicked = { viewModel.acceptIntent(ImagePicked(it)) },
        onCloseFromImagePicker = { viewModel.acceptIntent(NavigationBack) }
    )
}

@Composable
fun ReceiptRecognitionScreen(
    uiState: ReceiptRecognitionUiState,
    onImagePicked: (List<AssetInfo>) -> Unit,
    onCloseFromImagePicker: (List<AssetInfo>) -> Unit
) {
    ImagePickerScreen(onPicked = onImagePicked, onClose = onCloseFromImagePicker)
}

@Composable
fun ImagePickerScreen(onPicked: (List<AssetInfo>) -> Unit, onClose: (List<AssetInfo>) -> Unit) {
    PickerPermissions(permissions = listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
        AssetPicker(
            assetPickerConfig = AssetPickerConfig(gridCount = 3, maxAssets = 1),
            onPicked = onPicked,
            onClose = onClose
        )
    }
}

@Composable
private fun HandleEvents(events: Flow<ReceiptRecognitionEvent>, navigationManager: NavigationManager) {
    events.collectWithLifecycle {
        when (it) {
            is ReceiptRecognitionEvent.NavigationBack -> navigationManager.back()
        }
    }
}