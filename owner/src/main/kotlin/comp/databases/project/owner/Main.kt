package comp.databases.project.owner

import com.jakewharton.picnic.table
import comp.databases.project.owner.books.addBookOperation
import comp.databases.project.owner.books.data.DummyRepository
import comp.databases.project.owner.books.data.ManagementRepository
import comp.databases.project.owner.books.removeBookOperation
import comp.databases.project.shared.Control
import comp.databases.project.shared.View

class OwnerControl(private val managementRepository: ManagementRepository) : Control(View()) {
    override fun onCommand(args: List<String>): Boolean {
        when (args[0]) {
            "add" -> {
                addBookOperation(managementRepository)
                return true
            }
            "remove" -> {
                removeBookOperation(managementRepository, args.getOrNull(1) ?: "")
                return true
            }
        }
        return false
    }

    override fun onInitialize() {
    }

    override fun onQuit() {
    }

    override fun onHelp() {
        val helpTable = table {
            cellStyle {
                paddingLeft = 1
                paddingRight = 1
            }
            header {
                cellStyle { border = true }
                row("command", "description")
            }

            row {
                cellStyle {
                    borderBottom = true
                    paddingTop = 1
                }
                cell("Books") {
                    columnSpan = 2
                }
            }
            row("add", "Add a new book to the store")
            row("remove <isbn>", "Remove a book from the store using its <isbn>")

            row {
                cellStyle {
                    borderBottom = true
                    paddingTop = 1
                }
                cell("Reports") {
                    columnSpan = 2
                }
            }
            row("report", "View the latest report")
            row("report <month>", "View the report for a specific month")
        }

        view.println("$helpTable")
    }
}

/**
 * Required Features:
 * [ ] Add new books
 * [ ] Remove books
 * [ ] Report sales
 * [ ] Report expenses
 * [ ] Overall performance report
 *      [ ] Monthly?
 *      [ ] Yearly?
 */
fun main() {
    OwnerControl(DummyRepository).run()
}