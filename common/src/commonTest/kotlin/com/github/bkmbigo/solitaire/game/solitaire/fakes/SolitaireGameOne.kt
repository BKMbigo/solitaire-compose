package com.github.bkmbigo.solitaire.game.solitaire.fakes

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObject
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination.ToFoundation
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource.FromDeck
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.*
import com.github.bkmbigo.solitaire.models.core.CardRank.*
import com.github.bkmbigo.solitaire.models.core.CardSuite.*
import com.github.bkmbigo.solitaire.models.core.utils.of
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

private val originalGame = SolitaireGame(
    configuration = SolitaireGameConfiguration(cardsPerDeal = SolitaireCardsPerDeal.ONE),
    deck = listOf(
        ACE of SPADE,
        THREE of SPADE,
        JUDGE of SPADE,
        SEVEN of DIAMOND, // 3
        EIGHT of CLOVER,
        TEN of HEARTS,
        JUDGE of HEARTS, // 6
        KING of HEARTS,
        SEVEN of SPADE,
        QUEEN of DIAMOND, // 9
        KING of CLOVER,
        JUDGE of CLOVER,
        NINE of CLOVER, // 12
        THREE of HEARTS,
        TWO of SPADE,
        FIVE of HEARTS, // 15
        SIX of SPADE,
        NINE of SPADE,
    ),
    spadeFoundationStack = emptyList(),
    cloverFoundationStack = listOf(
        ACE of CLOVER,
        TWO of CLOVER,
    ),
    heartsFoundationStack = emptyList(),
    diamondFoundationStack = listOf(
        ACE of DIAMOND,
        TWO of DIAMOND,
        THREE of DIAMOND,
        FOUR of DIAMOND,
        FIVE of DIAMOND
    ),
    firstTableStackState = TableStack.EMPTY,
    secondTableStackState = TableStack(
        hiddenCards = emptyList(),
        revealedCards = listOf(
            ACE of HEARTS
        )
    ),
    thirdTableStackState = TableStack(
        hiddenCards = listOf(
            TWO of HEARTS,
            SIX of CLOVER
        ),
        revealedCards = listOf(
            EIGHT of DIAMOND,
            SEVEN of CLOVER,
            SIX of DIAMOND
        )
    ),
    fourthTableStackState = TableStack(
        revealedCards = listOf(
            KING of DIAMOND,
            QUEEN of CLOVER,
            JUDGE of DIAMOND,
            TEN of SPADE
        )
    ),
    fifthTableStackState = TableStack(
        hiddenCards = listOf(
            TEN of CLOVER,
            QUEEN of SPADE
        ),
        revealedCards = listOf(
            SIX of HEARTS,
            FIVE of CLOVER,
            FOUR of HEARTS
        )
    ),
    sixthTableStackState = TableStack(
        hiddenCards = listOf(
            FOUR of SPADE,
            EIGHT of HEARTS,
            TEN of DIAMOND
        ),
        revealedCards = listOf(
            NINE of DIAMOND,
            EIGHT of SPADE,
            SEVEN of HEARTS
        )
    ),
    seventhTableStackState = TableStack(
        hiddenCards = listOf(
            THREE of CLOVER,
            FOUR of CLOVER,
            FIVE of SPADE,
            NINE of HEARTS
        ),
        revealedCards = listOf(
            KING of SPADE,
            QUEEN of HEARTS,
        )
    )
)

