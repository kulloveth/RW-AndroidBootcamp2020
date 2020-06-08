package cafee

import cafee.product.Item
import kotlin.random.Random

class Receipt(
    val receipt_id: Int = Random(3).nextInt(),
    val customerId: String,
    val items: List<Item>
) {
    override fun toString(): String {
        return "Receipt(receipt_id=$receipt_id, customerId='$customerId', items=$items)"
    }
}