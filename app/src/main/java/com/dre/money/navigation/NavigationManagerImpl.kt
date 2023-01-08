package com.dre.money.navigation

import com.dre.core.di.MainImmediateScope
import com.dre.core.navigation.NavigationCommand
import com.dre.core.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigationManagerImpl @Inject constructor(
    @MainImmediateScope private val externalMainImmediateScope: CoroutineScope
) : NavigationManager {
    private val navigationCommandChannel = Channel<NavigationCommand>(Channel.BUFFERED)
    override val navigationEvent = navigationCommandChannel.receiveAsFlow()

    override fun navigate(command: NavigationCommand) {
        externalMainImmediateScope.launch {
            navigationCommandChannel.send(command)
        }
    }
}
