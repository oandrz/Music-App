package com.huhx.picker.data

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.huhx.picker.view.AssetDisplayScreen
import com.huhx.picker.view.AssetPreviewScreen
import com.huhx.picker.view.AssetSelectorScreen

@Composable
internal fun AssetPickerRoute(
    navController: NavHostController,
    viewModel: AssetViewModel,
    onPicked: (List<AssetInfo>) -> Unit,
    onClose: (List<AssetInfo>) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = "asset_display"
    ) {
        composable("asset_display") {
            AssetDisplayScreen(
                viewModel = viewModel,
                navigateToDropDown = { navController.navigate("asset_selector?directory=$it") },
                onPicked = onPicked,
                onClose = onClose,
            )
        }

        composable(
            "asset_selector?directory={directory}",
            arguments = listOf(navArgument("directory") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments!!
            val directory = arguments.getString("directory")!!
            AssetSelectorScreen(
                directory = directory,
                assetDirectories = viewModel.directoryGroup,
                navigateUp = { navController.navigateUp() },
                onClick = { name ->
                    navController.navigateUp()
                    viewModel.directory = name
                },
            )
        }

        composable(
            "asset_preview?index={index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments!!
            val index = arguments.getInt("index")
            val assets = viewModel.getAssets()
            AssetPreviewScreen(
                index = index,
                assets = assets,
                selectedList = viewModel.selectedList,
                navigateUp = { navController.navigateUp() },
            )
        }
    }
}