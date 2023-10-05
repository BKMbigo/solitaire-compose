package cafe.adriel.voyager.core.screen

public typealias ScreenKey = String

public val Screen.uniqueScreenKey: ScreenKey
    get() = "Screen#${randomUuid()}"
