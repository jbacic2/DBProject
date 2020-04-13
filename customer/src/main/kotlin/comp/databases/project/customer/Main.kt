package comp.databases.project.customer

import com.jakewharton.picnic.table
import comp.databases.project.customer.auth.control.addUserOperation
import comp.databases.project.customer.auth.control.loginOperation
import comp.databases.project.customer.auth.data.AuthManager
import comp.databases.project.customer.auth.data.RealAuthManager
import comp.databases.project.customer.auth.view.printCustomer
import comp.databases.project.customer.books.data.RealRepository
import comp.databases.project.customer.books.data.StorefrontRepository
import comp.databases.project.customer.books.view.printBookDetail
import comp.databases.project.customer.books.view.printSearchResults
import comp.databases.project.customer.cart.addOperation
import comp.databases.project.customer.cart.placeOrder
import comp.databases.project.customer.cart.removeOperation
import comp.databases.project.customer.cart.view.printCart
import comp.databases.project.customer.cart.view.printOrders
import comp.databases.project.customer.data.StoreViewState
import comp.databases.project.shared.Control
import comp.databases.project.shared.View

class CustomerControl(
    private val authManager: AuthManager,
    private val storefrontRepository: StorefrontRepository
) : Control(View()) {
    private var viewState: StoreViewState = StoreViewState.NoView

    override fun onCommand(args: List<String>): Boolean {
        return when (args[0]) {
            "sound" -> {
                view.println("Woof")
                true
            }
            "search" -> {
                val books = storefrontRepository.searchBooks(args.subList(1, args.size).joinToString(separator = " "))
                view.printSearchResults(books)
                viewState = StoreViewState.SearchResultsView(books)
                true
            }
            "view" -> {
                if(viewState is StoreViewState.SearchResultsView){
                    try {
                        if (args[1].toInt() < (viewState as StoreViewState.SearchResultsView).books.size) {
                            val book = (viewState as StoreViewState.SearchResultsView).books[args[1].toInt()]
                            val detail = storefrontRepository.getBookDetail(book)!!
                            view.printBookDetail(detail)
                            viewState = StoreViewState.DetailView(detail)
                        }
                    } catch (e:Exception){
                        view.println("Enter the number of a book ID on the screen")
                    }
                }
                else if(viewState is StoreViewState.CartView){
                    try {
                        if (args[1].toInt() < (viewState as StoreViewState.CartView).items.size) {
                            val book = (viewState as StoreViewState.CartView).items[args[1].toInt()].book
                            val detail = storefrontRepository.getBookDetail(book)!!
                            view.printBookDetail(detail)
                            viewState = StoreViewState.DetailView(detail)
                        }
                    } catch (e:Exception){
                        view.println("Enter the number of a book ID on the screen")
                    }
                }
                else {
                    view.println("You must first search for a book and then enter the number of a book ID to view it.")
                }
                true
            }
            "add" -> {
                if (authManager.customer == null){
                    view.println("You must first login to access a cart")
                }
                else{
                    addOperation(storefrontRepository, viewState, authManager.customer!!.email,args.getOrNull(1)?.toIntOrNull() ?: -1)
                }
                true
            }
            "remove" -> {
                if (authManager.customer == null){
                    view.println("You must first login to access a cart")
                }
                else {
                    removeOperation(storefrontRepository, viewState, authManager.customer!!.email,args.getOrNull(1)?.toIntOrNull() ?: -1)
                }
                true
            }
            "cart" -> {
                if (authManager.customer == null){
                    view.println("You must first login to put a book in the cart")
                }
                else{
                    val cart = storefrontRepository.getCart(authManager.customer!!.email)
                    view.printCart(cart)
                    cart?.let {
                        viewState = StoreViewState.CartView(it.items)
                    }
                }
                true
            }
            "order" -> {
                if (authManager.customer == null){
                    view.println("You must first login to make an order")
                }
                else {
                    if (placeOrder(storefrontRepository, authManager.customer!!)) {
                        view.println("Order placed.")
                    } else {
                        view.printerrln("Failed to place order.")
                    }
                }
                true
            }
            "orders" -> {
                if (authManager.customer == null){
                    view.println("You must first login look at your order history")
                }
                else{
                    view.printOrders(storefrontRepository.getOrders(authManager.customer!!))
                }
                true
            }
            "login" -> {
                loginOperation(authManager)
                if (authManager.isAuthenticated) {
                    promptUser = authManager.customer!!.email

                    view.println("Welcome back to Look Inna Book!")
                    view.println("""
                        |More helpful commands:
                        |* logout   | Log out of your account
                        |* cart     | View your shopping cart
                    """.trimMargin())
                }
                true
            }
            "logout" -> {
                authManager.logout()
                promptUser = ""
                view.println("Logged out.")
                true
            }
            "register" -> {
                addUserOperation(authManager)
                if (authManager.isAuthenticated) {
                    promptUser = authManager.customer!!.email
                }
                true
            }
            "account" -> {
                when (val customer = authManager.customer) {
                    null -> view.println("Not logged in.")
                    else -> view.printCustomer(customer)
                }
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
            * register          | Register a new account
            * login             | Sign in to your account
            * search <query>    | Search for your favourite book
            * view <#ID>        | View details about a search result
            * help              | View all commands
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
                cell("Account") {
                    columnSpan = 2
                }
            }
            row("register", "Register a new account")
            row("login", "Log in to your account")
            row("logout", "Log out of your account")
            row("account", "View account details of logged in customer")
            row("orders", "View the current status of orders for the logged in customer")
            row("cart", "View your cart")

            row {
                cellStyle {
                    borderBottom = true
                    paddingTop = 1
                }
                cell("Store") {
                    columnSpan = 2
                }
            }
            row("search <query>", "Search for books by the given <query>")
            row("view <#ID>", "Look at details for a specific book using its <#ID> from the search results")

            row {
                cellStyle {
                    borderBottom = true
                    paddingTop = 1
                }
                cell("Shopping") {
                    columnSpan = 2
                }
            }
            row("add <#ID>", "Add a book to your cart using its <#ID>")
            row("add", "Add a book directly to your cart after [view]ing it")
            row("remove <#ID>", "Remove a book from your cart using its <#ID> from the cart")
            row("order", "Place an order for everything that is currently in your shopping cart")
        }

        view.println("$helpTable")
    }
}

fun main() {
    CustomerControl(RealAuthManager, RealRepository).run()
}
