package comp.databases.project.customer.books.view

import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.table
import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.View
import org.davidmoten.text.utils.WordWrap
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

fun View.printBookDetail(book: BookDetail) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val table = table {
        cellStyle {
            border = true
            paddingLeft = 1
            paddingRight = 1
        }

        row {
            cell(book.detail.title) {
                columnSpan = 3
            }
        }

        row {
            val authors = WordWrap.from("By: ${book.authors.joinToString(separator = ", ") { (name) -> name }}")
                .maxWidth(62)
                .wrap()
            cell(authors) {
                columnSpan = 2
            }
            cell("Price: ${formatter.format(book.detail.price)}")
        }

        row {
            cell(book.detail.genre)
            cell("Pages: ${book.detail.pages}")
            cell("Stock: ${book.detail.stock}")
        }

        row {
            cell("Published By: ${book.detail.publisher}") {
                columnSpan = 2
            }
            cell("ISBN: ${book.detail.isbn}")
        }

        book.detail.synopsis?.let { synopsis ->
            // Wrap the word to 100 characters wide
            val wrapped = WordWrap.from(synopsis)
                .maxWidth(100)
                .insertHyphens(true)
                .wrap()

            row {
                cell(wrapped) {
                    columnSpan = 3
                }
            }
        }
    }

    println("$table")
}