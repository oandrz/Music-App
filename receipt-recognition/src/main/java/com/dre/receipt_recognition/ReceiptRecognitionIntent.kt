package com.dre.receipt_recognition

import com.huhx.picker.data.AssetInfo

sealed class ReceiptRecognitionIntent {
    object NavigationBack: ReceiptRecognitionIntent()
    class ImagePicked(val imageList: List<AssetInfo>): ReceiptRecognitionIntent()
}