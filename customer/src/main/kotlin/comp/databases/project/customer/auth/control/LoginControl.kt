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
            view.println("Wrong email or password.\n")
            attempts++
        }
    }

    view.println("Unable to log in.\n")
}
