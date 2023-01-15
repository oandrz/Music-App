package com.dre.receipt_recognition

import android.os.Parcelable
import javax.annotation.concurrent.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ReceiptRecognitionUiState(
    val name: String
) : Parcelable {

    sealed class PartialState {

    }
}