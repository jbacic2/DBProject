package comp.databases.project.owner.reports.view

import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.table
import comp.databases.project.shared.View
import comp.databases.project.shared.reports.model.Report
import java.text.NumberFormat
import java.util.*

fun View.printReport(report: Report) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val table = table {
        cellStyle {
            border = true
            paddingLeft = 1
            paddingRight = 1
        }

        row {
            cell("Report")
            cell("${report.month}/${report.year}") {
                alignment = TextAlignment.MiddleRight
            }
        }

        // Report Summary
        val summaryTotal = report.summary.fold(0.0, { acc, (_, amount) -> acc + amount })
        report.summary.forEach { (title, amount) ->
            row {
                cellStyle {
                    borderBottom = false
                    borderTop = false
                }
                cell(title)
                cell(formatter.format(amount)) {
                    alignment = TextAlignment.MiddleRight
                }
            }
        }
        row {
            cellStyle { borderTop = true }
            cell("Total $:")
            cell(formatter.format(summaryTotal)) {
                alignment = TextAlignment.MiddleRight
            }
        }

        // Each report category
        report.categories.forEach { (title, items) ->
            // Empty row
            row {
                cellStyle {
                    border = false
                    borderTop = true
                }
                cell("")
                cell("")
            }

            row { cell("$title (Revenue)") { columnSpan = 2 } }

            items.forEach { (title, amount) ->
                row {
                    cellStyle {
                        borderBottom = false
                        borderTop = false
                    }
                    cell(title)
                    cell(formatter.format(amount)) {
                        alignment = TextAlignment.MiddleRight
                    }
                }
            }
        }
        row {
            cellStyle {
                border = false
                borderTop = true
            }
            cell("")
            cell("")
        }
    }

    println(table)
}