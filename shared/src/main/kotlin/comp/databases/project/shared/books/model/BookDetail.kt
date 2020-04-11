package comp.databases.project.shared.books.model

data class BookDetail(
    val detail: Book,
    val authors: List<Author>
)