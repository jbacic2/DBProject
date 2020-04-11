package comp.databases.project.shared.books.data
import comp.databases.project.shared.books.model.Book

import java.lang.Exception
import java.sql.*;

object BookDatabase {
    private val connection: Connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:8888/postgres", "postgres", "postgres");

    fun authenticate(email: String, password: String): Customer? {
        val statement =
            connection.prepareStatement("SELECT * FROM customer NATURAL JOIN postal_zone WHERE cust_email = ?");
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
            return Customer(
                email,
                password,
                resSet.getString(resSet.findColumn("card_num")),
                resSet.getInt(resSet.findColumn("expiry_month")),
                resSet.getInt(resSet.findColumn("expiry_year")),
                resSet.getInt(resSet.findColumn("cvc_code")),
                address
            )
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
            println("Error with new user info")
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

//    fun searchBooks1(query : String):List<Book>?{
//        val isbn: String
//        val title: String
//        val genre: String
//        val coverImage: String?
//        val synopsis: String?
//        val pages: Int
//        val price: Double
//        val stock: Int
//        val publisher: String
//        val percentOfSales: Double
//        val isLegacyItem: Boolean
//
//        val pstmt = connection.prepareStatement("SELECT * FROM book WHERE isbn = CAST (? AS VARCHAR(17)) AND legacy_item = FALSE UNION SELECT isbn, title,  price FROM book WHERE title = ? AND legacy_item = FALSE UNION  SELECT isbn, title,  price FROM book WHERE genre = CAST (? AS VARCHAR(40)) AND legacy_item = FALSE UNION SELECT isbn, title,  price FROM book WHERE pub_name = CAST (? AS VARCHAR(40)) AND legacy_item = FALSE UNION SELECT isbn, title, price FROM book NATURAL JOIN author WHERE author_name = ?  AND legacy_item = FALSE");
//        var resSet: ResultSet;
//        pstmt.setString(1, query);
//        pstmt.setString(2, query);
//        pstmt.setString(3, query);
//        pstmt.setString(4, query);
//        pstmt.setString(5, query);
//
//        resSet = pstmt.executeQuery();
//        if (resSet.next()) {
//            isbn = resSet.getString(1)
//            title = resSet.getString(2)
//            genre = resSet.getString(3)
//            coverImage = resSet.getString(4)
//            synopsis = resSet.getString(5)
//        }
//
//
//    }
//    fun searchBooks2(query : String){
//
//    }
//    fun searchBooks2(query : String){
//
//    }
}
