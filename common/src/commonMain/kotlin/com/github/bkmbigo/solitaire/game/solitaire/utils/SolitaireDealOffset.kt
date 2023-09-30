package com.github.bkmbigo.solitaire.game.solitaire.utils

/** An offset from the normal ordering of cards on deck. Caused by removal of a card from the deck */
/*
* This is majorly used in a TreeCardPerDeal game configuration. The ordering of the deck is as follows, depending on the DealOffset:
*   NONE ->
*       1, 2, 3
*       4, 5, 6 ...
*   ONE ->
*       1, 2
*       3, 4, 5
*       6, 7, 8 ...
*   TWO ->
*       1
*       2, 3, 4
*       5, 6, 7 ...
*
* For instance, in a ThreeCardsPerDeal game:
*   The normal ordering would be:
*       1, 2, 3 (Deal)
*       4, 5, 6 (Deal)
*       7, 8, 9
*
*   However, if a card is removed the following arrangement ensues:
*       1, 2, 3 (Deal)
*       4, 5, 6 (6 is removed) --> This calls for a bump on the DealOffset to ONE
*       4, 5    (Deal)
*       6, 7, 8
*
*       This helps during undo
*           6, 7, 8 (Undeal)
*           3, 4, 5 (replace the moved 6)
*           4, 5, 6 (Undeal)
*           1, 2, 3
*
*   The lifetime of a deal offset is only until the full deck is transversed, where the DealOffset is rolled back to NONE
*
*   A typical lifetime of a deal offset would look like:
*       1, 2, 3 (3 is removed, offset is bumped to ONE)
*       1, 2 (Deal)
*       3, 4, 5 (5 is removed, offset is bumped to TWO)
*       3, 4 (Deal)
*       5, 6, 7 (Deal)
*       8, 9, 10 (10 is removed, offset is bumped to NONE)
*       8, 9 (Deal)
*       10, 11, 12
*
*   During undo, the moves are interpreted as:
*       emptyList   (Undeal, with an offset of zero and a deck size of 12)
*       10, 11, 12  (Undeal)
*       7, 8, 9     (return card at 10, reduce offset to TWO)
*       8, 9, 10    (Undeal)
*       5, 6, 7     (Undeal)
*       2, 3, 4     (return card at 5, reduce offset to ONE)
*       3, 4, 5     (Undeal)
*       1, 2
* */
enum class SolitaireDealOffset {
    NONE,
    ONE,
    TWO;

    fun increase(): SolitaireDealOffset =
        when(this) {
            NONE -> ONE
            ONE -> TWO
            TWO -> NONE
        }

    fun decrease(): SolitaireDealOffset =
        when(this) {
            NONE -> TWO
            ONE -> ONE
            TWO -> NONE
        }
}