val SolitaireGameOne = SolitaireGameTestObject(
    game = originalGame,
    isValid = true,
    isWon = false,
    isDrawn = false,
    moveTests = listOf(
        (ACE of HEARTS) moveSolitaireInstantlyFrom TableStackEntry.TWO moveTo ToFoundation
                testIsValid originalGame withMessage "Move of Ace from Table(on Top) to Empty Foundation fails"
                expectGame originalGame
                    .withTableStack(TableStackEntry.TWO, originalGame.secondTableStackState.withRemovedCard(ACE of HEARTS))
                    .withFoundationStack(
                        HEARTS, listOf(ACE of HEARTS)
                    ),

        (SIX of DIAMOND) moveSolitaireInstantlyFrom TableStackEntry.THREE moveTo ToFoundation testIsValid originalGame
                withMessage "Move of [Six of Diamonds] from Table to Diamond Foundation(Ace to Five) fails"
                expectGame originalGame
                    .withTableStack(
                        TableStackEntry.THREE, originalGame.thirdTableStackState.withRemovedCard(SIX of DIAMOND)
                    )
                    .withFoundationStack(
                        DIAMOND, originalGame.diamondFoundationStack.withAddedCard(
                            SIX of DIAMOND
                        )
                    ),

        (ACE of SPADE) moveSolitaireInstantlyFrom FromDeck(0) moveTo ToFoundation testIsValid originalGame
                withMessage "Move of [Ace of Spade] from Deck to Empty Foundation fails"
                expectGame originalGame
                    .withRemoveFromDeck(ACE of SPADE)
                    .withFoundationStack(
                        SPADE, originalGame.spadeFoundationStack.withAddedCard(
                            ACE of SPADE
                        )
                    ),

        (ACE of HEARTS) moveSolitaireInstantlyFrom FromDeck(0) moveTo ToFoundation testIsNotValid originalGame
                withMessage "Invalid Move of [Ace of Hearts] from Deck (Not in deck) to Empty Foundation Passes"
                expectGame originalGame,

        (KING of HEARTS) moveSolitaireInstantlyFrom FromDeck(7) moveTo TableStackEntry.ONE
                testIsValid originalGame withMessage "Move of King from Deck to Empty Table Stack fails"
                expectGame originalGame
                    .withRemoveFromDeck(KING of HEARTS)
                    .withTableStack(
                        TableStackEntry.ONE, originalGame.firstTableStackState.withAddedCard(
                            KING of HEARTS
                        )
                    ),

        (QUEEN of DIAMOND) moveSolitaireInstantlyFrom FromDeck(9) moveTo TableStackEntry.ONE
                testIsNotValid originalGame
                withMessage "Invalid move of Queen from Deck to Empty Table Stack passes"
                expectGame originalGame,

        (THREE of CLOVER) moveSolitaireInstantlyFrom TableStackEntry.SEVEN moveTo ToFoundation
                testIsNotValid originalGame withMessage "Invalid move of [Three of Clover] from table(is Hidden) to deck passes",

        (JUDGE of SPADE) moveSolitaireInstantlyFrom FromDeck(2) moveTo TableStackEntry.SEVEN testIsValid originalGame
                withMessage "Move of [Judge of Spade] from deck to Table([Queen of Hearts] on top of stack)"
                expectGame originalGame
                    .withRemoveFromDeck(JUDGE of SPADE)
                    .withTableStack(
                        TableStackEntry.SEVEN, originalGame.seventhTableStackState.withAddedCard(
                            JUDGE of SPADE
                        )
                    ),

        listOf(
            FIVE of CLOVER,
            FOUR of HEARTS
        ) moveSolitaireInstantlyFrom TableStackEntry.FIVE moveTo TableStackEntry.THREE testIsValid originalGame
                withMessage "Move of [Five of Clover, Four of Spade] from Table([SIX of HEARTS, FIVE of CLOVER, FOUR of HEARTS]) to Table([EIGHT of DIAMOND, SEVEN of CLOVER, SIX of DIAMOND]) fails"
                expectGame originalGame
                    .withTableStack(
                        TableStackEntry.FIVE, originalGame.fifthTableStackState.withRemovedCards(
                            listOf(FIVE of CLOVER, FOUR of HEARTS)
                        )
                    )
                    .withTableStack(
                        TableStackEntry.THREE, originalGame.thirdTableStackState.withAddedCards(
                            listOf(FIVE of CLOVER, FOUR of HEARTS)
                        )
                    ),

        originalGame.sixthTableStackState.revealedCards moveSolitaireInstantlyFrom TableStackEntry.SIX
                moveTo TableStackEntry.FOUR
                testIsValid originalGame
                withMessage "Move of [NINE of DIAMOND, EIGHT of SPADE, SEVEN of HEARTS] to Table([KING of DIAMOND, QUEEN of CLOVER, JUDGE of DIAMOND, TEN of SPADE]) fails"
                expectGame originalGame
            .withTableStack(
                TableStackEntry.SIX,
                originalGame.sixthTableStackState.copy(revealedCards = emptyList())
            )
            .withTableStack(
                TableStackEntry.FOUR, originalGame.fourthTableStackState.withAddedCards(
                    listOf(NINE of DIAMOND, EIGHT of SPADE, SEVEN of HEARTS)
                )
            ),

        originalGame.seventhTableStackState.revealedCards moveSolitaireInstantlyFrom TableStackEntry.SEVEN
                moveTo TableStackEntry.ONE
                testIsValid originalGame
                withMessage "Move of [KING of SPADE, QUEEN of HEARTS] to Empty Table fails"
                expectGame originalGame
                    .withTableStack(TableStackEntry.SEVEN, originalGame.seventhTableStackState.copy(revealedCards = emptyList()))
                    .withTableStack(
                        TableStackEntry.ONE,
                        TableStack(
                            revealedCards = listOf(KING of SPADE, QUEEN of HEARTS)
                        )
                    )
    )
)
