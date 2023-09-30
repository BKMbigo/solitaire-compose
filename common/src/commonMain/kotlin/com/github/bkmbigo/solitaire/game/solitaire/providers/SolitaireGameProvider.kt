package com.github.bkmbigo.solitaire.game.solitaire.providers

import com.github.bkmbigo.solitaire.game.GameProvider
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove

sealed class SolitaireGameProvider: GameProvider<SolitaireGame, SolitaireGameMove, SolitaireGameConfiguration>
