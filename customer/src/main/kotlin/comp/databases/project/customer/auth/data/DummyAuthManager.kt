package comp.databases.project.customer.auth.data

import comp.databases.project.shared.books.data.Customer

object DummyAuthManager : AuthManager {
    override var customer: Customer? = null
        private set

    override fun authenticate(email: String, password: String): Boolean {


        return if (email == "test@books.ca" && password == "password") {
            customer = Customer(email, password, 111111111, 2,2022, 111)
            true
        } else {
            false
        }
    }

    override fun logout() {
        customer = null
    }
}