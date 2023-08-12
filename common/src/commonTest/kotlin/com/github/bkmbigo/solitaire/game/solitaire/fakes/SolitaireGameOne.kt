package com.github.bkmbigo.solitaire.game.solitaire.fakes

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObject
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.expectGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.from
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.test
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.withAddedCard
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.withMessage
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.withRemoveFromDeck
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.core.utils.of
import com.github.bkmbigo.solitaire.models.solitaire.utils.TableStackEntry

private val originalGame = SolitaireGame(
    deck = listOf(
        CardRank.ACE of CardSuite.SPADE,
        CardRank.THREE of CardSuite.SPADE,
        CardRank.JUDGE of CardSuite.SPADE,
        CardRank.SEVEN of CardSuite.DIAMOND,
        CardRank.EIGHT of CardSuite.CLOVER,
        CardRank.TEN of CardSuite.HEARTS,
        CardRank.JUDGE of CardSuite.HEARTS,
        CardRank.KING of CardSuite.HEARTS,
        CardRank.SEVEN of CardSuite.SPADE,
        CardRank.QUEEN of CardSuite.DIAMOND,
        CardRank.KING of CardSuite.CLOVER,
        CardRank.JUDGE of CardSuite.CLOVER,
        CardRank.NINE of CardSuite.CLOVER
    ),
    spadeFoundationStack = emptyList(),
    cloverFoundationStack = listOf(
        CardRank.ACE of CardSuite.CLOVER,
        CardRank.TWO of CardSuite.CLOVER,
    ),
    heartsFoundationStack = emptyList(),
    diamondFoundationStack = listOf(
        CardRank.ACE of CardSuite.DIAMOND,
        CardRank.TWO of CardSuite.DIAMOND,
        CardRank.THREE of CardSuite.DIAMOND,
        CardRank.FOUR of CardSuite.DIAMOND,
        CardRank.FIVE of CardSuite.DIAMOND
    ),
    firstTableStackState = TableStack.EMPTY,
    secondTableStackState = TableStack(
        hiddenCards = emptyList(),
        revealedCards = listOf(
            CardRank.ACE of CardSuite.HEARTS
        )
    ),
    thirdTableStackState = TableStack(
        hiddenCards = listOf(
            CardRank.TWO of CardSuite.HEARTS,
            CardRank.SIX of CardSuite.CLOVER
        ),
        revealedCards = listOf(
            CardRank.EIGHT of CardSuite.DIAMOND,
            CardRank.SEVEN of CardSuite.CLOVER,
            CardRank.SIX of CardSuite.DIAMOND
        )
    ),
    fourthTableStackState = TableStack(
        revealedCards = listOf(
            CardRank.KING of CardSuite.DIAMOND,
            CardRank.QUEEN of CardSuite.CLOVER,
            CardRank.JUDGE of CardSuite.DIAMOND,
            CardRank.TEN of CardSuite.SPADE
        )
    ),
    fifthTableStackState = TableStack(
        hiddenCards = listOf(
            CardRank.TEN of CardSuite.CLOVER,
            CardRank.QUEEN of CardSuite.SPADE
        ),
        revealedCards = listOf(
            CardRank.SIX of CardSuite.HEARTS,
            CardRank.FIVE of CardSuite.CLOVER,
            CardRank.FOUR of CardSuite.HEARTS
        )
    ),
    sixthTableStackState = TableStack(
        hiddenCards = listOf(
            CardRank.FOUR of CardSuite.SPADE,
            CardRank.EIGHT of CardSuite.HEARTS,
            CardRank.TEN of CardSuite.DIAMOND
        ),
        revealedCards = listOf(
            CardRank.THREE of CardSuite.HEARTS,
            CardRank.TWO of CardSuite.SPADE
        )
    ),
    seventhTableStackState = TableStack(
        hiddenCards = listOf(
            CardRank.FOUR of CardSuite.CLOVER,
            CardRank.FIVE of CardSuite.SPADE,
            CardRank.NINE of CardSuite.HEARTS
        ),
        revealedCards = listOf(
            CardRank.KING of CardSuite.SPADE,
            CardRank.QUEEN of CardSuite.HEARTS,
        )
    ),
    eighthTableStackState = TableStack(
        hiddenCards = listOf(
            CardRank.THREE of CardSuite.CLOVER,
            CardRank.FIVE of CardSuite.HEARTS,
            CardRank.SIX of CardSuite.SPADE,
            CardRank.NINE of CardSuite.SPADE
        ),
        revealedCards = listOf(
            CardRank.NINE of CardSuite.DIAMOND,
            CardRank.EIGHT of CardSuite.SPADE,
            CardRank.SEVEN of CardSuite.HEARTS
        )
    )
)

