package comp.databases.project.shared.books.data
import java.math.BigDecimal
import java.sql.*;
import java.util.*;

object BookDatabase {
    private val connection: Connection;

    init {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:8888/postgres", "postgres", "postgres");

    }

    fun authenticate(email: String, password: String): Customer? {
        val cust_password: String;
        val card_num: String;
        val expiry_month: Int;
        val expiry_year: Int;
        val cvc_code: Int;
        val street_num: String;
        val street_name: String;
        val postal_code: String;

        val pstmt = connection.prepareStatement("SELECT * FROM customer WHERE cust_email = CAST(? AS VARCHAR(40))");
        var resSet: ResultSet;
        pstmt.setString(1, email);
        resSet = pstmt.executeQuery();
        if (resSet.next()) {
            cust_password = resSet.getString(2);
            card_num = resSet.getString(3);
            expiry_month = resSet.getInt(4);
            expiry_year = resSet.getInt(5);
            cvc_code = resSet.getInt(6);
            street_num = resSet.getString(7);
            street_name = resSet.getString(8);
            postal_code = resSet.getString(9);

            if (cust_password == password) {
                println("email ${email}, street name ${street_name}");
                return Customer(
                    email,
                    password,
                    card_num,
                    expiry_month,
                    expiry_year,
                    cvc_code,
                    street_num,
                    street_name,
                    postal_code
                );
            }
        }
        return null;
    }

    fun new_user(
        email: String,
        password: String,
        card_num: String,
        expiry_month: Int,
        expiry_year: Int,
        cvc_code: Int,
        street_num: String,
        street_name: String,
        postal_code: String,
        city: String,
        country: String
    ): Customer? {
        //check if user is in db
        val pstmt = connection.prepareStatement("SELECT * FROM customer WHERE cust_email = CAST(? AS VARCHAR(40))");
        var resSet: ResultSet;
        pstmt.setString(1, email);
        resSet = pstmt.executeQuery();
        if (resSet.next()) {
            return null;
        }

        val postal_stmt = connection.prepareStatement("INSERT INTO postal_zone VALUES (CAST(? AS VARCHAR(8)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(40))) ON CONFLICT DO NOTHING");
        postal_stmt.setString(1, postal_code);
        postal_stmt.setString(2, city);
        postal_stmt.setString(3, country);
        postal_stmt.executeUpdate();


        val cust_stmt = connection.prepareStatement("INSERT INTO customer VALUES (CAST(? AS VARCHAR(40), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(20)), CAST(? AS INT), CAST(? AS INT), CAST(? AS INT), CAST(? AS VARCHAR(20)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(8)))");

        cust_stmt.setString(1, email);
        cust_stmt.setString(2, password);
        cust_stmt.setString(3, card_num);
        cust_stmt.setInt(4, expiry_month);
        cust_stmt.setInt(5, expiry_year);
        cust_stmt.setInt(6, cvc_code);
        cust_stmt.setString(7, street_num);
        cust_stmt.setString(8, street_name);
        cust_stmt.setString(9, postal_code);

        cust_stmt.executeUpdate();

        return Customer(
            email,
            password,
            card_num,
            expiry_month,
            expiry_year,
            cvc_code,
            street_num,
            street_name,
            postal_code
        );
    }
}