package com.github.bkmbigo.solitaire.game

import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

/** Provides a scoring mechanism for */
interface GameScoringSystem<
        G : Game<G, M>,
        M : GameMove<G, M>,
        C : GameConfiguration<G, M>,
        P : GameProvider<G, M, C>> {

    val points: StateFlow<Int>


    fun initializedGame(provider: P, configuration: C)

    fun moveMade(game: G, move: M, lastMove: M)

    fun hintProvided()

    fun undoMovePerformed(game: G, move: M)

    fun redoMovePerformed(game: G, move: M)

    fun finishedGame(game: G)

    fun penalizeGameTime(duration: Duration)

}
