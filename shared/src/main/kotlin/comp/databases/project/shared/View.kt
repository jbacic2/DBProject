package comp.databases.project.shared

import java.io.IOException

open class View(var prompt: String = "> ") {
    private val defaultPrompt = prompt

    fun resetPrompt() {
        prompt = defaultPrompt
    }

    fun prompt(user: String = ""): List<String>? {
        print("$user$prompt")
        return readLine()?.split(" ")
    }

    fun print(value: Any?) {
        kotlin.io.print(value?.toString())
    }

    fun println(value: Any?) {
        kotlin.io.println(value?.toString())
    }

    fun printerr(value: Any?) {
        print(value?.toString())
    }

    fun printerrln(value: Any?) {
        // TODO: Print in red
        println(value?.toString())
    }

    fun readPassword(): String? {
        return try {
            System.console().readPassword().joinToString()
        } catch (e: IOException) {
            null
        }
    }

    fun readLine(): String? {
        return kotlin.io.readLine()
    }
}