package com.github.bkmbigo.solitaire.game.solitaire.hints

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource.FromFoundation
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardColor
import com.github.bkmbigo.solitaire.models.core.CardColor.BLACK
import com.github.bkmbigo.solitaire.models.core.CardColor.RED
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.core.CardRank.*
import com.github.bkmbigo.solitaire.models.core.CardSuite.*
import com.github.bkmbigo.solitaire.models.core.utils.of
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

class FoundationToTableStackTestObject(
    val game: SolitaireGame,
    val currentCard: Card,
    val targetCard: Card,
    val fittingSpecs: List<Pair<CardRank, CardColor>>,
    val fittingCards: List<Card>,
    val moves: List<SolitaireUserMove>,
    val resultingGame: SolitaireGame
)

val foundationToTableStackTestObjects = listOf(
    FoundationToTableStackTestObject(
        game = SolitaireGame(
            configuration = SolitaireGameConfiguration(cardsPerDeal = SolitaireCardsPerDeal.ONE),
            deck = listOf(),
            spadeFoundationStack = listOf(
                ACE of SPADE,
                TWO of SPADE,
                THREE of SPADE,
                FOUR of SPADE,
                FIVE of SPADE,
            ),
            cloverFoundationStack = listOf(),
            heartsFoundationStack = listOf(),
            diamondFoundationStack = listOf(),
            firstTableStackState = TableStack(
                revealedCards = listOf(
                    SIX of HEARTS,
                )
            ),
            secondTableStackState = TableStack(
                hiddenCards = listOf(
                    KING of SPADE
                ),
                revealedCards = listOf(
                    FOUR of DIAMOND
                )
            ),
            thirdTableStackState = TableStack(),
            fourthTableStackState = TableStack(),
            fifthTableStackState = TableStack(),
            sixthTableStackState = TableStack(),
            seventhTableStackState = TableStack()
        ),
        currentCard = FOUR of DIAMOND,
        targetCard = SIX of HEARTS,
        fittingSpecs = listOf(
            FIVE to BLACK
        ),
        fittingCards = listOf(
            FIVE of SPADE
        ),
        moves = listOf(
            FIVE of SPADE move FromFoundation to TableStackEntry.ONE,
            FOUR of DIAMOND move TableStackEntry.TWO to TableStackEntry.ONE
        ),
        resultingGame = SolitaireGame(
            configuration = SolitaireGameConfiguration(cardsPerDeal = SolitaireCardsPerDeal.ONE),
            deck = listOf(),
            spadeFoundationStack = listOf(
                ACE of SPADE,
                TWO of SPADE,
                THREE of SPADE,
                FOUR of SPADE,
                FIVE of SPADE,
            ),
            cloverFoundationStack = listOf(),
            heartsFoundationStack = listOf(),
            diamondFoundationStack = listOf(),
            firstTableStackState = TableStack(
                revealedCards = listOf(
                    SIX of HEARTS,
                    FIVE of SPADE,
                    FOUR of SPADE
                )
            ),
            secondTableStackState = TableStack(
                hiddenCards = listOf(
                    KING of SPADE
                )
            ),
            thirdTableStackState = TableStack(),
            fourthTableStackState = TableStack(),
            fifthTableStackState = TableStack(),
            sixthTableStackState = TableStack(),
            seventhTableStackState = TableStack()
        ),
    ),
    FoundationToTableStackTestObject(
        game = SolitaireGame(
            configuration = SolitaireGameConfiguration(cardsPerDeal = SolitaireCardsPerDeal.ONE),
            deck = listOf(),
            spadeFoundationStack = listOf(
                ACE of SPADE,
                TWO of SPADE,
                THREE of SPADE,
                FOUR of SPADE,
                FIVE of SPADE,
            ),
            cloverFoundationStack = listOf(),
            heartsFoundationStack = listOf(
                ACE of HEARTS,
                TWO of HEARTS,
                THREE of HEARTS,
                FOUR of HEARTS,
                FIVE of HEARTS,
                SIX of HEARTS,
            ),
            diamondFoundationStack = listOf(
                ACE of DIAMOND,
                TWO of DIAMOND,
                THREE of DIAMOND
            ),
            firstTableStackState = TableStack(
                revealedCards = listOf(
                    SEVEN of SPADE,
                )
            ),
            secondTableStackState = TableStack(
                hiddenCards = listOf(
                    KING of SPADE
                ),
                revealedCards = listOf(
                    ACE of CLOVER
                )
            ),
            thirdTableStackState = TableStack(
                revealedCards = listOf(
                    SIX of CLOVER
                )
            ),
            fourthTableStackState = TableStack(),
            fifthTableStackState = TableStack(),
            sixthTableStackState = TableStack(),
            seventhTableStackState = TableStack()
        ),
        currentCard = ACE of CLOVER,
        targetCard = SEVEN of SPADE,
        fittingSpecs = listOf(
            SIX to RED,
            FIVE to BLACK,
            FOUR to RED,
            THREE to BLACK,
            TWO to RED
        ),
        fittingCards = listOf(
            SIX of HEARTS,
            FIVE of SPADE,
            FOUR of HEARTS,
            THREE of SPADE,
            TWO of DIAMOND
        ),
        moves = listOf(
            SIX of HEARTS move FromFoundation to TableStackEntry.ONE,
            FIVE of SPADE move FromFoundation to TableStackEntry.ONE,
            FIVE of HEARTS move FromFoundation to TableStackEntry.THREE,    // Adjustment move to free FOUR of HEARTS
            FOUR of HEARTS move FromFoundation to TableStackEntry.ONE,
            FOUR of SPADE move FromFoundation to TableStackEntry.THREE,      // Adjustment move to free THREE of SPADE
            THREE of SPADE move FromFoundation to TableStackEntry.ONE,
            THREE of DIAMOND move FromFoundation to TableStackEntry.THREE,    // Adjustment move to free TWO of DIAMOND
            TWO of DIAMOND move FromFoundation to TableStackEntry.ONE,
            ACE of CLOVER move TableStackEntry.TWO to TableStackEntry.ONE
        ),
        resultingGame = SolitaireGame(
            configuration = SolitaireGameConfiguration(cardsPerDeal = SolitaireCardsPerDeal.ONE),
            deck = listOf(),
            spadeFoundationStack = listOf(
                ACE of SPADE,
                TWO of SPADE,
            ),
            cloverFoundationStack = listOf(),
            heartsFoundationStack = listOf(
                ACE of HEARTS,
                TWO of HEARTS,
                THREE of HEARTS,
            ),
            diamondFoundationStack = listOf(
                ACE of DIAMOND,
            ),
            firstTableStackState = TableStack(
                revealedCards = listOf(
                    SEVEN of SPADE,
                    SIX of HEARTS,
                    FIVE of SPADE,
                    FOUR of HEARTS,
                    THREE of SPADE,
                    TWO of DIAMOND,
                    ACE of CLOVER
                )
            ),
            secondTableStackState = TableStack(
                hiddenCards = listOf(
                    KING of SPADE
                ),
                revealedCards = listOf(
                )
            ),
            thirdTableStackState = TableStack(
                revealedCards = listOf(
                    SIX of CLOVER,
                    FIVE of HEARTS,
                )
            ),
            fourthTableStackState = TableStack(
                revealedCards = listOf(
                    FIVE of DIAMOND,
                    FOUR of SPADE,
                )
            ),
            fifthTableStackState = TableStack(
                revealedCards = listOf(
                    FOUR of CLOVER,
                    THREE of DIAMOND
                )
            ),
            sixthTableStackState = TableStack(),
            seventhTableStackState = TableStack()
        ),
    ),

    )
