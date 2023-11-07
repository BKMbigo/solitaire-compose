package com.github.bkmbigo.solitaire.game.solitaire.scoring

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.providers.RandomSolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.providers.VeryEasySolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.utils.SolitaireDealOffset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

/*
* The proposed scoring system includes the following mechanisms:
*   1. OutcomePoints -> Awards points for overall outcome of the game:
*           i) If a win:
*               a) VeryEasy (+600 points)
*               b) Random (+1500 points)
*           ii) If draw:
*               a) VeryEasy (0 points)
*               b) Random (+600 points)
*   2. GameInitializationPoints ->
*           i) VeryEasy (+150 points)
*           ii) Random (+300 points)
*   3. GamePoints -> Awards a point for:
*           i) Revealing a Card (+80 points)
*           ii) Moving a card towards the foundation (+150 points)
*
*   4. HintDeductionPoints -> Deducts points for every hint provided (-10 points)
*
*   5. MovePoints -> Awards points for every move made
*           i) Under 5 seconds (+40 points)
*           ii) Under 30 seconds (+20 points)
*           iii) Under 1 minute (+10 points)
*           iv) Over 1 minute (+5 points)
*
*           If move is from foundation, deduct points according to (3)
*
*   6. FromDeckPoints -> Awards points for freeing cards from the deck
*           If configuration is ThreeCardsPerDeal ->
*               FirstCard (+10 points)
*               SecondCard (+20 points)
*               ThirdCard (+40 points)
*           else OneCardPerDeal ->
*               FirstCard (+10 points)
*
*   7. UndoDeductionPoints -> Deducts points for an undo move (-30 points)
*           if the undo, reverses a move that is awarded by (3 or 6), the system should deduct the points already awarded
*
*   8. RedoDeductionPoints -> Deducts points for a redo operation (-30 points)
*
*   9. GameTimePenaltyPoints -> Deducts points for overall game time
*           i) 3 minutes (-250 points)
*           ii) 7 minutes (-500 points)
*           iii) 10 minutes (-750 points)
*           iv) 20 minutes (-1000 points)
* */

/** Manages the score for a single [SolitaireGame] */
internal class SolitairePointsSystem {
    private var savedProvider: SolitaireGameProvider = VeryEasySolitaireGameProvider

    private val _points = MutableStateFlow(0)
    val points: StateFlow<Int> = _points

    private fun updatePoints(points: Int) {
        _points.value += points
    }

    private fun deductPoints(points: Int) {
        _points.value -= points
    }

    fun awardOutcomePoints(outcome: SolitaireOutcome) {
        val outcomePoints = when (outcome) {
            SolitaireOutcome.Won -> when (savedProvider) {
                VeryEasySolitaireGameProvider -> 600
                RandomSolitaireGameProvider -> 1500
            }

            SolitaireOutcome.Drawn -> when (savedProvider) {
                VeryEasySolitaireGameProvider -> 0
                RandomSolitaireGameProvider -> 600
            }
        }

        updatePoints(outcomePoints)
    }

    fun awardInitializationPoints(provider: SolitaireGameProvider) {
        val initializationPoints = when (provider) {
            VeryEasySolitaireGameProvider -> 150
            RandomSolitaireGameProvider -> 300
        }

        savedProvider = provider

        _points.value = 0
        updatePoints(initializationPoints)
    }

    fun awardGamePoints(gamePointOptions: SolitaireGamePointOption) {
        val gamePoints = when (gamePointOptions) {
            SolitaireGamePointOption.RevealCard -> 80
            SolitaireGamePointOption.MoveToFoundation -> 150
        }

        updatePoints(gamePoints)
    }

    fun deductGamePoints(gamePointOptions: SolitaireGamePointOption) {
        val gamePoints = when (gamePointOptions) {
            SolitaireGamePointOption.RevealCard -> 80
            SolitaireGamePointOption.MoveToFoundation -> 150
        }

        deductPoints(gamePoints)
    }

    fun deductHintPoints() {
        val hintPoints = 10
        deductPoints(hintPoints)
    }

    fun awardMovePoints(timeFromLastMove: Duration) {
        val seconds = timeFromLastMove.inWholeSeconds

        val movePoints = when {
            (seconds < 5) -> 40
            (seconds < 30) -> 20
            (seconds < 60) -> 10
            else -> 5
        }

        updatePoints(movePoints)
    }

    fun awardFromDeckPoints(dealOffset: SolitaireDealOffset) {
        val fromDeckPoints = when (dealOffset) {
            SolitaireDealOffset.NONE -> 10
            SolitaireDealOffset.ONE -> 20
            SolitaireDealOffset.TWO -> 40
        }

        updatePoints(fromDeckPoints)
    }

    fun deductFromDeckPoints(dealOffset: SolitaireDealOffset) {
        val fromDeckPoints = when (dealOffset) {
            SolitaireDealOffset.NONE -> 10
            SolitaireDealOffset.ONE -> 20
            SolitaireDealOffset.TWO -> 40
        }

        deductPoints(fromDeckPoints)
    }

    fun deductUndoPoints() {
        val undoPoints = 30

        deductPoints(undoPoints)
    }

    fun deductRedoPoints() {
        val redoPoints = 30

        deductPoints(redoPoints)
    }

    fun deductGameTimePoints(gameTimeOption: SolitaireGameTimeOption) {
        val gameTimePoints = when (gameTimeOption) {
            SolitaireGameTimeOption.THREE_MINUTES -> 250
            SolitaireGameTimeOption.SEVEN_MINUTES -> 500
            SolitaireGameTimeOption.TEN_MINUTES -> 750
            SolitaireGameTimeOption.TWENTY_MINUTES -> 1000
        }

        deductPoints(gameTimePoints)
    }

}
