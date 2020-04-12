package comp.databases.project.customer.auth.view

import com.jakewharton.picnic.table
import comp.databases.project.shared.View
import comp.databases.project.shared.books.data.Customer

fun View.printCustomer(customer: Customer) {
    val table = table {
        cellStyle {
            border = true
            paddingLeft = 1
            paddingRight = 1
        }

        row {
            cell(customer.email) {
                columnSpan = 4
            }
        }

        val (num, name, postal, city, country) = customer.address
        row {
            cell("Billing Address")
            cell("$num $name, $city, $country, $postal") {
                columnSpan = 3
            }
        }
        row(
            "Credit Card #",
            "${"*".repeat(customer.card_num.length - 4)}${customer.card_num.substring(
                customer.card_num.length - 4,
                customer.card_num.length
            )}",
            "Expiry Date",
            "${customer.expiry_month}/${customer.expiry_year}"
        )
    }

    println("$table")
}