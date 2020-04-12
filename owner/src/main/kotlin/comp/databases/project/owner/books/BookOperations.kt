package comp.databases.project.owner.books

import comp.databases.project.owner.books.data.ManagementRepository
import comp.databases.project.shared.Control
import comp.databases.project.shared.books.model.Author
import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail

fun Control.addBookOperation(repository: ManagementRepository) {
    view.print("ISBN: ")
    val isbn = view.readLine() ?: return

    view.print("Title: ")
    val title = view.readLine() ?: return

    view.print("Genre: ")
    val genre = view.readLine() ?: return

    view.print("Cover Image URL (Enter blank value to skip): ")
    val coverImageUrl = view.readLine()?.ifBlank { null }

    view.print("Synopsis (Enter blank value skip): ")
    val synopsis = view.readLine()?.ifBlank { null }

    view.print("Number of Pages: ")
    val numPages = view.readLine()?.toIntOrNull() ?: return

    view.print("Price: ")
    val price = view.readLine()?.toDoubleOrNull() ?: return

    view.print("Stock: ")
    val stock = view.readLine()?.toIntOrNull() ?: return

    view.print("Publisher: ")
    val publisher = view.readLine() ?: return

    view.print("Percent of Sales: ")
    val percentOfSales = view.readLine()?.toDoubleOrNull() ?: return

    val book =
        Book(isbn, title, genre, coverImageUrl, synopsis, numPages, price, stock, publisher, percentOfSales, false)
    val authors = mutableListOf<Author>()
    while (true) {
        view.print("Author Name (Enter blank value to finish): ")
        val authorName = view.readLine()
        if (authorName.isNullOrBlank()) {
            break
        } else {
            authors.add(Author(authorName))
        }
    }

    if (repository.addBook(BookDetail(book, authors))) {
        view.println("Book added successfully.")
    } else {
        view.printerrln("Could not add book.")
    }
}

fun Control.removeBookOperation(repository: ManagementRepository, isbn: String) {
    if (repository.removeBook(isbn)) {
        view.println("Book removed successfully.")
    } else {
        view.printerrln("Could not remove book.")
    }
}