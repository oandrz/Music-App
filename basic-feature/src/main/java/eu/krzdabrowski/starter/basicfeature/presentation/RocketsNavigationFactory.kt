package eu.krzdabrowski.starter.basicfeature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.krzdabrowski.starter.basicfeature.presentation.composable.RocketsRoute
import com.dre.core.navigation.NavigationDestination.Rockets
import com.dre.core.navigation.NavigationFactory
import javax.inject.Inject

class RocketsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(Rockets.route) {
            RocketsRoute()
        }
    }
}
