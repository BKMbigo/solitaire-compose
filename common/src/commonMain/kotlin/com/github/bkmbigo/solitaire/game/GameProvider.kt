package com.github.bkmbigo.solitaire.game

interface GameProvider<G: Game<G, M>, M: GameMove<G, M>> {
    suspend fun createGame(): G
}
