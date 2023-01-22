package com.dre.receipt_recognition

import androidx.lifecycle.SavedStateHandle
import com.dre.core.base.BaseViewModel
import com.dre.receipt_recognition.ReceiptRecognitionUiState.PartialState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@HiltViewModel
class ReceiptRecognitionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initState: ReceiptRecognitionUiState
) : BaseViewModel<ReceiptRecognitionUiState, PartialState, ReceiptRecognitionEvent, ReceiptRecognitionIntent>(
  savedStateHandle, initState
) {
    override fun mapIntents(intent: ReceiptRecognitionIntent): Flow<PartialState> = when (intent) {
        is ReceiptRecognitionIntent.NavigationBack -> back()
        is ReceiptRecognitionIntent.ImagePicked -> processImage()
    }

    override fun reduceUiState(
        previousState: ReceiptRecognitionUiState,
        partialState: PartialState
    ): ReceiptRecognitionUiState = when (partialState) {
        is PartialState.CaptureImage -> previousState.copy("capture image")
    }

    private fun back(): Flow<PartialState> {
        publishEvent(ReceiptRecognitionEvent.NavigationBack)

        return emptyFlow()
    }

    private fun processImage(): Flow<PartialState> {
        return emptyFlow()
    }
}