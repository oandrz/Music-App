package com.huhx.picker.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dre.image_picker.R
import com.huhx.picker.data.AssetInfo
import com.huhx.picker.data.AssetViewModel

@Composable
internal fun AssetDisplayScreen(
    viewModel: AssetViewModel,
    navigateToDropDown: (String) -> Unit,
    onPicked: (List<AssetInfo>) -> Unit,
    onClose: (List<AssetInfo>) -> Unit,
) {
    Scaffold(
        topBar = {
            val directory = viewModel.directory
            DisplayTopAppBar(
                directory = directory,
                selectedList = viewModel.selectedList,
                navigateUp = onClose,
                navigateToDropDown = navigateToDropDown
            )
        },
        bottomBar = { DisplayBottomBar(viewModel, onPicked) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AssetContent(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayTopAppBar(
    directory: String,
    selectedList: List<AssetInfo>,
    navigateUp: (List<AssetInfo>) -> Unit,
    navigateToDropDown: (String) -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = { navigateUp(selectedList) }) {
                Icon(Icons.Filled.Close, contentDescription = "")
            }
        },
        title = {
            Row(modifier = Modifier.clickable { navigateToDropDown(directory) }) {
                Text(directory, fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
            }
        },
    )
}

@Composable
private fun DisplayBottomBar(
    viewModel: AssetViewModel,
    onPicked: (List<AssetInfo>) -> Unit
) {
    var cameraUri: Uri? by remember { mutableStateOf(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            cameraUri?.let { viewModel.initDirectories() }
        } else {
            viewModel.deleteImage(cameraUri)
        }
    }

    if (viewModel.selectedList.isEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(
                onClick = {
                    cameraUri = viewModel.getUri()
                    cameraLauncher.launch(cameraUri)
                },
                content = {
                    Text(text = stringResource(id = R.string.label_camera), fontSize = 16.sp, color = Color.Gray)
                }
            )
            TextButton(
                onClick = {},
                content = {
                    Text(text = stringResource(id = R.string.label_album), fontSize = 16.sp)
                }
            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.text_asset_select), fontSize = 12.sp, color = Color.Gray)
            AppBarButton(
                size = viewModel.selectedList.size,
                onPicked = { onPicked(viewModel.selectedList) }
            )
        }
    }
}

@Composable
private fun AssetContent(
    viewModel: AssetViewModel
) {
    val assets = viewModel.getAssets()
    val gridCount = LocalAssetConfig.current.gridCount

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(gridCount),
        contentPadding = PaddingValues(horizontal = 1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        userScrollEnabled = true
    ) {
        itemsIndexed(assets, key = { _, it -> it.id }) { index, it ->
            AssetImage(
                assetInfo = it,
                navigateToPreview = { viewModel.navigateToPreview(index) },
                selectedList = viewModel.selectedList
            )
        }
    }
}

@Composable
private fun AssetImage(
    assetInfo: AssetInfo,
    selectedList: SnapshotStateList<AssetInfo>,
    navigateToPreview: () -> Unit,
) {
    val selected = selectedList.any { it.id == assetInfo.id }

    val (backgroundColor, alpha) = if (selected) {
        Pair(Color.Black, 0.6F)
    } else {
        Pair(Color.Transparent, 1F)
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .alpha(alpha),
            contentAlignment = Alignment.BottomEnd,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(assetInfo.uriString)
                    .build(),
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1.0F)
                    .clickable { navigateToPreview() },
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            if (assetInfo.isVideo()) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp, end = 8.dp),
                    text = assetInfo.formatDuration(),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
            if (assetInfo.isGif()) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 4.dp, end = 6.dp)
                        .background(
                            color = Color(0F, 0F, 0F, 0.4F),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp),
                        text = stringResource(R.string.text_gif),
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }
        AssetImageIndicator(assetInfo = assetInfo, selected = selected, assetSelected = selectedList)
    }
}
