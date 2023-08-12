package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObject
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObjects
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SolitaireGameTests {

    @Test
    fun validityTests() = testValidity(SolitaireGameTestObjects)

    @Test
    fun winningTests() = testVictory(SolitaireGameTestObjects)

}

private fun testValidity(testObjects: List<SolitaireGameTestObject>) =
    testObjects.forEachIndexed { index, testObject ->
        if (testObject.isValid) {
            assertTrue("Test for validity for Game $index fails (returns false)") { testObject.game.isValid() }
        } else {
            assertFalse("Test for validity for Game $index fails (returns true)") { testObject.game.isValid() }
        }
    }

private fun testVictory(testObjects: List<SolitaireGameTestObject>) =
    testObjects.forEachIndexed { index, testObject ->
        if (testObject.isWon) {
            assertTrue("Test for victory for Game $index fails (returns false)") { testObject.game.isWon() }
        } else {
            assertFalse("Test for victory for Game $index fails (returns true)") { testObject.game.isWon() }
        }
    }

