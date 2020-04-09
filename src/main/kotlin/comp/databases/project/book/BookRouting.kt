package comp.databases.project.book

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.books() {
    get("/") {
        call.respondText("List of all books!")
    }
    get("/{isbn}") {
        val isbn = context.parameters["isbn"]

        call.respondText("Requested ISBN: $isbn")
    }
}