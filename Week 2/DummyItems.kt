import cafee.Receipt
import cafee.product.Cat
import cafee.product.Item
import people.Employee
import people.Patron
import shelter.Shelter

class DummyItems {

    companion object {


        //Cats
        val carol = Cat(id = 911, name = "Lewis Carol", color = "purple", sex = "male", breed = "CHESHIRE")

        val orangey = Cat(id = 929, name = "Orangey", breed = "marmalade", color = "orange", sex = "male")

        val choupe = Cat(id = 918, name = "Choupette", color = "brown", breed = "birman", sex = "female")

        val morris = Cat(id = 456, name = "Morris", color = "blue", breed = "tabby tomcat", sex = "male")

        val cheetah = Cat(id = 457, name = "Cheetah", color = "silver", breed = "Toyger", sex = "female")

        //Patrons
        val john =
            Patron(email = "jparker@razeware.com", firstName = "John", lastName = "Parker", phoneNumber = "0123675787")

        val chidi =
            Patron(
                email = "cloveth@gmail.com",
                firstName = "Chidi",
                lastName = "Loveth",
                phoneNumber = "3445786298",
                cats = mutableSetOf(
                    carol
                )
            )

        val doe = Patron(email = "jdoe@gmail.com", firstName = "John", lastName = "Doe", phoneNumber = "6745893645")


        //Employees
        val chris = Employee(
            email = "cmike@yahoo.com", firstName = "Chris", lastName = "Mike",
            phoneNumber = "7846574889", hireDate = "02-01-2020", salary = 1000.00, socialSecurityNumber = "456-89-0987"
        )

        val james = Employee(
            email = "jLarry@yahoo.com", firstName = "James", lastName = "Larry",
            phoneNumber = "6848344989", hireDate = "02-01-2019", salary = 1500.00, socialSecurityNumber = "456-67-9879"
        )

        val richy = Employee(
            email = "rjeff@yahoo.com", firstName = "Richy", lastName = "Jeff",
            phoneNumber = "1948367989", hireDate = "07-10-2019", salary = 2500.00, socialSecurityNumber = "867-57-9879"
        )


        //Cafee Items
        val lemonande = Item(id = 323, desc = "lemonade", price = 7.5)

        val salad = Item(id = 324, desc = "Garden Salad", price = 9.5)

        val chrispyChicken = Item(id = 320, desc = "Chrispy Chicken", price = 50.0)

        val friedChicken = Item(id = 321, desc = "Fried Chicken", price = 150.0)

        val blackCoffee = Item(id = 322, desc = "Black Cofee", price = 20.0)

        //shelters

        val first_shelter =
            Shelter(
                shelter_id = 249, address = "21 Jump St, Albany NY 12203", phone = "7896576543", cats = listOf(
                    orangey, carol, choupe
                )
            )

        val second_shelter = Shelter(
            shelter_id = 242, address = "187 Biscayne Dr, Albany NY 12202", phone = "7893425678", cats = listOf(
                cheetah, morris
            )
        )

        val receiptOneItems = listOf(lemonande, salad)

        // sum all receiptOneItems
        val receiptOneTotal = receiptOneItems.sumByDouble { it.price }
        val receiptOne = Receipt(
            customerId = john.id, items = receiptOneItems
        )

        val receiptTwoItems = listOf(blackCoffee, chrispyChicken)

        // sum all receiptOneItems
        val receiptTwoTotal = receiptOneItems.sumByDouble { it.price }
        val receiptTwo = Receipt(
            customerId = chris.id, items = receiptTwoItems
        )

        val receiptThreeItems = listOf(blackCoffee, friedChicken)

        // sum all receiptOneItems
        val receiptThreeTotal = receiptThreeItems.sumByDouble { it.price }
        val receiptThree = Receipt(
            customerId = richy.id,
            items = receiptThreeItems
        )

        val receiptFourItems = listOf(salad, chrispyChicken)

        // sum all receiptOneItems
        val receiptFourTotal = receiptFourItems.sumByDouble { it.price }
        val receiptFour = Receipt(
            customerId = james.id,
            items = receiptFourItems
        )
    }


}