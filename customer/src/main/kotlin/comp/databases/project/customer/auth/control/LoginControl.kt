package comp.databases.project.customer.auth.control

import comp.databases.project.customer.auth.data.AuthManager
import comp.databases.project.shared.Control
import comp.databases.project.shared.books.data.Address

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
    var validNum = false
    var expiryMonth = 0
    var expiryYear = 0
    var cvcCode = 0

    while (attempts < 2) {
        view.print("Email: ")
        val email = view.readLine() ?: ""
        view.print("Password: \r")
        val password = if (System.console() != null) view.readPassword() else view.readLine()
        view.print("Credit card number: ")
        val cardNum = view.readLine() ?: ""
        while (!validNum) {
            view.print("Expiry month (in form MM): ")
            try {
                val temp = view.readLine() ?: ""
                expiryMonth = temp.toInt()
                validNum = true
            } catch (e: Exception) {
                view.print("Please print in a numeric format \n ")
            }
        }
        validNum = false
        while (!validNum) {
            view.print("Expiry year (in form YYYY): ")
            try {
                val temp = view.readLine() ?: ""
                expiryYear = temp.toInt()
                validNum = true
            } catch (e: Exception) {
                view.print("Please print in a numeric format \n ")
            }
        }
        validNum = false
        while (!validNum) {
            view.print("CVC code: ")
            try {
                val temp = view.readLine() ?: ""
                cvcCode = temp.toInt()
                validNum = true
            } catch (e: Exception) {
                view.print("Please print in a numeric format \n ")
            }
        }
        validNum = false
        view.print("Street number: ")
        val streetNum = view.readLine() ?: ""
        view.print("Street name: ")
        val streetName = view.readLine() ?: ""
        view.print("Postal code: ")
        val postalCode = view.readLine() ?: ""
        view.print("City: ")
        val city = view.readLine() ?: ""
        view.print("Country: ")
        val country = view.readLine() ?: ""

        if (authManager.newUser(
                email,
                password ?: "",
                cardNum,
                expiryMonth,
                expiryYear,
                cvcCode,
                Address(streetNum, streetName, postalCode, city, country)
            )
        ) {
            return
        } else {
            view.printerrln("Wrong email or password.\n")
            attempts++
        }
    }

    view.printerrln("User with this email address already exists\n")
}
