package comp.databases.project.data

import comp.databases.project.db.BookDatabase

object DatabaseProvider {
    val database = BookDatabase(PostgresDriver())
}