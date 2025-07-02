data class Passenger(

    val name: String,
    val age: Int,
    val gender: String,
    val berthPreference: String,
    var allottedBert: String = "None",
    var ticketStatus: String = "None" // confirmed, RAC, Waiting

)


