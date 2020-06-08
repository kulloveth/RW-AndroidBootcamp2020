package people

import java.text.SimpleDateFormat
import java.time.LocalDateTime

class Employee(
    firstName: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    val salary: Double,
    val socialSecurityNumber: String,
    val hireDate: String
) : Person(firstName = firstName, lastName = lastName, email = email, phoneNumber = phoneNumber) {

    val fullName = "$firstName $lastName"

    /*
    * Creates time When user clocked in
    * and formats nicely with simpledateformatter
    * */
    fun clockIn(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val time = LocalDateTime.now().toString()
        return formatter.format(parser.parse(time))
    }

    fun clockOut() {

    }

    override fun toString(): String {
        return "Employee(salary=$salary, socialSecurityNumber='$socialSecurityNumber', hireDate='$hireDate')"
    }
}

  fun isEmploye(id: String): Boolean {
    return false
}