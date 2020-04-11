package comp.databases.project.customer.books.data

import comp.databases.project.shared.books.data.Address
import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.cart.model.Cart
import comp.databases.project.shared.cart.model.Order

interface StorefrontRepository {
    fun getSuggestedBooks(count: Int): List<Book>

    fun searchBooks(query: String): List<Book>

    fun getBookDetail(isbn: String): BookDetail?

    fun addToCart(isbn: String, quantity: Long = 1): Boolean

    fun removeFromCart(isbn: String): Boolean

    fun updateCartItem(isbn: String, quantity: Int)

    fun getCart(): Cart?

    fun submitOrder(address: Address? = null): Order?
}