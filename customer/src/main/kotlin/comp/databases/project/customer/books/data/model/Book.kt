package comp.databases.project.customer.books.data.model

data class Book(
    val isbn: String,
    val title: String,
    val genre: String,
    val coverImage: String?,
    val synopsis: String?,
    val pages: Int,
    val price: Double,
    val stock: Int,
    val publisher: String,
    val percentOfSales: Double,
    val isLegacyItem: Boolean
)