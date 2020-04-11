package comp.databases.project.shared.cart.model

import comp.databases.project.shared.books.data.Address
import comp.databases.project.shared.books.model.Book

data class Order(
    val id: Long,
    val status: Status,
    val purchaseDay: Int,
    val purchaseMonth: Int,
    val purchaseYear: Int,
    val billingAddress: Address,
    val shippingAddress: Address,
    val customerEmail: String,
    val items: List<Item>
) {
    data class Item(
        val book: Book,
        val quantity: Int
    )

    enum class Status {
        AwaitingFulfillment,
        AwaitingShipment,
        EnRoute,
        Delivered;

        fun asString() = when (this) {
            AwaitingFulfillment -> "Awaiting Fulfillment"
            AwaitingShipment -> "Awaiting Shipment"
            EnRoute -> "En Route"
            Delivered -> "Delivered"
        }
    }
}