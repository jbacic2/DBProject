package comp.databases.project.customer

import comp.databases.project.shared.Control
import comp.databases.project.shared.View

class CustomerControl : Control(View()) {
    override fun executeCommand(args: List<String>): Boolean {
        return when (args[0]) {
            "sound" -> {
                println("Woof")
                true
            }
            else -> false
        }
    }

    override fun onInitialize() {
        println("Welcome!")
    }

    override fun onQuit() {
        println("Goodbye!")
    }
}

fun main() {
    val control = CustomerControl()
    control.run()
}