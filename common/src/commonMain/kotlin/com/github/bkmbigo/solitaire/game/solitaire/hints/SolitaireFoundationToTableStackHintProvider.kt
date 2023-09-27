package com.github.bkmbigo.solitaire.game.solitaire.hints

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardColor
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.core.utils.of
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/** Finds moves that involve moving a card(s) from the table stack in-order to reveal a card */
internal object SolitaireFoundationToTableStackHintProvider {

    operator fun invoke(game: SolitaireGame): List<SolitaireUserMove> {
        with(game) {
            val moves = mutableListOf<SolitaireUserMove>()

            tableStacks.forEachIndexed { currentIndex, currentTableStack ->
                tableStacks.forEachIndexed { targetIndex, targetTableStack ->
                    if (currentIndex != targetIndex) {
                        val current = currentTableStack.firstRevealedCard
                        if (
                            current != null &&
                            !(currentTableStack.hiddenCards.isEmpty() && currentTableStack.firstRevealedCard?.rank == CardRank.KING) &&
                            canFitInTableStack(current, targetTableStack)
                        ) {
                            val fittingCardSpecs = getFittingCards(current, targetTableStack.lastCard!!)
                            allFittingCardsOnFoundation(game, fittingCardSpecs)?.let { fittingCards ->
                                isFittingPossible(game, currentIndex, targetIndex, fittingCards)?.let { fittingMoves ->
                                    fittingMoves.firstOrNull()?.let { firstMove ->
                                        moves.add(firstMove)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return moves
        }
    }


    /** Checks if [card] can fit in [target] */
    internal fun canFitInTableStack(card: Card, target: TableStack): Boolean {
        val topCard = target.lastCard ?: return false
        val rankDifference = topCard.rank.ordinal - card.rank.ordinal
        return if (rankDifference % 2 == 0) {
            topCard.color == card.color
        } else {
            topCard.color != card.color && rankDifference > 1
        }
    }


    /** Gets a list of cards needed to fit [card] to [target]. For example, to fit THREE of HEARTS to SEVEN of DIAMOND, you require a Black SIX, Red FIVE and a Black FOUR */
    internal fun getFittingCards(card: Card, target: Card): List<Pair<CardRank, CardColor>> {
        if (card.rank.ordinal > target.rank.ordinal) return emptyList()

        return ((card.rank.ordinal + 1)..<target.rank.ordinal).reversed().map { rank ->
            val rankDifference = rank - card.rank.ordinal

            val color = if (rankDifference % 2 == 0) {
                card.color
            } else {
                card.color.alternate
            }

            CardRank.entries[rank] to color
        }
    }

    /** Returns a list of cards needed to fit. Returns null if a card cannot be resolved, hence fitting cannot occur in the game */
    internal fun allFittingCardsOnFoundation(
        game: SolitaireGame,
        fittingCards: List<Pair<CardRank, CardColor>>
    ): List<Card>? {
        with(game) {
            val cards = mutableListOf<Card>()

            fittingCards.forEach { cardSpec ->
                when (cardSpec.second) {
                    CardColor.BLACK -> {
                        val spadeHighestRank = spadeFoundationStack.lastOrNull()?.rank?.ordinal ?: -1
                        val cloverHighestRank = cloverFoundationStack.lastOrNull()?.rank?.ordinal ?: -1
                        val spadeContainsCard = cardSpec.first.ordinal <= spadeHighestRank
                        val cloverContainsCard = cardSpec.first.ordinal <= cloverHighestRank

                        if (spadeContainsCard || cloverContainsCard) {

                            val preferredSuite =
                                if (spadeContainsCard && !(cloverContainsCard && cloverHighestRank < spadeHighestRank)) {
                                    CardSuite.SPADE
                                } else {
                                    CardSuite.CLOVER
                                }

                            cards.add(cardSpec.first of preferredSuite)
                        } else {
                            return null
                        }
                    }

                    CardColor.RED -> {
                        val heartsHighestRank = heartsFoundationStack.lastOrNull()?.rank?.ordinal ?: -1
                        val diamondHighestRank = diamondFoundationStack.lastOrNull()?.rank?.ordinal ?: -1
                        val heartsContainsCard = cardSpec.first.ordinal <= heartsHighestRank
                        val diamondContainsCard = cardSpec.first.ordinal <= diamondHighestRank

                        if (heartsContainsCard || diamondContainsCard) {

                            val preferredSuite =
                                if (heartsContainsCard && !(diamondContainsCard && diamondHighestRank < heartsHighestRank)) {
                                    CardSuite.HEARTS
                                } else {
                                    CardSuite.DIAMOND
                                }

                            cards.add(cardSpec.first of preferredSuite)
                        } else {
                            return null
                        }
                    }
                }
            }

            return cards
        }
    }

    /** Runs a controlled simulation to verify whether the proposed moves can be executed to achieve a move */
    internal fun isFittingPossible(
        game: SolitaireGame,
        currentTableStackIndex: Int,
        targetTableStackIndex: Int,
        fittingCards: List<Card>
    ): List<SolitaireUserMove>? {
        with(game) {
            var newGame = this
            val moves = mutableListOf<SolitaireUserMove>()

            fittingCards.forEach { card ->
                val attemptedMoves = attemptMoveFromFoundation(newGame, card, targetTableStackIndex)
                if (attemptedMoves != null) {
                    newGame = attemptedMoves.first
                    moves.addAll(attemptedMoves.second)
                } else {
                    return null
                }
            }

            val currentTableStackEntry = TableStackEntry.entries[currentTableStackIndex]

            // After fittingCards are placed on the stack, attempt to place the targetCard on the currentStack
            val finalMove =
                tableStack(currentTableStackEntry).revealedCards move currentTableStackEntry to TableStackEntry.entries[targetTableStackIndex]

            if (finalMove.isValid(newGame)) {
                moves.add(finalMove)
            } else {
                return null
            }

            return moves
        }
    }

    /** Finds moves to move all cards top of the target Card and then move the target Card to the target Table Stack. If its not possible to move the card, it returns null*/
    internal fun attemptMoveFromFoundation(
        game: SolitaireGame,
        card: Card,
        targetTableStackIndex: Int
    ): Pair<SolitaireGame, List<SolitaireUserMove>>? {
        with(game) {
            val cardsToFindMoves = foundationStack(card.suite).filter { it.rank >= card.rank }

            var newGame = this
            val moves = mutableListOf<SolitaireUserMove>()

            cardsToFindMoves.reversed().forEach { cardToFindMove ->
                /* If the card you want to move is the target card, then it should move to the targetTableStack. No other card should move to the target tableStack */
                val possibleMove = if (card == cardToFindMove) {
                    val proposedMove =
                        card move MoveSource.FromFoundation to MoveDestination.ToTable(TableStackEntry.entries[targetTableStackIndex])
                    if (proposedMove.isValid(newGame)) {
                        proposedMove
                    } else {
                        null
                    }
                } else {
                    findPossibleMoveForSingleCard(game, cardToFindMove, targetTableStackIndex)
                }
                if (possibleMove != null && possibleMove.isValid(newGame)) {
                    newGame = newGame.play(possibleMove)
                    moves.add(possibleMove)
                } else {
                    return null
                }
            }

            return newGame to moves
        }
    }

    internal fun findPossibleMoveForSingleCard(
        game: SolitaireGame,
        card: Card,
        targetTableStackIndex: Int
    ): SolitaireUserMove? {
        with(game) {
            /* For each tableStack attempt a move and find whether it is valid. Does not propose a move to the currentTableStack */
            TableStackEntry.entries.forEachIndexed { index, targetTableStackEntry ->
                if (index != targetTableStackIndex) {
                    val move = card move MoveSource.FromFoundation to MoveDestination.ToTable(targetTableStackEntry)
                    if (move.isValid(this)) {
                        return move
                    }
                }
            }
            return null
        }
    }
}
