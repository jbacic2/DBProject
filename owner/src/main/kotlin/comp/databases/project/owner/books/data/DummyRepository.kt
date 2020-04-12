package comp.databases.project.owner.books.data

import comp.databases.project.shared.books.model.BookDetail

object DummyRepository : ManagementRepository {
    override fun addBook(book: BookDetail): Boolean {
        println(book)
        return true
    }

    override fun removeBook(isbn: String): Boolean {
        println("Remove $isbn")
        return true
    }
}