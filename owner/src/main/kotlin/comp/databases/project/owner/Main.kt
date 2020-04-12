package comp.databases.project.owner

import com.jakewharton.picnic.table
import comp.databases.project.owner.books.addBookOperation
import comp.databases.project.owner.books.data.DummyRepository
import comp.databases.project.owner.books.data.ManagementRepository
import comp.databases.project.owner.books.data.RealManagementRepository
import comp.databases.project.owner.books.removeBookOperation
import comp.databases.project.owner.reports.view.printReport
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
            "report" -> {
                view.printReport(managementRepository.getReport(5, 2020))
                return true
            }
        }
        return false
    }

    override fun onInitialize() {
        view.println(
            """
${'$'}${'$'}\                          ${'$'}${'$'}\             ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\                                     ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\                      ${'$'}${'$'}\       
${'$'}${'$'} |                         ${'$'}${'$'} |            \_${'$'}${'$'}  _|                                    ${'$'}${'$'}  __${'$'}${'$'}\                     ${'$'}${'$'} |      
${'$'}${'$'} |      ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\   ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\  ${'$'}${'$'} |  ${'$'}${'$'}\         ${'$'}${'$'} |  ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\  ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\   ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\        ${'$'}${'$'} |  ${'$'}${'$'} | ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\   ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\  ${'$'}${'$'} |  ${'$'}${'$'}\ 
${'$'}${'$'} |     ${'$'}${'$'}  __${'$'}${'$'}\ ${'$'}${'$'}  __${'$'}${'$'}\ ${'$'}${'$'} | ${'$'}${'$'}  |        ${'$'}${'$'} |  ${'$'}${'$'}  __${'$'}${'$'}\ ${'$'}${'$'}  __${'$'}${'$'}\  \____${'$'}${'$'}\       ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\ |${'$'}${'$'}  __${'$'}${'$'}\ ${'$'}${'$'}  __${'$'}${'$'}\ ${'$'}${'$'} | ${'$'}${'$'}  |
${'$'}${'$'} |     ${'$'}${'$'} /  ${'$'}${'$'} |${'$'}${'$'} /  ${'$'}${'$'} |${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}  /         ${'$'}${'$'} |  ${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'} |  ${'$'}${'$'} | ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'} |      ${'$'}${'$'}  __${'$'}${'$'}\ ${'$'}${'$'} /  ${'$'}${'$'} |${'$'}${'$'} /  ${'$'}${'$'} |${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}  / 
${'$'}${'$'} |     ${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'}  _${'$'}${'$'}<          ${'$'}${'$'} |  ${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'}  __${'$'}${'$'} |      ${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'}  _${'$'}${'$'}<  
${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\\${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}  |\${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}  |${'$'}${'$'} | \${'$'}${'$'}\       ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\ ${'$'}${'$'} |  ${'$'}${'$'} |${'$'}${'$'} |  ${'$'}${'$'} |\${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'} |      ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}  |\${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}  |\${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}  |${'$'}${'$'} | \${'$'}${'$'}\ 
\________|\______/  \______/ \__|  \__|      \______|\__|  \__|\__|  \__| \_______|      \_______/  \______/  \______/ \__|  \__
        """.trimIndent()
        )

        view.println("Owner Management System")
        view.println("Type [help] for all commands.\n\n")
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

fun main() {
    OwnerControl(RealManagementRepository).run()
}