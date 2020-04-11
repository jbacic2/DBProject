package comp.databases.project.customer.auth.data

import comp.databases.project.shared.books.data.Address
import comp.databases.project.shared.books.data.Customer

object DummyAuthManager : AuthManager {
    override var customer: Customer? = null
        private set

    override fun authenticate(email: String, password: String): Boolean {
        return if (email == "test@books.ca" && password == "password") {
            customer = Customer(
                email,
                password,
                "111111111111",
                2,
                2022,
                111,
                Address("123", "main st", "K1S 5L7", "Ottawa", "Canada")
            )
            true
        } else {
            false
        }
    }

    override fun newUser(
        email: String,
        password: String,
        card_num: String,
        expiry_month: Int,
        expiry_year: Int,
        cvc_code: Int,
        address: Address
    ): Boolean {
        return true
    }


    override fun logout() {
        customer = null
    }
}