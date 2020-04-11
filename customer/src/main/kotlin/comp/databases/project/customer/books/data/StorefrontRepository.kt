package comp.databases.project.customer.books.data

import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail

interface StorefrontRepository {
    fun getSuggestedBooks(count: Int): List<Book>

    fun searchBooks(query: String): List<Book>

    fun getBookDetail(isbn: String): BookDetail?

    fun addToCart(isbn: String, amount: Int = 1): Boolean
}