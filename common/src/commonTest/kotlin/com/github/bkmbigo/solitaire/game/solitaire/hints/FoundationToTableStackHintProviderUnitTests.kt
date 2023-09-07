package com.github.bkmbigo.solitaire.game.solitaire.hints

import kotlin.test.Test
import kotlin.test.assertEquals

class FoundationToTableStackHintProviderUnitTests {

    @Test
    fun getFittingCards_returnsProperList() {
        foundationToTableStackTestObjects.forEach { testObject ->
            assertEquals(
                expected = testObject.fittingSpecs,
                actual = SolitaireFoundationToTableStackHintProvider.getFittingCards(
                    card = testObject.currentCard,
                    target = testObject.targetCard
                )
            )
        }
    }

    @Test
    fun allFittingCardsOnFoundationHints() {

        foundationToTableStackTestObjects.forEach { testObject ->
            assertEquals(
                expected = testObject.fittingCards,
                actual = SolitaireFoundationToTableStackHintProvider.allFittingCardsOnFoundation(
                    game = testObject.game,
                    fittingCards = testObject.fittingSpecs
                )
            )
        }

    }

    @Test
    fun isFittingPossible() {

        foundationToTableStackTestObjects.forEach { testObject ->
            assertEquals(
                expected = testObject.moves,
                actual = SolitaireFoundationToTableStackHintProvider.isFittingPossible(
                    game = testObject.game,
                    currentTableStackIndex = 1,
                    targetTableStackIndex = 0,
                    fittingCards = testObject.fittingCards
                )
            )
        }

    }


}
