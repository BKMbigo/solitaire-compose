package cafe.adriel.voyager.core.screen

import androidx.compose.runtime.Composable

public interface Screen {
    public val key: ScreenKey
        get() = commonKeyGeneration()

    @Composable
    public fun Content()
}
