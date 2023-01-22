package com.dre.receipt_recognition

sealed class ReceiptRecognitionEvent {
    object NavigationBack : ReceiptRecognitionEvent()
}