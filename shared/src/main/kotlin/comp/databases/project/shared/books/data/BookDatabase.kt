package comp.databases.project.shared.books.data

import comp.databases.project.shared.books.model.Author
import comp.databases.project.shared.books.model.Book
import comp.databases.project.shared.books.model.BookDetail
<<<<<<< HEAD
import comp.databases.project.shared.cart.model.Cart
=======
import comp.databases.project.shared.cart.model.Order
import comp.databases.project.shared.reports.model.Report
>>>>>>> ce5faf2fb6bb4dcad454b0468245cbcc9a181996

import java.lang.Exception
import java.sql.*;

object BookDatabase {
    private val connection: Connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:8888/postgres", "postgres", "postgres");

    fun authenticate(email: String, password: String): Customer? {
        val statement = connection.prepareStatement("SELECT * FROM customer NATURAL JOIN postal_zone WHERE cust_email = ?");
        statement.setString(1, email)
        val resSet: ResultSet = statement.executeQuery()

        if (resSet.next() && resSet.getString(resSet.findColumn("password")) == password) {
            val address = Address(
                resSet.getString(resSet.findColumn("street_num")),
                resSet.getString(resSet.findColumn("street_name")),
                resSet.getString(resSet.findColumn("postal_code")),
                resSet.getString(resSet.findColumn("city")),
                resSet.getString(resSet.findColumn("country"))
            )

            val cust = Customer(
                email,
                password,
                resSet.getString(resSet.findColumn("card_num")),
                resSet.getInt(resSet.findColumn("expiry_month")),
                resSet.getInt(resSet.findColumn("expiry_year")),
                resSet.getInt(resSet.findColumn("cvc_code")),
                address
            )


            // check if the user has a cart
            val cartCheckStmt = connection.prepareStatement("SELECT order_num FROM cust_order WHERE status = 'Cart' AND cust_email = ?");
            cartCheckStmt.setString(1, email)
            val cartCheckResSet:ResultSet = cartCheckStmt.executeQuery()
            if (cartCheckResSet.next()){
                //they already have a cart
            }
            else{
                val makeCartStmt = connection.prepareStatement("INSERT INTO cust_order (status, cust_email) VALUES ('Cart', ?)")
                makeCartStmt.setString(1, email)
                try {
                    makeCartStmt.executeUpdate();
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("Error when making a cart")
                }
            }
            return cust
        }
        return null
    }

    fun new_user(
        email: String,
        password: String,
        card_num: String,
        expiry_month: Int,
        expiry_year: Int,
        cvc_code: Int,
        address: Address
    ): Customer? {
        val (street_num, street_name, postal_code, city, country) = address
        //check if user is in db
        val pstmt = connection.prepareStatement("SELECT * FROM customer WHERE cust_email = CAST(? AS VARCHAR(40))");
        var resSet: ResultSet;
        pstmt.setString(1, email);
        resSet = pstmt.executeQuery();
        if (resSet.next()) {
            return null;
        }

        val postal_stmt =
            connection.prepareStatement("INSERT INTO postal_zone VALUES (CAST(? AS VARCHAR(8)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(40))) ON CONFLICT DO NOTHING");
        postal_stmt.setString(1, postal_code);
        postal_stmt.setString(2, city);
        postal_stmt.setString(3, country);

        try {
            postal_stmt.executeUpdate();
        } catch (e: Exception) {
            println("Error with postal code")
            return null
        }

        val cust_stmt =
            connection.prepareStatement("INSERT INTO customer VALUES (CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(20)), CAST(? AS INT), CAST(? AS INT), CAST(? AS INT), CAST(? AS VARCHAR(20)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(8)))");

        cust_stmt.setString(1, email);
        cust_stmt.setString(2, password);
        cust_stmt.setString(3, card_num);
        cust_stmt.setInt(4, expiry_month);
        cust_stmt.setInt(5, expiry_year);
        cust_stmt.setInt(6, cvc_code);
        cust_stmt.setString(7, street_num);
        cust_stmt.setString(8, street_name);
        cust_stmt.setString(9, postal_code);

        try {
            cust_stmt.executeUpdate();
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error with new user info")
            return null
        }

        val makeCartStmt = connection.prepareStatement("INSERT INTO cust_order (status, cust_email) VALUES ('Cart', ?)")
        makeCartStmt.setString(1, email)
        try {
            makeCartStmt.executeUpdate();
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error when making a cart")
            return null
        }

        return Customer(
            email,
            password,
            card_num,
            expiry_month,
            expiry_year,
            cvc_code,
            address
        )
    }

