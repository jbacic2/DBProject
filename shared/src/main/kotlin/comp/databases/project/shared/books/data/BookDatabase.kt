package comp.databases.project.shared.books.data
import java.sql.*;
import java.util.*;

object BookDatabase {
    private val connection: Connection;

    init {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:8888/postgres", "postgres", "postgres");
        //Statement statement = connection.createStatement();

    }

    fun authenticate(email: String, password: String): Customer? {
        val cust_password: String;
        val card_num: Int;
        val expiry_month: Int;
        val expiry_year: Int;
        val cvc_code: Int;
        val street_num: Float;
        val street_name: String;
        val postal_code: String;
        val pstmt = connection.prepareStatement("SELECT * FROM customer WHERE cust_email = CAST(? AS VARCHAR(40))");
        var resSet: ResultSet;
        pstmt.setString(1, email);
        resSet = pstmt.executeQuery();
        if (resSet.next()) {
            cust_password = resSet.getString(2);
            card_num = resSet.getInt(3);
            expiry_month = resSet.getInt(4);
            expiry_year = resSet.getInt(5);
            cvc_code = resSet.getInt(6);
            street_num = resSet.getFloat(7);
            street_name = resSet.getString(8);
            postal_code = resSet.getString(9);

            if (cust_password == password) {
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
    // Query code here
}