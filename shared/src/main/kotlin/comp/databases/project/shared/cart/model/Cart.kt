package comp.databases.project.shared.cart.model

import comp.databases.project.shared.books.model.Book

data class Cart(val id: Long, val items: List<Item>) {
    data class Item(
        val book: Book,
        val quantity: Long
    )
}