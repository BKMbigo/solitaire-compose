package com.github.bkmbigo.solitaire.game.solitaire.configuration

import com.github.bkmbigo.solitaire.game.GameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove

/** The configuration of a [SolitaireGame] */
data class SolitaireGameConfiguration(
    /** The number of cards dealt on a single deal */
    val cardsPerDeal: SolitaireCardsPerDeal
): GameConfiguration<SolitaireGame, SolitaireGameMove>
