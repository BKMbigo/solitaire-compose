package cafe.adriel.voyager.core.lifecycle

import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen

@OptIn(ExperimentalVoyagerApi::class)
internal class DefaultNavigatorScreenLifecycleProvider : NavigatorScreenLifecycleProvider {
    override fun provide(screen: Screen): List<ScreenLifecycleContentProvider> = emptyList()
}
