package comp.databases.project.shared

import kotlin.system.exitProcess

abstract class Control(protected val view: View) {
    protected open var promptUser: String = ""

    fun run() {
        onInitialize()
        while (true) {
            when (val result = view.prompt(user = promptUser)) {
                null -> System.err.println("Could not read input.")
                else -> baseCommand(result)
            }
        }
    }

    fun quit(status: Int = 0) {
        onQuit()
        exitProcess(status)
    }

    private fun baseCommand(args: List<String>) {
        if (args.size == 1 && args[0] == "exit") {
            quit()
        } else if (args.isNotEmpty() && args[0].isNotBlank()) {
            if (!onCommand(args)) {
                println("Unknown command: ${args[0]}")
            }
        }
    }

    protected abstract fun onCommand(args: List<String>): Boolean

    protected open fun onInitialize() {}
    protected open fun onQuit() {}
}