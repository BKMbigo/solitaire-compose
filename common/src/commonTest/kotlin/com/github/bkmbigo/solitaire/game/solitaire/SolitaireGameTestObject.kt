package com.github.bkmbigo.solitaire.game.solitaire

import com.github.bkmbigo.solitaire.game.solitaire.fakes.SolitaireGameFive
import com.github.bkmbigo.solitaire.game.solitaire.fakes.SolitaireGameFour
import com.github.bkmbigo.solitaire.game.solitaire.fakes.SolitaireGameOne
import com.github.bkmbigo.solitaire.game.solitaire.fakes.SolitaireGameSix
import com.github.bkmbigo.solitaire.game.solitaire.fakes.SolitaireGameThree
import com.github.bkmbigo.solitaire.game.solitaire.fakes.SolitaireGameTwo
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMoveTest

data class SolitaireGameTestObject(
    val game: SolitaireGame,
    val isValid: Boolean,
    val isWon: Boolean,
    val isDrawn: Boolean,
    val moveTests: List<SolitaireGameMoveTest>
)

val SolitaireGameTestObjects = listOf(SolitaireGameOne, SolitaireGameTwo, SolitaireGameThree, SolitaireGameFour, SolitaireGameFive, SolitaireGameSix)