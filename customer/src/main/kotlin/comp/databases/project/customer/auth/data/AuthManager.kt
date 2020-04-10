package comp.databases.project.customer.auth.data

interface AuthManager {
    val customer: Customer?

    val isAuthenticated: Boolean
        get() = customer != null

    fun authenticate(email: String, password: String): Boolean

    fun logout()
}
