package comp.databases.project.customer.auth.data

import comp.databases.project.shared.books.data.Address
import comp.databases.project.shared.books.data.BookDatabase
import comp.databases.project.shared.books.data.Customer

object RealAuthManager : AuthManager {
    override var customer: Customer? = null
        private set

    override fun authenticate(email: String, password: String): Boolean {
        customer = BookDatabase.authenticate(email, password)
        return customer != null
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
        customer = BookDatabase.new_user(email, password, card_num, expiry_month, expiry_year, cvc_code, address)
        return customer != null
    }


    override fun logout() {
        customer = null
    }
}