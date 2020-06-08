package cafee.product

data class Item(
    val id: Int,
    val desc: String,
    val quantity: Int = 0,
    var price: Double
)