package comp.databases.project.shared

class View(var prompt: String = ">") {
    private val defaultPrompt = prompt

    fun resetPrompt() {
        prompt = defaultPrompt
    }

    fun prompt(user: String = ""): List<String>? {
        print("$user$prompt ")
        return readLine()?.split(" ")
    }

}