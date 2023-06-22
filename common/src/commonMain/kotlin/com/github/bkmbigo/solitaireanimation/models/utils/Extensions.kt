package com.github.bkmbigo.solitaireanimation.models.utils

import com.github.bkmbigo.solitaireanimation.models.CardRank
import kotlin.math.abs

fun CardRank.isAdjacent(other: CardRank): Boolean =
    abs(ordinal - other.ordinal) == 1

fun CardRank.isJustLowerRanked(other: CardRank): Boolean =
    other.ordinal - ordinal == 1

fun CardRank.isJustUpperRanked(other: CardRank): Boolean =
    ordinal - other.ordinal == 1