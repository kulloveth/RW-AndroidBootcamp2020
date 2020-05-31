data class Card(val pip: String, val suit: Char)

val createDeck = fun(): MutableSet<Card> {
    val suits = listOf('\u2663', '\u2660', '\u2666', '\u2665')
    val pips = listOf(
        "J", "Q", "K", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "J", "Q", "K", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "J", "Q", "K", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "J", "Q", "K", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10"
    )
    val deck = mutableSetOf<Card>()
    for (p in pips.indices) {
        for (s in suits.indices) {
            deck.add(Card(pips[p], suits[s]))
        }
    }
    return deck
}

val dealHand = fun(deck: MutableSet<Card>, noOfCards: Int): MutableList<Card> {
    val hand = mutableListOf<Card>()
    for (i in 1..noOfCards) {
        val card = deck.random()
        hand.add(card)
        deck.remove(card)
    }
    return hand
}
val evaluateHand = fun(hand: MutableList<Card>): Int {
    var total = 0
    for (card in hand) {
        val pipValue = when (val pip = card.pip) {
            "J",
            "Q",
            "K" -> 10
            "A" -> 11
            else -> pip.toInt()
        }
        total += pipValue
    }
    return total
}
val printResult = fun(hand: MutableList<Card>, total: Int) {
    var message = ""
    val cards: ArrayList<String> = arrayListOf()
    for (card in hand) {
        cards.add("Your Hand was ${card.pip} ${card.suit}")
        message = when (total) {
            21 -> "You are a winner"
            22 -> "You Lose!"
            else -> ""
        }
    }
    cards.forEach {
        println(it)
    }
    println(
        "For a total of $total \n" +
                " $message"
    )
}
