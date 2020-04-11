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

    override fun new_user (email: String, password: String, card_num : String, expiry_month : Int, expiry_year : Int, cvc_code : Int, street_num: String, street_name : String, postal_code : String, city: String, country: String): Boolean{
        customer = BookDatabase.new_user(email, password, card_num, expiry_month, expiry_year, cvc_code, street_num, street_name, postal_code, city, country);
        if (customer == null){
            return false;
        }
        return true;
    }


    override fun logout() {
        customer = null
    }
}