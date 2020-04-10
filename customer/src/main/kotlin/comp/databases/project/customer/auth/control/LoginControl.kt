package comp.databases.project.customer.auth.control

import comp.databases.project.customer.auth.data.AuthManager
import comp.databases.project.shared.Control
import comp.databases.project.shared.View


class LoginControl(private val authManager: AuthManager, view: View = View()) : Control(view) {
    private var email: String? = null

    override fun onInitialize() {
        view.prompt = ""
        view.print("Email: ")
    }

    override fun onCommand(args: List<String>): Boolean {
        email = args.joinToString()
        var attempts = 0

        while (attempts < 2) {
            view.print("Password: ")
            val password = if (System.console() != null) view.readPassword() else view.prompt()?.joinToString()

            if (authManager.authenticate(email!!, password ?: "")) {
                quit()
                return true
            } else {
                view.println("Wrong email or password.\n")
                attempts++
            }
        }

        view.println("Unable to login.")
        quit()
        return true
    }

    override fun onQuit() {
        view.resetPrompt()
    }
}