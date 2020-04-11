package comp.databases.project.customer.books.view

import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.table
import comp.databases.project.customer.books.data.model.Book
import comp.databases.project.customer.books.data.model.BookDetail
import comp.databases.project.shared.View
import java.text.NumberFormat
import java.util.*

fun View.printSearchResults(results: List<Book>) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val table = table {
        cellStyle {
            border = true
            paddingLeft = 1
            paddingRight = 1
        }

        row("#ID", "Title", "Price", "ISBN")

        results.forEachIndexed { index, book ->
            row {
                cell(index) {
                    alignment = TextAlignment.MiddleCenter
                }
                cell(book.title)
                cell(formatter.format(book.price))
                cell(book.isbn)
            }
        }
    }

    println("$table")
}

fun View.printBookDetail(detail: BookDetail) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val table = table {

    }
}