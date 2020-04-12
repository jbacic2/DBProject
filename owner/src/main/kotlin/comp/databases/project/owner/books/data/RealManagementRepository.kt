package comp.databases.project.owner.books.data

import comp.databases.project.shared.books.data.BookDatabase
import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.reports.model.Report

object RealManagementRepository : ManagementRepository {
    override fun addBook(book: BookDetail): Boolean = BookDatabase.addBook(book)

    override fun removeBook(isbn: String): Boolean = BookDatabase.removeBook(isbn)

    override fun getReport(month: Int, year: Int): Report {
        TODO("Not yet implemented")
    }
}