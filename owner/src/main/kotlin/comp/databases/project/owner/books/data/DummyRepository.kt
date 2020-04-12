package comp.databases.project.owner.books.data

import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.reports.model.Report

object DummyRepository : ManagementRepository {
    override fun addBook(book: BookDetail): Boolean {
        println(book)
        return true
    }

    override fun removeBook(isbn: String): Boolean {
        println("Remove $isbn")
        return true
    }

    override fun getReport(month: Int, year: Int): Report {
        return Report(
            month, year, listOf(
                Report.LineItem("Sales", 9999.0),
                Report.LineItem("Expenses", -4555.24)
            ), listOf(
                Report.Category(
                    "Genre", listOf(
                        Report.LineItem("Calendars", 8888.0),
                        Report.LineItem("Magic", 10.0),
                        Report.LineItem("Sci-Fi", 8.0)
                    )
                ),
                Report.Category(
                    "Author", listOf(
                        Report.LineItem("J. K. Rowling", 1_000_000.0),
                        Report.LineItem("J.-R. Sack", 15.99),
                        Report.LineItem("J. R. R. Tolkien", 10.0)
                    )
                )
            )
        )
    }
}