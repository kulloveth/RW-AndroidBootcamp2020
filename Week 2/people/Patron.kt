package people

import cafee.product.Cat

class Patron(
    firstName: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    cats: MutableSet<Cat> = mutableSetOf()
) : Person(firstName = firstName, lastName = lastName, email = email, phoneNumber = phoneNumber, cats = cats) {
    val fullName = "$firstName $lastName"

    override fun toString(): String {
        return "Patron(fullName='$fullName')"
    }


}