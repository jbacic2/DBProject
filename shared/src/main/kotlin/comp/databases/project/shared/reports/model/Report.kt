package comp.databases.project.shared.reports.model

data class Report(
    val month: Int,
    val year: Int,
    val summary: List<LineItem>,
    val categories: List<Category>
) {
    data class LineItem(
        val name: String,
        val amount: Double
    )

    data class Category(
        val title: String,
        val items: List<LineItem>
    )
}