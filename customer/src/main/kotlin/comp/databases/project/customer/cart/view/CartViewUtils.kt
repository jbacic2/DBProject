package comp.databases.project.customer.cart.view

import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.table
import comp.databases.project.shared.View
import comp.databases.project.shared.cart.model.Cart
import comp.databases.project.shared.cart.model.Order
import org.davidmoten.text.utils.WordWrap
import java.text.NumberFormat
import java.util.*

private const val MAX_TITLE_WIDTH = 60

fun View.printCart(cart: Cart) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val totalPrice = cart.items.fold(0.0) { acc, (book, quantity) -> acc + book.price * quantity }

    val cartTable = table {
        cellStyle {
            border = true
            paddingLeft = 1
            paddingRight = 1
        }

        header {
            row {
                cell("Cart") {
                    columnSpan = 3
                }
                cell("Id: ${cart.id}") {
                    borderLeft = false
                    columnSpan = 2
                }
            }
            row {
                cell("#ID")
                cell("Title")
                cell("ISBN")
                cell("Quantity #")
                cell("Price $")
            }
        }

        cart.items.forEachIndexed { index, (book, quantity) ->
            val displayTitle = WordWrap
                .from(book.title)
                .maxWidth(MAX_TITLE_WIDTH)
                .wrap()

            row {
                cell(index) {
                    alignment = TextAlignment.MiddleCenter
                }
                cell(displayTitle)
                cell(book.isbn)
                cell("x$quantity") {
                    alignment = TextAlignment.MiddleRight
                }
                cell(formatter.format(book.price * quantity)) {
                    alignment = TextAlignment.MiddleRight
                }
            }
        }

        footer {
            row {
                cell("Total $:") {
                    columnSpan = 4
                }
                cell(formatter.format(totalPrice)) {
                    alignment = TextAlignment.MiddleRight
                }
            }
        }
    }

    println("$cartTable")
}

fun View.printOrders(orders: List<Order>) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.CANADA)

    val ordersTable = table {
        cellStyle {
            border = true
            paddingLeft = 1
            paddingRight = 1
        }

        header {
            row {
                cell("Orders") {
                    columnSpan = 5
                }
            }
            row {
                cell("ID")
                cell("Details") {
                    columnSpan = 4
                }
            }
        }

        orders.forEach { order ->
            row {
                cell(order.id) {
                    rowSpan = 4 + order.items.size
                }
                cell("Order Placed: ${order.purchaseDay}/${order.purchaseMonth}/${order.purchaseYear}") {
                    columnSpan = 2
                }
                cell("Status: ${order.status.asString()}") {
                    columnSpan = 2
                }
            }
            row {
                val (num, street, postalCode, city, country) = order.shippingAddress
                cell("Ship to: $num $street, $city, $country, $postalCode") {
                    columnSpan = 4
                }
            }

            row("Title", "ISBN", "Quantity #", "Price $")
            val totalPrice = order.items.fold(0.0) { acc, (book, quantity) -> acc + book.price * quantity }
            order.items.forEach { (book, quantity) ->
                val displayTitle = WordWrap
                    .from(book.title)
                    .maxWidth(MAX_TITLE_WIDTH)
                    .wrap()

                row {
                    cell(displayTitle)
                    cell(book.isbn)
                    cell("x$quantity") {
                        alignment = TextAlignment.MiddleRight
                    }
                    cell(formatter.format(book.price * quantity)) {
                        alignment = TextAlignment.MiddleRight
                    }
                }
            }
            row {
                cell("Total $:") {
                    columnSpan = 3
                }
                cell(formatter.format(totalPrice)) {
                    alignment = TextAlignment.MiddleRight
                }
            }
        }

        if (orders.isEmpty()) {
            footer {
                row {
                    cell("No orders to display.") {
                        columnSpan = 5
                    }
                }
            }
        }
    }

    println("$ordersTable")
}