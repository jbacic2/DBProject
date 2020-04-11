package comp.databases.project.shared.books.data

data class Customer(var email: String,
                    val password: String,
                    val card_num: Int,
                    val expiry_month: Int,
                    val expiry_year: Int,
                    val cvc_code: Int,
                    val street_num: Float,
                    val street_name: String,
                    val postal_code: String)

