package comp.databases.project.shared.books.data

data class Customer(
    var email: String,
    val password: String,
    val card_num: String,
    val expiry_month: Int,
    val expiry_year: Int,
    val cvc_code: Int,
    val address: Address
)

data class Address(
    val streetNumber: String,
    val streetName: String,
    val postalCode: String,
    val city: String,
    val country: String
)