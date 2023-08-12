package com.github.bkmbigo.solitaire.game.utils

import com.github.bkmbigo.solitaire.models.core.CardRank

/** Returns true if the current rank is immediately lower than [other]
 * @return Boolean
 */
fun CardRank.isImmediatelyLowerTo(other: CardRank) =
    this.ordinal + 1 == other.ordinal

/** Returns true if the current rank is immediately lower than [other]
 * @return Boolean
 */
fun CardRank.isImmediatelyUpperTo(other: CardRank) =
    this.ordinal - 1 == other.ordinal

/**
 * Returns true if [other] is adjacent to current. TWO is adjacent to ACE and THREE only
 * @param other Card rank to compare to
 * @return true - if the [other] is adjacent to [this]*/
fun CardRank.isAdjacentTo(other: CardRank) =
    this.ordinal + 1 == other.ordinal