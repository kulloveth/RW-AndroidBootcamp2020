package people

import cafee.product.Cat
import java.util.*

open class Person(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val cats: MutableSet<Cat> = mutableSetOf()
) {
    override fun toString(): String {
        return "Person(id='$id', firstName='$firstName', lastName='$lastName', phoneNumber='$phoneNumber', email='$email', cats=$cats)"
    }
}