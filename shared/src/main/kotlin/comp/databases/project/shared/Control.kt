package comp.databases.project.shared

import kotlin.system.exitProcess

abstract class Control(protected val view: View) {
    protected open var promptUser: String = ""
    private var _run = true


    fun run() {
        onInitialize()
        while (_run) {
            when (val result = view.prompt(user = promptUser)) {
                null -> System.err.println("Could not read input.")
                else -> baseCommand(result)
            }
        }
    }

    fun quit(status: Int = 0, process: Boolean = false) {
        onQuit()
        if (process) {
            exitProcess(status)
        } else {
            _run = false
        }
    }

    private fun baseCommand(args: List<String>) {
        if (args.size == 1 && args[0] == "exit") {
            quit(process = true)
        } else if (args.isNotEmpty() && args[0].isNotBlank()) {
            if (!onCommand(args)) {
                view.println("Unknown command: ${args[0]}")
            }
        }
    }

    protected abstract fun onCommand(args: List<String>): Boolean

    protected open fun onInitialize() {}
    protected open fun onQuit() {}
}