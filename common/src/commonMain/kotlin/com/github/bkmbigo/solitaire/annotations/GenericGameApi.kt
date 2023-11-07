package com.github.bkmbigo.solitaire.annotations

/** Used to annotate that the given function or class is overridden in the particular game and one should prefer to use the game-specific API*/
@RequiresOptIn(message = "This API has a better overridden API in the game", level = RequiresOptIn.Level.WARNING)
annotation class GenericGameApi(val instruction: String)


