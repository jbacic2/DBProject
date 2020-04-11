package comp.databases.project.customer.data

import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.cart.model.Cart

sealed class StoreViewState {
    object NoView : StoreViewState()
    data class DetailView(val book: BookDetail) : StoreViewState()
    data class SearchResultsView(val books: List<Book>) : StoreViewState()
    data class CartView(val items: List<Cart.Item>) : StoreViewState()
}