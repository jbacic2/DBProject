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

    fun print(text: String) {
        kotlin.io.print(text)
    }

    fun println(text: String) {
        kotlin.io.println(text)
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