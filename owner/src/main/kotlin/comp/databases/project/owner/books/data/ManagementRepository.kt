package comp.databases.project.owner.books.data

import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.reports.model.Report

interface ManagementRepository {
    fun addBook(book: BookDetail): Boolean

    fun removeBook(isbn: String): Boolean

    fun getReport(month: Int, year: Int): Report
}