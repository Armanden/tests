fun main() {
    println("This is a calculator in Kotlin")

    println("Enter first number:")
    val numberOneInput = readLine() 
    val numberOne: Int? = numberOneInput?.toIntOrNull() 

    println("Enter equation symbol (+, -, *, /):")
    val equationSymbol = readLine() 

    println("Enter second number:")
    val numberTwoInput = readLine() 
    val numberTwo: Int? = numberTwoInput?.toIntOrNull() 

    if (numberOne != null && numberTwo != null && equationSymbol != null) {
        val result = when (equationSymbol) {
            "+" -> numberOne + numberTwo
            "-" -> numberOne - numberTwo
            "*" -> numberOne * numberTwo
            "/" -> if (numberTwo != 0) numberOne / numberTwo else "Cannot divide by zero"
            else -> "Invalid equation symbol"
        }
        println("Result: $numberOne $equationSymbol $numberTwo = $result")
    } else {
        println("Invalid input. Please make sure to enter valid numbers and an equation symbol.")
    }
}
