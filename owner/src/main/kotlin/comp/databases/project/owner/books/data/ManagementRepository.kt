package comp.databases.project.owner.books.data

import comp.databases.project.shared.books.model.BookDetail

interface ManagementRepository {
    fun addBook(book: BookDetail): Boolean

    fun removeBook(isbn: String): Boolean
}