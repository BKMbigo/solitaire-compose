package com.github.bkmbigo.solitaire.presentation.solitaire.layouts.utils

import com.github.bkmbigo.solitaire.presentation.solitaire.layouts.SolitaireGameLayout

/** Used to tag and identify various components on [SolitaireGameLayout] */
enum class SolitaireLayoutId {
    /** Tags the empty deck composable */
    EMPTY_DECK,

    /** Tags the overlay placed on top oof deck cards */
    DECK_OVERLAY,

    /** Tags all cards on deck */
    DECK_CARD,

    /** Tags the empty spade foundation composable  */
    EMPTY_FOUNDATION_SPADE,

    /** Tags the empty clover foundation composable  */
    EMPTY_FOUNDATION_CLOVER,

    /** Tags the empty hearts foundation composable  */
    EMPTY_FOUNDATION_HEARTS,

    /** Tags the empty diamond foundation composable  */
    EMPTY_FOUNDATION_DIAMOND,

    /** Tags all cards on spade foundation */
    FOUNDATION_SPADE,

    /** Tags all cards on clover foundation */
    FOUNDATION_CLOVER,

    /** Tags all cards on hearts foundation */
    FOUNDATION_HEARTS,

    /** Tags all cards on diamond foundation */
    FOUNDATION_DIAMOND,

    /** Tags all cards on the first table stack */
    FIRST_TABLE_STACK,

    /** Tags all cards on the second table stack */
    SECOND_TABLE_STACK,

    /** Tags all cards on the third table stack */
    THIRD_TABLE_STACK,

    /** Tags all cards on the fourth table stack */
    FOURTH_TABLE_STACK,

    /** Tags all cards on the fifth table stack */
    FIFTH_TABLE_STACK,

    /** Tags all cards on the sixth table stack */
    SIXTH_TABLE_STACK,

    /** Tags all cards on the seventh table stack */
    SEVENTH_TABLE_STACK
}
