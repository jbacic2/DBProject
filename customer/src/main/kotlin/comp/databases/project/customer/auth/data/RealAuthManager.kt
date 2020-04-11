package comp.databases.project.customer.auth.data

import comp.databases.project.shared.books.data.BookDatabase
import comp.databases.project.shared.books.data.Customer

object RealAuthManager : AuthManager {
    override var customer: Customer? = null
        private set

    override fun authenticate(email: String, password: String): Boolean {
        customer = BookDatabase.authenticate(email,password);
        if (customer == null){
            return false;
        }
        return true;
    }

    override fun logout() {
        customer = null
    }
}