class TicketBookingSystem {

    private val confirmedSeats = 3
    private val racSeats = 2
    private val waitingListSeats = 1

    private var availableConfirmed = 3
    private var availableRAC = 2
    private var availableWaitingList = 1

    private val lowerBerths = mutableListOf("L1","L2","L3","L4","L5","L6")
    private val middleBerths = mutableListOf("M1","M2","M3","M4","M5","M6")
    private val upperBerths = mutableListOf("U1","U2","U3","U4","U5","U6")
    private val sideLowerBerths = mutableListOf("SL1","SL2","SL3")

    private val bookedTickets = mutableListOf<Passenger>()
    private val racList = mutableListOf<Passenger>()
    private val waitingList = mutableListOf<Passenger>()
    private val childrenList = mutableListOf<Passenger>()

    fun bookTicket(passenger: Passenger) {
        if (passenger.age < 5) {
            println("Child below 5 years not allotted a ticket, but data is stored.")
            childrenList.add(passenger)
            return
        }

        when {
            availableConfirmed > 0 -> {
                allocateBerth(passenger)
                passenger.ticketStatus = "Confirmed"
                bookedTickets.add(passenger)
                availableConfirmed--
                println("Ticket Booked Successfully (Confirmed)")
            }
            availableRAC > 0 -> {
                passenger.allottedBert = "Side Lower"
                passenger.ticketStatus = "RAC"
                racList.add(passenger)
                availableRAC--
                println("Ticket Booked as RAC")
            }

            availableWaitingList > 0 -> {
                passenger.allottedBert = "None"
                passenger.ticketStatus = "Waiting"
                waitingList.add(passenger)
                availableWaitingList--
                println("Ticket Booked as Waiting List")
            }
            else -> println("No tickets available")
        }
    }

    private fun allocateBerth(passenger: Passenger) {
        // special cases

        if (passenger.age > 60 && lowerBerths.isNotEmpty()) {
            passenger.allottedBert = lowerBerths.removeAt(0)
            return
        }

        if (passenger.gender == "F" && passenger.age >= 18 && lowerBerths.isNotEmpty()) {
            passenger.allottedBert = lowerBerths.removeAt(0)
            return
        }

        // Berth preference

        when(passenger.berthPreference) {
            "L" -> if (lowerBerths.isNotEmpty()) {
                passenger.allottedBert = lowerBerths.removeAt(0)
                return
            }

            "M" -> if (middleBerths.isNotEmpty()) {
                passenger.allottedBert = middleBerths.removeAt(0)
                return
            }

            "U" -> if (upperBerths.isNotEmpty()) {
                passenger.allottedBert = upperBerths.removeAt(0)
                return
            }
        }

        // if preferred not available, give any

        when {
            lowerBerths.isNotEmpty() -> passenger.allottedBert = lowerBerths.removeAt(0)
            middleBerths.isNotEmpty() -> passenger.allottedBert = middleBerths.removeAt(0)
            upperBerths.isNotEmpty() -> passenger.allottedBert = upperBerths.removeAt(0)
        }

    }

    fun cancelTicket(name: String) {
        val passenger = bookedTickets.find { it.name == name }

        if (passenger != null) {
            // Free the berth
            freeBerth(passenger.allottedBert)

            bookedTickets.remove(passenger)
            availableConfirmed++

            // promote from RAC to Confirmed

            if (racList.isNotEmpty()) {
                val racPassenger = racList.removeAt(0)
                availableRAC++

                allocateBerth(racPassenger)
                racPassenger.ticketStatus = "Confirmed"
                bookedTickets.add(racPassenger)
                availableConfirmed--

                // Promote from waiting to RAC

                if (waitingList.isNotEmpty()) {
                    val waitPassenger = waitingList.removeAt(0)
                    waitPassenger.allottedBert = "Side Lower"
                    waitPassenger.ticketStatus = "RAC"
                    racList.add(waitPassenger)
                    availableWaitingList++
                    availableRAC--
                }
            }

            println("Ticket Cancelled Successfully")
        } else {
            println("No such booking found!")
        }
    }

    private fun freeBerth(berth: String) {
        when {
            berth.startsWith("L") -> lowerBerths.add(berth)
            berth.startsWith("M") -> middleBerths.add(berth)
            berth.startsWith("U") -> upperBerths.add(berth)
        }
    }

    fun printBookedTickets() {
        println("\n --- Booked Tickets ---")
        if (bookedTickets.isEmpty()) {
            println("No confirmed tickets.")
        }

        var pNumber = 1 // This is the number that increments, if the booked users are.. blah.. blah.. you can see in the code.
        for (p in bookedTickets) {
            println("${pNumber} :  ${p.name} | Age: ${p.age} | Gender: ${p.gender} | Berth: ${p.allottedBert} | Status: ${p.ticketStatus}" )
            pNumber++
        }

        println("Total Booked: ${bookedTickets.size}")
        println("RAC: ${racList.size}, Waiting Lists: ${waitingList.size}")

    }

    fun printAvailableTickets() {
        println("\n--- Available Tickets ---")
        println("Confirmed Tickets Left: $availableConfirmed")
        println("RAC Tickets Left: $availableRAC")
        println("Waiting List Tickets Left: $availableWaitingList")
    }

}