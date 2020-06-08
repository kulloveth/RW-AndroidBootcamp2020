import cafee.Cafe

fun main() {
    val cafe = Cafe()
    val items = listOf(DummyItems.salad,DummyItems.friedChicken)
    cafe.getReceipt(items,DummyItems.chidi.id)
    //cafe.printReceiptForTheDay("Wednesday")

   // println(DummyItems.chris.clockIn())
    println(cafe.getAdopters())
}