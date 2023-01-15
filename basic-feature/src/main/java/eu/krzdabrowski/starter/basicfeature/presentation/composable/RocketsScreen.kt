package eu.krzdabrowski.starter.basicfeature.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import eu.krzdabrowski.starter.basicfeature.R
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent.OpenWebBrowserWithDetails
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RefreshRockets
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RocketClicked
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsViewModel
import com.dre.core.extensions.collectAsStateWithLifecycle
import com.dre.core.extensions.collectWithLifecycle
import com.dre.core.navigation.NavigationCommand
import com.dre.core.navigation.NavigationDestination
import com.dre.core.navigation.NavigationManager
import kotlinx.coroutines.flow.Flow

private const val TOOLBAR = "Testing"

@Composable
fun RocketsRoute(
    viewModel: RocketsViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {
    HandleEvents(viewModel.event, navigationManager)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RocketsScreen(
        uiState = uiState,
        onRefreshRockets = {
            viewModel.acceptIntent(RefreshRockets)
        },
        onRocketClicked = {
            viewModel.acceptIntent(RocketClicked(it))
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun RocketsScreen(
    uiState: RocketsUiState,
    onRefreshRockets: () -> Unit,
    onRocketClicked: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = TOOLBAR)
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = onRefreshRockets
        ) {
            if (uiState.rockets.isNotEmpty()) {
                RocketsAvailableContent(
                    scaffoldState = scaffoldState,
                    uiState = uiState,
                    onRocketClick = onRocketClicked
                )
            } else {
                RocketsNotAvailableContent(
                    uiState = uiState
                )
            }
        }
    }
}

@Composable
private fun HandleEvents(events: Flow<RocketsEvent>, navigationManager: NavigationManager) {
    events.collectWithLifecycle {
        when (it) {
            is OpenWebBrowserWithDetails -> {
                navigationManager.navigate(object : NavigationCommand {
                    override val destination: String
                        get() = NavigationDestination.ReceiptRecognition.route
                })
            }
        }
    }
}

@Composable
private fun RocketsAvailableContent(
    scaffoldState: ScaffoldState,
    uiState: RocketsUiState,
    onRocketClick: (String) -> Unit
) {
    if (uiState.isError) {
        val errorMessage = stringResource(R.string.rockets_error_refreshing)

        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage
            )
        }
    }

    RocketsListContent(
        rocketList = uiState.rockets,
        onRocketClick = { onRocketClick(it) }
    )
}

@Composable
private fun RocketsNotAvailableContent(uiState: RocketsUiState) {
    when {
        uiState.isLoading -> RocketsLoadingPlaceholder()
        uiState.isError -> RocketsErrorContent()
    }
}
