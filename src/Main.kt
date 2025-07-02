//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {


    val system = TicketBookingSystem()

    while (true) {

        println(

        """
             Railway Reservation System
            1. Book Ticket
            2. Cancel Ticket
            3. Print Booked Tickets
            4. Print Available Tickets
            5. Exit
        """.trimIndent()
        )

        print("Enter your choice: ")

        when(readLine()!!.toInt()) {
            1 -> {
                print("Name: ")
                val name = readLine()!!
                print("Age: ")
                val age = readLine()!!.toInt()
                print("Gender (M/F): ")
                val gender = readLine()!!
                print("Berth Preference (L/M/U): ")
                val berth = readLine().toString()

                val passenger = Passenger(name,age,gender, berth)
                system.bookTicket(passenger)
            }

            2 -> {
                print("Enter passenger name to cancel : ")
                val name = readLine()!!
                system.cancelTicket(name)
            }

            3 -> system.printBookedTickets()
            4 -> system.printAvailableTickets()

            5 -> {
                println("Exiting...")
                return
            }

            else -> println("Invalid Choice")
        }
    }
}