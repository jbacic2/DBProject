package comp.databases.project.customer.books.data

import comp.databases.project.shared.books.data.Address
import comp.databases.project.shared.books.data.Customer
import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.cart.model.Cart
import comp.databases.project.shared.cart.model.Order

interface StorefrontRepository {
    fun getSuggestedBooks(count: Int): List<Book>

    fun searchBooks(query: String): List<Book>

    fun getBookDetail(book: Book): BookDetail

    fun addToCart(isbn: String, quantity: Long = 1, email: String): Boolean

    fun removeFromCart(isbn: String, email: String): Boolean

    fun updateCartItem(isbn: String, quantity: Int)

    fun getCart(email: String): Cart?

    fun submitOrder(cust: Customer, address: Address? = null): Boolean

    fun getOrders(): List<Order>
}