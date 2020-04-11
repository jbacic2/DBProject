package comp.databases.project.customer.auth.data

import comp.databases.project.shared.books.data.Customer

interface AuthManager {
    val customer: Customer?

    val isAuthenticated: Boolean
        get() = customer != null

    fun authenticate(email: String, password: String):Boolean;
    fun new_user(email: String, password: String, card_num : String, expiry_month : Int, expiry_year : Int, cvc_code : Int, street_num: String, street_name : String, postal_code : String, city: String, country: String): Boolean

    fun logout()
}
