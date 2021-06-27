package cli

import application.Application
import java.util.*

fun main(args: Array<String>) {

  val scanner = Scanner(System.`in`)

  while(true) {
    print("Enter your containers destinations: ")
    val enteredDestinations = scanner.next()

    val calculatedResult = Application().transport(enteredDestinations)
    println("$enteredDestinations -> $calculatedResult hours")
  }
}
