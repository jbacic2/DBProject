package comp.databases.project.shared.cart.model

import comp.databases.project.shared.books.data.Address

data class Order(
    val id: Long,
    val status: Status,
    val purchaseDay: Int,
    val purchaseMonth: Int,
    val purchaseYear: Int,
    val billingAddress: Address,
    val shippingAddress: Address,
    val customerEmail: String
) {
    enum class Status {
        AwaitingFulfillment,
        AwaitingShipment,
        EnRoute,
        Delivered
    }
}