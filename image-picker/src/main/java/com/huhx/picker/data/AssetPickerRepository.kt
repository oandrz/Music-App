package com.huhx.picker.data

import android.content.Context
import android.net.Uri

internal class AssetPickerRepository(
    private val context: Context
) {
    suspend fun getAssets(): List<AssetInfo> {
        return AssetLoader.load(context)
    }

    fun insertImage(): Uri? {
        return AssetLoader.insertImage(context)
    }

    fun findByUri(uri: Uri?): AssetInfo? {
        return uri?.let {
            AssetLoader.findByUri(context, it)
        }
    }

    fun deleteByUri(uri: Uri?) {
        uri?.let {
            AssetLoader.deleteByUri(context, it)
        }
    }
}