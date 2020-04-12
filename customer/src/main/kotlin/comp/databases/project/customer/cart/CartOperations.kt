package comp.databases.project.customer.cart

import comp.databases.project.customer.books.data.StorefrontRepository
import comp.databases.project.customer.data.StoreViewState
import comp.databases.project.shared.Control
import comp.databases.project.shared.books.data.Address
import comp.databases.project.shared.cart.model.Order

private fun checkId(id: Int, list: List<*>): Boolean = id < list.size && id >= 0

fun Control.addOperation(repository: StorefrontRepository, viewState: StoreViewState, email: String, id: Int = -1) {

    if (viewState is StoreViewState.NoView ||
        id < 0 && viewState !is StoreViewState.DetailView
    ) {
        view.printerrln("No product to add.")
        return
    }

    if ((viewState is StoreViewState.SearchResultsView && !checkId(id, viewState.books)) ||
        viewState is StoreViewState.CartView && !checkId(id, viewState.items)
    ) {
        view.printerrln("Invalid item #ID.")
        return
    }

    view.print("Enter quantity: ")
    val quantity = view.readLine()?.toLongOrNull()
    if (quantity == null || quantity < 1) {
        view.printerrln("Invalid quantity.")
        return
    }

    val item = when (viewState) {
        StoreViewState.NoView -> throw IllegalStateException()
        is StoreViewState.DetailView -> viewState.book.detail.isbn
        is StoreViewState.SearchResultsView -> viewState.books[id].isbn
        is StoreViewState.CartView -> viewState.items[id].book.isbn
    }

    if (!repository.addToCart(item, quantity, email)) {
        when (quantity) {
            1L -> view.printerrln("Couldn't add item to cart.")
            else -> view.printerrln("Couldn't add items to cart.")
        }
    } else {
        when (quantity) {
            1L -> view.println("Added item to cart.")
            else -> view.println("Added items to cart.")
        }
    }
}

fun Control.removeOperation(repository: StorefrontRepository, viewState: StoreViewState, email: String, id: Int = -1) {
    if (viewState !is StoreViewState.CartView) {
        view.printerrln("Cannot remove item from card when not viewing cart. Run [cart] to view cart.")
        return
    }

    if (!checkId(id, viewState.items)) {
        view.printerrln("Invalid item #ID for cart.")
        return
    }

    if (repository.removeFromCart(viewState.items[id].book.isbn, email)) {
        when (viewState.items[id].quantity) {
            1L -> view.println("Removed item from cart.")
            else -> view.println("Removed items from cart.")
        }
    } else {
        when (viewState.items[id].quantity) {
            1L -> view.printerrln("Could not remove item from cart.")
            else -> view.printerrln("Could not remove items from cart.")
        }
    }
}

fun Control.placeOrder(repository: StorefrontRepository): Order? {
    var selection: String? = null
    while (selection !in arrayOf("y", "n")) {
        view.print("Use billing address as shipping address [y/n]: ")
        selection = view.readLine()?.toLowerCase()

        if (selection == null) {
            view.printerrln("Could not read input.")
            return null
        }
    }

    return if (selection == "y") {
        repository.submitOrder()
    } else {
        view.println("""
            |Shipping Address Details
            |------------------------
        """.trimMargin())

        view.print("Street Number: ")
        val streetNumber = view.readLine() ?: return null

        view.print("Street Name: ")
        val streetName = view.readLine() ?: return null

        view.print("Postal Code: ")
        val postalCode = view.readLine() ?: return null

        view.print("City: ")
        val city = view.readLine() ?: return null

        view.print("Country: ")
        val country = view.readLine() ?: return null

        repository.submitOrder(Address(streetNumber, streetName, postalCode, city, country))
    }
}