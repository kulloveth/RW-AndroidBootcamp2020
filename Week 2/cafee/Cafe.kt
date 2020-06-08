package cafee

import DummyItems
import cafee.product.Item
import people.Employee
import people.Person
import people.isEmploye
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class Cafe {

    private val receiptsByDay = mutableMapOf(
        "Monday" to mutableSetOf(DummyItems.receiptOne, DummyItems.receiptFour),
        "Tuesday" to mutableSetOf(DummyItems.receiptFour, DummyItems.receiptThree),
        "Wednesday" to mutableSetOf(DummyItems.receiptTwo, DummyItems.receiptThree),
        "Thursday" to mutableSetOf(DummyItems.receiptThree, DummyItems.receiptTwo),
        "Friday" to mutableSetOf(DummyItems.receiptOne, DummyItems.receiptThree),
        "Saturday" to mutableSetOf(DummyItems.receiptFour, DummyItems.receiptTwo),
        "Sunday" to mutableSetOf(DummyItems.receiptFour, DummyItems.receiptThree)
    )

    val sponsorship = mutableSetOf(
        Sponsorship("2454", "435"),
        Sponsorship("8764", "415"),
        Sponsorship("9087", "321"),
        Sponsorship("6785", "876"),
        Sponsorship("3324", "546")
    )

    val employee = mutableSetOf(DummyItems.chris, DummyItems.james, DummyItems.richy)
    val patron = mutableSetOf(DummyItems.doe, DummyItems.john, DummyItems.chidi)

    /**
     * checks no of receipt by [day]
     *
     * */
    fun showNumberOfReceiptsForDay(day: String) {
        val receiptForDay = receiptsByDay[day] ?: return println("You entered a wrong day")
        println("On $day you made ${receiptForDay.size} transactions!")
    }

    /**
     * Checks [items] by [customerId]
     * checks the price of each item
     * deducts 15% if the customer is an employee
     * */
    fun getReceipt(items: List<Item>, customerId: String): Receipt {
        items.forEach { item ->
            if (isEmploye(customerId) && !item.desc.contains("Cat")) {
                item.price = item.price * 0.15
            }
        }
        val receipt = Receipt(customerId = customerId, items = items)
        val today: String = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        receiptsByDay[today]?.add(receipt)
        return receipt
    }

    fun printReceiptForTheDay(day: String) {
        val receiptForDay = receiptsByDay[day] ?: return println("You entered a wrong day")
        println("Recipt for  $day is $receiptForDay ")

    }

    fun checkInEmployee(employee: Employee) {
        employee.clockIn()
    }

    fun checkOutEmployee(employee: Employee) {
        employee.clockOut()
    }

    /**
     * adds sponsorship with [catId] and [personId]
     * */
    fun addSponsorship(catId: String, personId: String) {
        sponsorship.add(Sponsorship(personId, catId))
    }

    fun getAdopters(): List<Person> {
        return (employee + patron).filter { it.cats.isNotEmpty() }
    }

    fun getWorkingEmployees(): Set<Employee> = employee


}