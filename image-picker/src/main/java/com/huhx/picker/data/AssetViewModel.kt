package com.huhx.picker.data

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

internal class AssetViewModel(
    private val assetPickerRepository: AssetPickerRepository,
    private val navController: NavController,
) : ViewModel() {

    private val assets = mutableStateListOf<AssetInfo>()
    private val _directoryGroup = mutableStateListOf<AssetDirectory>()

    val directoryGroup: List<AssetDirectory>
        get() = _directoryGroup

    val selectedList = mutableStateListOf<AssetInfo>()
    var directory by mutableStateOf("Photos")

    fun initDirectories() {
        viewModelScope.launch {
            initAssets()
            val directoryList = assets.groupBy {
                it.directory
            }.map {
                AssetDirectory(directory = it.key, assets = it.value)
            }
            _directoryGroup.clear()
            _directoryGroup.add(AssetDirectory(directory = "Photos", assets = assets))
            _directoryGroup.addAll(directoryList)
        }
    }

    private suspend fun initAssets() {
        assets.clear()
        assets.addAll(assetPickerRepository.getAssets())
    }

    fun getAssets(): List<AssetInfo> {
        val assetList = _directoryGroup.first { it.directory == directory }.assets

        return assetList.filter(AssetInfo::isImage)
    }

    fun navigateToPreview(index: Int) {
        navController.navigate("asset_preview?index=$index")
    }

    fun deleteImage(cameraUri: Uri?) {
        assetPickerRepository.deleteByUri(cameraUri)
    }

    fun getUri(): Uri? {
        return assetPickerRepository.insertImage()
    }
}