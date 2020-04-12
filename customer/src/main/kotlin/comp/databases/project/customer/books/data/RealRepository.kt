package comp.databases.project.customer.books.data

import comp.databases.project.shared.books.data.Address
import comp.databases.project.shared.books.data.BookDatabase
import comp.databases.project.shared.books.model.Author
import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail
import comp.databases.project.shared.cart.model.Cart
import comp.databases.project.shared.cart.model.Order

private val dummyBooks = listOf(
    BookDetail(
        Book(
            "0-7475-3269-9",
            "Harry Potter and the Philosopher's Stone",
            "Fantasy",
            "https://en.wikipedia.org/wiki/Harry_Potter_and_the_Philosopher%27s_Stone#/media/File:Harry_Potter_and_the_Philosopher%22",
            "Harry Potter and the Philosophers Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling's debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.",
            400,
            39.99,
            10,
            "Bloomsbury",
            0.12,
            false
        ),
        listOf(Author("J. K. Rowling"))
    ),
    BookDetail(
        Book(
            "0-7475-3849-2",
            "Harry Potter and the Chamber of Secrets",
            "Fantasy",
            "https://en.wikipedia.org/wiki/Harry_Potter_and_the_Chamber_of_Secrets#/media/File:Harry_Potter_and_the_Chamber_of_Secrets.jpg",
            "Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry's second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the schools corridors warn that the \"Chamber of Secrets\" has been opened and that the \"heir of Slytherin\" would kill all pupils who do not come from all-magical families.",
            251,
            22.00,
            35,
            "Bloomsbury",
            0.10,
            false
        ),
        listOf(
            Author("J. K. Rowling"),
            Author("J. K. Rowling"),
            Author("J. K. Rowling"),
            Author("J. K. Rowling"),
            Author("J. K. Rowling")
        )
    )
)

//private var cart: Cart? = null

object RealRepository : StorefrontRepository {
    override fun getSuggestedBooks(count: Int): List<Book> = dummyBooks.map { (book) -> book }

    override fun searchBooks(query: String): List<Book> {
        var books: List<Book>
        books = BookDatabase.searchBooks(query)
        return books
    }

    override fun getBookDetail(book: Book): BookDetail{
        return BookDatabase.getBookDetail(book);
    }

    override fun addToCart(isbn: String, quantity: Long, email: String): Boolean {
        return BookDatabase.addToCart(isbn,quantity,email)

        /*if (cart != null) {
            cart = cart?.copy(
                items = cart!!.items + Cart.Item(
                    dummyBooks.map { (book) -> book }.find { it.isbn == isbn }!!,
                    quantity
                )
            )
        } else {
            cart = Cart(
                5L, listOf(
                    Cart.Item(
                        dummyBooks.map { (book) -> book }.find { it.isbn == isbn }!!,
                        quantity
                    )
                )
            )
        }*/
        return true
    }

    override fun removeFromCart(isbn: String, email: String): Boolean {
        return BookDatabase.removeFromCart(isbn, email)
    }

    override fun updateCartItem(isbn: String, quantity: Int) {
        TODO("Not yet implemented")
    }

    override fun getCart(email: String): Cart?{
        return BookDatabase.getCart(email)
    }

    override fun submitOrder(address: Address?): Order? {
        TODO("Not yet implemented")
    }

    override fun getOrders(): List<Order> {
        TODO("Not yet implemented")
    }
}
