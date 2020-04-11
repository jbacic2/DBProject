package comp.databases.project.customer

import comp.databases.project.customer.auth.control.LoginControl
import comp.databases.project.customer.auth.data.AuthManager
import comp.databases.project.customer.auth.data.DummyAuthManager
import comp.databases.project.customer.books.data.DummyRepository
import comp.databases.project.customer.books.data.StorefrontRepository
import comp.databases.project.shared.Control
import comp.databases.project.shared.View

class CustomerControl(
    private val authManager: AuthManager,
    private val storefrontRepository: StorefrontRepository
) : Control(View()) {
    override fun onCommand(args: List<String>): Boolean {
        return when (args[0]) {
            "sound" -> {
                view.println("Woof")
                true
            }
            "login" -> {
                LoginControl(authManager, view).run()
                if (authManager.isAuthenticated) {
                    promptUser = authManager.customer!!.email
                }
                true
            }
            "logout" -> {
                authManager.logout()
                promptUser = ""
                view.println("Logged out.")
                true
            }
            else -> false
        }
    }

    override fun onInitialize() {
        view.println("Welcome to...\n")
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
        view.println("\nThe most popular book store this side of the Coronavirus Pandemic.")
        view.println(
            """
            Popular commands:
            * login  | Sign in to your account
            * search | Search for your favourite book
            * help   | View all commands
        """.trimIndent()
        )
        view.print("\n\n")
        view.println("Here are some recommended books:")
        storefrontRepository.getSuggestedBooks(5).forEachIndexed { index, (_, title) ->
            view.println("${index + 1}. $title")
        }
        view.print("\n\n")
    }

    override fun onQuit() {
        view.println("Goodbye!")
    }
}

fun main() {
    CustomerControl(DummyAuthManager, DummyRepository).run()
}