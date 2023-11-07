package com.github.bkmbigo.solitaire.game.solitaire.scoring

import com.github.bkmbigo.solitaire.annotations.UnusableGenericGameApi
import com.github.bkmbigo.solitaire.game.GameScoringSystem
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.utils.SolitaireDealOffset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Duration

/*
* A [SolitairePointsSystem] exposes the following functions:
*       awardInitializationPoints
*           -> Called when the game starts
*       awardGamePoints
*           -> Called when a move that satisfies either:
*               - Reveals a card
*               - Moves a card to the foundation
*       deductGamePoints
*           -> Called when a move either:
*               - Hides a previously revealed card
*               - Moves a card from the foundation
*       deductHintPoints
*           -> Called when a user requests for a hint
*                   NOTE: A hint should not be penalized more than once!!
*       awardMovePoints
*           -> Called in every move
*       awardFromDeckPoints
*           -> Called whenever a card is moved from the deck
*       deductUndoPoints
*           -> Called whenever the user requests for an undo
*       deductRedoPoints
*           -> Called whenever the user requests for a redo
*       deductGameTimePoints
*           -> Called in regular intervals
* */

/** Provides a scoring mechanism for a [SolitaireGame] */
class SolitaireScoringSystem :
    GameScoringSystem<SolitaireGame, SolitaireGameMove, SolitaireGameConfiguration, SolitaireGameProvider> {

    private var currentPointsSystem: SolitairePointsSystem? = null

    override var points: StateFlow<Int> = MutableStateFlow(0).asStateFlow()

    override fun initializedGame(provider: SolitaireGameProvider, configuration: SolitaireGameConfiguration) {
        val newPointsSystem = SolitairePointsSystem(provider, configuration)
        points = newPointsSystem.points

        newPointsSystem.awardInitializationPoints()
    }

    @UnusableGenericGameApi("Does not have enough information to know whether a card was revealed or the favorable dealOffset to use")
    override fun moveMade(game: SolitaireGame, move: SolitaireGameMove, lastMove: SolitaireGameMove) {
    }

    fun moveMade(
        game: SolitaireGame,
        move: SolitaireUserMove.CardMove,
        lastMove: SolitaireUserMove,
        doesRevealCard: Boolean = false,
        isRepetitiveMove: Boolean = false
    ) {
        if (!isRepetitiveMove) {
            if (doesRevealCard) {
                currentPointsSystem?.awardGamePoints(SolitaireGamePointOption.RevealCard)
            }

            if (move.to == MoveDestination.ToFoundation) {
                currentPointsSystem?.awardGamePoints(SolitaireGamePointOption.MoveToFoundation)
            }

            if (move.from == MoveSource.FromFoundation) {
                currentPointsSystem?.deductGamePoints(SolitaireGamePointOption.MoveToFoundation)
            }

            currentPointsSystem?.awardMovePoints(
                timeFromLastMove = move.time - lastMove.time
            )
        }
    }

    fun moveMade(
        game: SolitaireGame,
        move: SolitaireUserMove.Deal,
        lastMove: SolitaireUserMove,
        dealOffset: SolitaireDealOffset
    ) {
        currentPointsSystem?.awardFromDeckPoints(dealOffset = dealOffset)

        currentPointsSystem?.awardMovePoints(
            timeFromLastMove = move.time - lastMove.time
        )
    }

    override fun hintProvided() {
        currentPointsSystem?.deductHintPoints()
    }

    @UnusableGenericGameApi("Does not have enough information to check if a card was hidden")
    override fun undoMovePerformed(game: SolitaireGame, move: SolitaireGameMove) {
    }

    fun undoMovePerformed(
        game: SolitaireGame,
        move: SolitaireGameMove,
        doesReturnCardToDeck: SolitaireDealOffset? = null,
        doesHideCard: Boolean = false,
        doesMoveCardFromFoundation: Boolean = false
    ) {
        doesReturnCardToDeck?.let { doesReturnCardToDeck ->
            currentPointsSystem?.deductFromDeckPoints(doesReturnCardToDeck)
        }

        if (doesHideCard) {
            currentPointsSystem?.deductGamePoints(SolitaireGamePointOption.RevealCard)
        }

        if (doesMoveCardFromFoundation) {
            currentPointsSystem?.deductGamePoints(SolitaireGamePointOption.MoveToFoundation)
        }

        currentPointsSystem?.deductUndoPoints()
    }

    @UnusableGenericGameApi("Does not have enough context to determine whether a card is revealed or the favorable deal offset")
    override fun redoMovePerformed(game: SolitaireGame, move: SolitaireGameMove) {
    }

    fun redoMovePerformed(
        game: SolitaireGame,
        move: SolitaireUserMove.CardMove,
        lastMove: SolitaireUserMove,
        doesRevealCard: Boolean = false,
        isRepetitiveMove: Boolean = false
    ) {
        currentPointsSystem?.deductRedoPoints()

        moveMade(
            game = game,
            move = move,
            lastMove = lastMove,
            doesRevealCard = doesRevealCard,
            isRepetitiveMove = isRepetitiveMove
        )
    }

    fun redoMovePerformed(
        game: SolitaireGame,
        move: SolitaireUserMove.Deal,
        lastMove: SolitaireUserMove,
        dealOffset: SolitaireDealOffset
    ) {
        currentPointsSystem?.deductRedoPoints()

        moveMade(
            game = game,
            move = move,
            lastMove = lastMove,
            dealOffset = dealOffset
        )
    }


    override fun finishedGame(game: SolitaireGame) {
        when {
            game.isWon() -> currentPointsSystem?.awardOutcomePoints(SolitaireOutcome.Won)
            game.isDrawn() -> currentPointsSystem?.awardOutcomePoints(SolitaireOutcome.Drawn)
        }
    }

    override fun penalizeGameTime(duration: Duration) {
        when (duration.inWholeMinutes) {
            3L -> currentPointsSystem?.deductGameTimePoints(SolitaireGameTimeOption.THREE_MINUTES)
            7L -> currentPointsSystem?.deductGameTimePoints(SolitaireGameTimeOption.SEVEN_MINUTES)
            10L -> currentPointsSystem?.deductGameTimePoints(SolitaireGameTimeOption.TEN_MINUTES)
            20L -> currentPointsSystem?.deductGameTimePoints(SolitaireGameTimeOption.TWENTY_MINUTES)
            else -> {}
        }
    }
}