val SolitaireGameOne = SolitaireGameTestObject(
    game = originalGame,
    isValid = true,
    isWon = false,
    isDrawn = false,
    moveTests = listOf(
        originalGame move (CardRank.ACE of CardSuite.HEARTS) from TableStackEntry.TWO to MoveDestination.ToFoundation
                test true withMessage "Move of Ace from Table(on Top) to Empty Foundation fails"
                expectGame originalGame.withRemoveFromDeck(CardRank.ACE of CardSuite.HEARTS)
            .withFoundationStack(
                CardSuite.HEARTS, listOf(CardRank.ACE of CardSuite.HEARTS)
            ),

        originalGame move (CardRank.SIX of CardSuite.DIAMOND) from TableStackEntry.THREE to MoveDestination.ToFoundation
                test true withMessage "Move of [Six of Diamonds] from Table to Diamond Foundation(Ace to Five) fails"
                expectGame originalGame
            .withTableStack(
                TableStackEntry.THREE, originalGame.thirdTableStackState.withRemovedCard(
                    CardRank.SIX of CardSuite.DIAMOND
                )
            )
            .withFoundationStack(
                CardSuite.DIAMOND, originalGame.diamondFoundationStack.withAddedCard(
                    CardRank.SIX of CardSuite.DIAMOND
                )
            ),

        originalGame move (CardRank.ACE of CardSuite.SPADE) from MoveSource.FromDeck to MoveDestination.ToFoundation
                test true withMessage "Move of [Ace of Spade] from Deck to Empty Foundation fails"
                expectGame originalGame
            .withRemoveFromDeck(CardRank.ACE of CardSuite.SPADE)
            .withFoundationStack(
                CardSuite.SPADE, originalGame.spadeFoundationStack.withAddedCard(
                    CardRank.ACE of CardSuite.SPADE
                )
            ),

        originalGame move (CardRank.ACE of CardSuite.HEARTS) from MoveSource.FromDeck to MoveDestination.ToFoundation
                test false withMessage "Invalid Move of [Ace of Hearts] from Deck (Not in deck) to Empty Foundation Passes"
                expectGame originalGame,

        originalGame move (CardRank.KING of CardSuite.HEARTS) from MoveSource.FromDeck to TableStackEntry.ONE
                test true withMessage "Move of King from Deck to Empty Table Stack fails"
                expectGame originalGame
            .withRemoveFromDeck(CardRank.KING of CardSuite.HEARTS)
            .withTableStack(
                TableStackEntry.ONE, originalGame.firstTableStackState.withRemovedCard(
                    CardRank.KING of CardSuite.HEARTS
                )
            ),

        originalGame move (CardRank.QUEEN of CardSuite.DIAMOND) from MoveSource.FromDeck to TableStackEntry.ONE
                test false withMessage "Invalid move of Queen from Deck to Empty Table Stack passes"
                expectGame originalGame,

        originalGame move (CardRank.THREE of CardSuite.CLOVER) from TableStackEntry.EIGHT to MoveDestination.ToFoundation
                test false withMessage "Invalid move of [Three of Clover] from table(is Hidden) to deck passes",

        originalGame move (CardRank.JUDGE of CardSuite.SPADE) from MoveSource.FromDeck to TableStackEntry.SEVEN
                test true withMessage "Move of [Judge of Spade] from deck to Table([Queen of Hearts] on top of stack)"
                expectGame originalGame
            .withRemoveFromDeck(CardRank.JUDGE of CardSuite.SPADE)
            .withTableStack(
                TableStackEntry.SEVEN, originalGame.seventhTableStackState.withAddedCard(
                    CardRank.JUDGE of CardSuite.SPADE
                )
            ),

        originalGame move listOf(
            CardRank.FIVE of CardSuite.CLOVER,
            CardRank.FOUR of CardSuite.HEARTS
        ) from TableStackEntry.FIVE to TableStackEntry.THREE
                test true withMessage "Move of [Five of Clover, Four of Spade] from Table([SIX of HEARTS, FIVE of CLOVER, FOUR of HEARTS]) to Table([EIGHT of DIAMOND, SEVEN of CLOVER, SIX of DIAMOND]) fails"
                expectGame originalGame
            .withTableStack(
                TableStackEntry.FIVE, originalGame.fifthTableStackState.withRemovedCards(
                    listOf(
                        CardRank.FIVE of CardSuite.CLOVER, CardRank.FOUR of CardSuite.HEARTS
                    )
                )
            )
            .withTableStack(
                TableStackEntry.THREE, originalGame.thirdTableStackState.withAddedCards(
                    listOf(
                        CardRank.FIVE of CardSuite.CLOVER, CardRank.FOUR of CardSuite.HEARTS
                    )
                )
            ),

        originalGame move originalGame.eighthTableStackState.revealedCards from TableStackEntry.EIGHT to TableStackEntry.FOUR
                test true withMessage "Move of [NINE of DIAMOND, EIGHT of SPADE, SEVEN of HEARTS] to Table([KING of DIAMOND, QUEEN of CLOVER, JUDGE of DIAMOND, TEN of SPADE]) fails"
                expectGame originalGame
            .withTableStack(
                TableStackEntry.EIGHT,
                originalGame.eighthTableStackState.copy(revealedCards = emptyList())
            )
            .withTableStack(
                TableStackEntry.FOUR, originalGame.fourthTableStackState.withAddedCards(
                    listOf(
                        CardRank.NINE of CardSuite.DIAMOND,
                        CardRank.EIGHT of CardSuite.SPADE,
                        CardRank.SEVEN of CardSuite.HEARTS
                    )
                )
            ),

        originalGame move originalGame.seventhTableStackState.revealedCards from TableStackEntry.SEVEN to TableStackEntry.ONE
                test true withMessage "Move of [KING of SPADE, QUEEN of HEARTS] to Empty Table fails"
                expectGame originalGame
            .withTableStack(TableStackEntry.SEVEN, TableStack.EMPTY)
            .withTableStack(
                TableStackEntry.ONE,
                TableStack(
                    revealedCards = listOf(
                        CardRank.KING of CardSuite.SPADE,
                        CardRank.QUEEN of CardSuite.HEARTS
                    )
                )
            )
    )
)