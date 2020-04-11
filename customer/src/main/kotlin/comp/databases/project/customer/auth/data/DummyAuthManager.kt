package comp.databases.project.customer.auth.data

import comp.databases.project.shared.books.data.Customer

object DummyAuthManager : AuthManager {
    override var customer: Customer? = null
        private set

    override fun authenticate(email: String, password: String): Boolean {


        return if (email == "test@books.ca" && password == "password") {
            customer = Customer(email, password, "111111111111", 2,2022, 111, "123", "main st", "K1S 5L7")
            true
        } else {
            false
        }
    }

    override fun new_user(
        email: String,
        password: String,
        card_num: String,
        expiry_month: Int,
        expiry_year: Int,
        cvc_code: Int,
        street_num: String,
        street_name: String,
        postal_code: String,
        city: String,
        country: String
    ): Boolean{ return true }



    override fun logout() {
        customer = null
    }
}