    fun searchBooks(query: String): List<Book> {
        var isbn: String
        var title: String
        var genre: String
        var coverImage: String?
        var synopsis: String?
        var pages: Int
        var price: Double
        var stock: Int
        var publisher: String
        var percentOfSales: Double
        var isLegacyItem: Boolean
        var bookList: MutableList<Book> = mutableListOf()
        val resSet: ResultSet;
        val pstmt: PreparedStatement;

        if (query.length <= 17) {
            pstmt =
                connection.prepareStatement("SELECT * FROM book WHERE isbn = CAST (? AS VARCHAR(17)) AND NOT legacy_item UNION SELECT * FROM book WHERE title = ? AND NOT legacy_item UNION SELECT * FROM book WHERE genre = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT * FROM book WHERE pub_name = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item FROM book NATURAL JOIN author WHERE author_name = ? AND NOT legacy_item")
            pstmt.setString(1, query);
            pstmt.setString(2, query);
            pstmt.setString(3, query);
            pstmt.setString(4, query);
            pstmt.setString(5, query);
        } else if (query.length <= 40) {
            pstmt =
                connection.prepareStatement("SELECT * FROM book WHERE title = ? AND NOT legacy_item UNION SELECT * FROM book WHERE genre = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT * FROM book WHERE pub_name = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item FROM book NATURAL JOIN author WHERE author_name = ? AND NOT legacy_item")
            pstmt.setString(1, query);
            pstmt.setString(2, query);
            pstmt.setString(3, query);
            pstmt.setString(4, query);
        } else {
            pstmt =
                connection.prepareStatement("SELECT * FROM book WHERE title = ? AND NOT legacy_item UNION SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item FROM book NATURAL JOIN author WHERE author_name = ? AND NOT legacy_item")
            pstmt.setString(1, query);
            pstmt.setString(2, query);
        }

        resSet = pstmt.executeQuery();
        while (resSet.next()) {
            isbn = resSet.getString(1)
            title = resSet.getString(2)
            genre = resSet.getString(3)
            coverImage = resSet.getString(4)
            synopsis = resSet.getString(5)
            pages = resSet.getInt(6)
            price = resSet.getDouble(7)
            stock = resSet.getInt(8)
            publisher = resSet.getString(9)
            percentOfSales = resSet.getDouble(10)
            isLegacyItem = resSet.getBoolean(11)
            bookList.add(
                Book(
                    isbn,
                    title,
                    genre,
                    coverImage,
                    synopsis,
                    pages,
                    price,
                    stock,
                    publisher,
                    percentOfSales,
                    isLegacyItem
                )
            )
        }

        val finalBooks: List<Book> = bookList
        return finalBooks;
    }

    fun getBookDetail(book: Book): BookDetail {
        val resSet: ResultSet;
        var auth_name: String;
        var authorList: MutableList<Author> = mutableListOf()
        val pstmt = connection.prepareStatement("SELECT author_name FROM author WHERE isbn = ?")
        pstmt.setString(1, book.isbn);
        resSet = pstmt.executeQuery();
        while (resSet.next()) {
            auth_name = resSet.getString(1)
            authorList.add(Author(auth_name))
        }
        return BookDetail(book, authorList)
    }

    fun addBook(book: BookDetail): Boolean {
        val (detail, authors) = book
        try {
            connection.autoCommit = false

            connection.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").apply {
                setString(1, detail.isbn)
                setString(2, detail.title)
                setString(3, detail.genre)
                setString(4, detail.coverImage)
                setString(5, detail.synopsis)
                setInt(6, detail.pages)
                setDouble(7, detail.price)
                setInt(8, detail.stock)
                setString(9, detail.publisher)
                setDouble(10, detail.percentOfSales)
                setBoolean(11, detail.isLegacyItem)
            }.executeUpdate()

            connection.prepareStatement("INSERT INTO author VALUES (?, ?)").apply {
                setString(2, detail.isbn)
                authors.forEach { (name) ->
                    setString(1, name)
                    executeUpdate()
                }
            }

            connection.commit()
        } catch (e: Exception) {
            connection.rollback()
            return false
        }
        return true
    }

    fun getCart(email: String): Cart?{
        var isbn: String
        var title: String
        var genre: String
        var coverImage: String?
        var synopsis: String?
        var pages: Int
        var price: Double
        var stock: Int
        var publisher: String
        var percentOfSales: Double
        var isLegacyItem: Boolean
        var id: Long = -1
        var quantity: Long
        var itemList: MutableList<Cart.Item> =mutableListOf()
        val resSet: ResultSet;
        val pstmt = connection.prepareStatement("SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item, order_num, quantity FROM book NATURAL JOIN book_ordered NATURAL JOIN cust_order WHERE status = 'Cart' AND cust_email = ?")
        pstmt.setString(1, email);

        resSet = pstmt.executeQuery();
        while (resSet.next()) {
            if (id.compareTo(-1) != 0 && id.compareTo(resSet.getInt(12)) != 0)
                break;
            isbn = resSet.getString(1)
            title = resSet.getString(2)
            genre = resSet.getString(3)
            coverImage = resSet.getString(4)
            synopsis = resSet.getString(5)
            pages = resSet.getInt(6)
            price = resSet.getDouble(7)
            stock = resSet.getInt(8)
            publisher = resSet.getString(9)
            percentOfSales = resSet.getDouble(10)
            isLegacyItem = resSet.getBoolean(11)
            id = resSet.getLong(12)
            quantity = resSet.getLong(13)
            itemList.add(Cart.Item(Book(isbn,title,genre,coverImage, synopsis, pages, price, stock, publisher, percentOfSales, isLegacyItem), quantity))
        }
        if (id.compareTo(-1) == 0){ //didn't find a cart
            return null
        }
        return Cart(id, itemList)

    }

    fun removeBook(isbn: String): Boolean {
        try {
            connection.prepareStatement("UPDATE book SET legacy_item = true WHERE isbn = ?").apply {
                setString(1, isbn)
            }.execute()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun getMonthlyReport(month: Int, year: Int): Report? {
        try {
            val result =
                connection.prepareStatement("SELECT * FROM monthly_sales_vs_expense WHERE month = ? AND year = ?")
                    .apply {
                        setInt(1, month)
                        setInt(2, year)
                    }.executeQuery()

            if (result.next()) {
                val sales: Double? = result.getDouble("sales")
                val expense: Double? = result.getDouble("expense")


                return Report(month, year, listOfNotNull(
                    sales?.let { Report.LineItem("Sales", it) },
                    expense?.let { Report.LineItem("Expenses", it) }
                ), emptyList())
            }
        } catch (e: Exception) {
            return null
        }

        return null
    }
}
