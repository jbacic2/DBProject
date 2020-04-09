package comp.databases.project.data

import com.squareup.sqldelight.sqlite.driver.JdbcDriver
import java.sql.Connection
import java.sql.DriverManager

class PostgresDriver : JdbcDriver() {
    override fun getConnection(): Connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:8888/postgres", "postgres", "postgres")
}