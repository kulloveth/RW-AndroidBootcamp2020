
class Test {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val deal = dealHand(createDeck(), 2)
            val evaluate = evaluateHand(deal)
            printResult(deal, evaluate)

        }
    }
}
