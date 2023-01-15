package com.dre.money

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.dre.core.extensions.collectWithLifecycle
import com.dre.core.navigation.NavigationFactory
import com.dre.core.navigation.NavigationManager
import com.dre.core.ui.AndroidStarterTheme
import com.dre.money.navigation.NavigationHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStarterTheme {
                val navController = rememberNavController()

                Scaffold {
                    NavigationHost(
                        modifier = Modifier
                            .padding(it),
                        navController = navController,
                        factories = navigationFactories
                    )
                }

                navigationManager
                    .navigationEvent
                    .collectWithLifecycle(
                        key = navController
                    ) {
                        navController.navigate(it.destination, it.configuration)
                    }
            }
        }
    }
}
