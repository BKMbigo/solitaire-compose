package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.models.Card

fun Card.canBePlacedBelow(stackCard: Card): Boolean = stackCard.rank - rank == 1

