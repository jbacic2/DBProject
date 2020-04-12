package comp.databases.project.shared

import com.github.ricksbrown.cowsay.Cowsay
import kotlin.system.exitProcess

abstract class Control(val view: View) {
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
        if (args.isNotEmpty() && args[0] == "exit") {
            quit(process = true)
        } else if (args.isNotEmpty() && args[0] == "cowsay") {
            val text = args.subList(1, args.size)
            val say =
                if (text.isEmpty()) "Moo, this counts as a bonus-- I mean Moo" else text.joinToString(separator = " ")
            view.println(Cowsay.say(arrayOf(say)))
        } else if (args.isNotEmpty() && args[0] == "help") {
            onHelp()
        } else if (args.isNotEmpty() && args[0].isNotBlank()) {
            if (!onCommand(args)) {
                view.println("Unknown command: ${args[0]}")
            }
        }
    }

    protected abstract fun onCommand(args: List<String>): Boolean

    protected open fun onInitialize() {}
    protected open fun onQuit() {}
    protected open fun onHelp() {}
}