/**
 * A data class to represent each card with [pip] and [suit]
 * @param pip represents the numeric or face value of the card
 * @param suit represents the 5 suits in a card
 *
 */

data class Card(val pip: String, val suit: Char)


/*
* THis method creates a deck as a MutableSet using the Card class
*
* */
fun createDeck(): MutableSet<Card> {
    val suits = listOf('\u2663', '\u2660', '\u2666', '\u2665')
    val pips = listOf("J", "Q", "K", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    val deck = mutableSetOf<Card>()

    //loop through the suits and pips providing a suit for each pip
    for (p in pips.indices) {
        for (s in suits.indices) {
            deck.add(Card(pips[p], suits[s]))
        }
    }
    return deck
}

/**
 * dealHand method to deal an initial hand of card with params [deck] and [noOfCards]
 * @param noOfCards cards are dealt from
 * @param deck randomly
 */
fun dealHand(deck: MutableSet<Card>, noOfCards: Int): MutableList<Card> {
    val hand = mutableListOf<Card>()
    for (i in 1..noOfCards) {
        val card = deck.random()
        hand.add(card)
        deck.remove(card)
    }
    return hand
}


fun evaluateHand(hand: MutableList<Card>): Int {
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

/**
 * printResult prints dealt card
 * @param hand with
 * @param total of pips
 * */
fun printResult(hand: MutableList<Card>, total: Int) {
    var message = ""
    val cards: ArrayList<String> = arrayListOf()
    for (card in hand) {
        cards.add(
            """
.-------.                                             
|${card.pip}          
|        |
|    ${card.suit}                  
|        |
|        |                                  
|        |     
'--------' """.trimIndent()
        )
        message = when (total) {
            21 -> "You are a winner"
            22 -> "You Lose!"
            else -> ""
        }
    }
    println("Your Hand Was")
    cards.forEach {
        print("$it \n")
    }
    println(
        "For a total of $total \n" +
                " $message"
    )
}
