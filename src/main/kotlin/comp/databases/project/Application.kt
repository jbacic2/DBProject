package comp.databases.project

import comp.databases.project.data.DatabaseProvider
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.main() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/book") {
            val books = DatabaseProvider.database.bookQueries.select().executeAsList()
            call.respondText("$books")
        }
    }
}