package com.github.bkmbigo.solitaire.game

interface GameProvider<G: Game<G, M>, M: GameMove<G, M>, C: GameConfiguration<G, M>> {
    suspend fun createGame(configuration: C): G
}
