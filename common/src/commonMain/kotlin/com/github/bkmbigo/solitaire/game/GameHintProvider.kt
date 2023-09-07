package com.github.bkmbigo.solitaire.game

/** Provides a hint provision system for the game. */
interface GameHintProvider<G : Game<G, M>, M : GameMove<G, M>> {

    /** Provides the hints based on the [game] provided. */
    fun provideHints(game: G): List<M>
}
