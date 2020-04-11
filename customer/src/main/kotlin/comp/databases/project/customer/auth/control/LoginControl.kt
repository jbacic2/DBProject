package comp.databases.project.customer.auth.control

import comp.databases.project.customer.auth.data.AuthManager
import comp.databases.project.shared.Control
import comp.databases.project.shared.View

fun Control.loginOperation(authManager: AuthManager) {
    var attempts = 0

    while (attempts < 2) {
        view.print("Email: ")
        val email = view.readLine() ?: ""
        view.print("Password: \r")
        val password = if (System.console() != null) view.readPassword() else view.readLine()

        if (authManager.authenticate(email, password ?: "")) {
            return
        } else {
            view.printerrln("Wrong email or password.\n")
            attempts++
        }
    }

    view.printerrln("Unable to log in.\n")
}

fun Control.addUserOperation(authManager: AuthManager) {
    var attempts = 0
    var valid_num = false
    var expiry_month = 0
    var expiry_year = 0
    var cvc_code = 0

    while (attempts < 2) {
        view.print("Email: ")
        val email = view.readLine() ?: ""
        view.print("Password: \r")
        val password = if (System.console() != null) view.readPassword() else view.readLine()
        view.print("Credit card number: ")
        val card_num = view.readLine() ?: ""
        while (!valid_num) {
            view.print("Expiry month (in form MM): ")
            try {
                val temp = view.readLine() ?: ""
                expiry_month = temp.toInt()
                valid_num = true
            } catch (e: Exception) {
                view.print("Please print in a numeric format \n ")
            }
        }
        valid_num = false
        while (!valid_num) {
            view.print("Expiry year (in form YYYY): ")
            try {
                val temp = view.readLine() ?: ""
                expiry_year = temp.toInt()
                valid_num = true
            } catch (e: Exception) {
                view.print("Please print in a numeric format \n ")
            }
        }
        valid_num = false
        while (!valid_num) {
            view.print("CVC code: ")
            try {
                val temp = view.readLine() ?: ""
                cvc_code = temp.toInt()
                valid_num = true
            } catch (e: Exception) {
                view.print("Please print in a numeric format \n ")
            }
        }
        valid_num = false
        view.print("Street number: ")
        val street_num = view.readLine() ?: ""
        view.print("Street name: ")
        val street_name = view.readLine() ?: ""
        view.print("Postal code: ")
        val postal_code = view.readLine() ?: ""
        view.print("City: ")
        val city = view.readLine() ?: ""
        view.print("Country: ")
        val country = view.readLine() ?: ""

        if (authManager.new_user(email, password?: "", card_num, expiry_month, expiry_year, cvc_code, street_num, street_name, postal_code, city, country)) {
            return
        } else {
            view.printerrln("Wrong email or password.\n")
            attempts++
        }

    }

    view.printerrln("User with this email address already exists\n")
}
