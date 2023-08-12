package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.fakes.SolitaireGameOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

data class SolitaireGameMoveTest(
    val game: SolitaireGame,
    val move: SolitaireGameMove,
    val actual: Boolean,
    val message: String = "Solitaire Game Move",
    val expectedGame: SolitaireGame = game
)

class SolitaireGameMoveTests {
    @Test
    fun movesTest() = testMoves(SolitaireGameOne.moveTests)

    @Test
    fun playTest() = testPlayResult(SolitaireGameOne.moveTests)
}

private fun testMoves(moves: List<SolitaireGameMoveTest>) = moves.forEach { moveTest ->
    if (moveTest.actual) {
        assertTrue(moveTest.message) { moveTest.move.isValid() }
    } else {
        assertFalse(moveTest.message) { moveTest.move.isValid() }
    }
}

private fun testPlayResult(moves: List<SolitaireGameMoveTest>) = moves.forEach { moveTest ->
    assertEquals(
        moveTest.expectedGame,
        moveTest.game.play(moveTest.move),
        "${moveTest.message} play result differs"
    )
}