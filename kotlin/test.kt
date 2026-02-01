fun main() {

println("hello ")

var customers = 8
println( "and welcome dear $customers customers and welcome dear ${customers * 2}  parents"  )


var hello = 10
hello += 3
hello *= 12
hello /= 4
println(hello)

val d: Int
d = 3

val e: String = "hello "

println(d)
println(e)



//lists

// Read only list
val readOnlyShapes = listOf("triangle", "square", "circle")
println(readOnlyShapes)

// Mutable list with explicit type declaration
val shapes: MutableList<String> = mutableListOf("triangle", "square", "circle")
println(shapes)

//val shapes: MutableList<String> = mutableListOf("triangle", "square", "circle")
//val shapesLocked: List<String> = shapes //locks the list not sure how useful it is

println("The first item in the list is: ${readOnlyShapes[0]}")

// $ required for text only



val gauss = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
println(((gauss.last() + 1) * gauss.last()) / 2)

gauss.add(11) //mutable list forced as adding values changes the list
gauss.add(12)

println("added values")
println(((gauss.last() + 1) * gauss.last()) / 2)



//sets
//Whereas lists are ordered and allow duplicate items, sets are unordered and only store unique items.

// Read-only set
val readOnlyFruit = setOf("apple", "banana", "cherry", "cherry")
// Mutable set with explicit type declaration
val fruit: MutableSet<String> = mutableSetOf("apple", "banana", "cherry", "cherry")

println(readOnlyFruit)
println("This set has ${readOnlyFruit.count()} items")


//val readOnlyFruit = setOf("apple", "banana", "cherry", "cherry")
println("banana" in readOnlyFruit) // possible binary set calculations


//maps item = price etc.

val readOnlyJuiceMenu = mapOf("apple" to 100, "kiwi" to 190, "orange" to 100)
println(readOnlyJuiceMenu)
// {apple=100, kiwi=190, orange=100}

// Mutable map with explicit type declaration
val juiceMenu: MutableMap<String, Int> = mutableMapOf("apple" to 100, "kiwi" to 190, "orange" to 100)
println(juiceMenu)


//val readOnlyJuiceMenu = mapOf("apple" to 100, "kiwi" to 190, "orange" to 100)
println("The value of apple juice is: ${readOnlyJuiceMenu["apple"]}")

//


val f: Int
val check = true

if (check) { //looks at check 0 1 
    f = 1
} else {
    f = 2
}

println(f)


val a = 1
val b = 2

println(if (a > b) a else b)



val obj = "Hello"

when (obj) {
    // Checks whether obj equals to "1"
    "1" -> println("One")
    // Checks whether obj equals to "Hello"
    "Hello" -> println("Greeting")
    // Default statement
    else -> println("Unknown")     
}

fun lights() {
    val trafficLightState = "Red" // This can be "Green", "Yellow", or "Red"

    val trafficAction = when {
        trafficLightState == "Green" -> "Go"
        trafficLightState == "Yellow" -> "Slow down"
        trafficLightState == "Red" -> "Stop"
        else -> "Malfunction"
    }

    println(trafficAction)
    // Stop
}
lights()

val range = mutableListOf(1..20)
println(range)


for (number in 1..5) { 
    // number is the iterator and 1..5 is the range
    print(number)
}

val cakes = listOf("carrot", "cheese", "chocolate")

for (cake in cakes) {
    println("Yummy, it's a $cake cake!")
}

fun name() {
    println("Enter your name:")
    val name = readLine() // Read user input
    println("Hello, $name!") // Print greeting
}
name()

var cakesEaten = 0
while (cakesEaten < 3) {
    println("Eat a cake")
    cakesEaten++
}
var cakesBaked = 0
do {
    println("Bake a cake")
    cakesBaked++
} while (cakesBaked < cakesEaten)

// if fun main exists then every function needs to be in it but otherwise you don't have to as the whole file acts as a main function file

fun sum(x: Int, y: Int): Int {
    return x + y
}

println(sum(34, 56))

fun printMessageWithPrefix(message: String, prefix: String) {
    println("[$prefix] $message")
}

    // Calling the function with hardcoded values
    printMessageWithPrefix(prefix = "Log", message = "Hello")

    // Getting user input
    println("Enter prefix:")
    val prefix: String? = readLine() // Nullable type to handle empty input
    println("Enter message:")
    val message: String? = readLine() // Nullable type to handle empty input

    // Calling the function with user input, with null checks
    if (prefix != null && message != null) {
        printMessageWithPrefix(message, prefix)
    } else {
        println("Invalid input: Please provide both prefix and message.")
    }


//fun uppercaseString(text: String): String {
  //  return text.uppercase()
//}


//lambda functions

val upperCaseString = { text: String -> text.uppercase() }
    println(upperCaseString("hello"))


val numbers = listOf(1, -2, 3, -4, 5, -6)

val positives = numbers.filter ({ x -> x > 0 })

val isNegative = { x: Int -> x < 0 }
val negatives = numbers.filter(isNegative)

println(positives)
// [1, 3, 5]
println(negatives)
// [-2, -4, -6]







//classes


class Customer

class Contact(val id: Int, var email: String = "example@gmail.com") {
    val category: String = "work"
     fun printId() {
        println(id)
    }
}
 //val contact = Contact(1, "mary@gmail.com")
    
    // Prints the value of the property: email
   // println(contact.email)           
    // mary@gmail.com

    // Updates the value of the property: email
    //contact.email = "jane@gmail.com"
    
    // Prints the new value of the property: email
   // println(contact.email)           
    // jane@gmail.com

 val contact = Contact(1, "mary@gmail.com")
 
    // Calls member function printId()
    contact.printId()           
    // 1


data class User(val name: String, val id: Int)

val user = User("Alex", 1)
val secondUser = User("Alex", 1)
val thirdUser = User("Max", 2)

// Compares user to second user
println("user == secondUser: ${user == secondUser}") 
// user == secondUser: true

// Compares user to third user
println("user == thirdUser: ${user == thirdUser}")   
// user == thirdUser: false

// neverNull has String type
    var neverNull: String = "This can't be null"

    // Throws a compiler error
    neverNull = null

    // nullable has nullable String type
    var nullable: String? = "You can keep a null here"

    // This is OK
    nullable = null

    // By default, null values aren't accepted
    var inferredNonNull = "The compiler assumes non-nullable"

    // Throws a compiler error
    inferredNonNull = null

    // notNull doesn't accept null values
    fun strLength(notNull: String): Int {                 
        return notNull.length
    }

    println(strLength(neverNull)) // 18
    println(strLength(nullable))  // Throws a compiler error















}