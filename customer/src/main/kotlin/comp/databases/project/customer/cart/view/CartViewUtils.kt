package comp.databases.project.customer.cart.view

import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.table
import comp.databases.project.shared.View
import comp.databases.project.shared.cart.model.Cart
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