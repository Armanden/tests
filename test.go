package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	user := askName()
	greet(user)

	fmt.Println("Welcome to Personal Finance Tracker")

	fmt.Println("Enter Your Current Balance")
	balance := fBalance()

	income := fIncome()
	fmt.Println("Your income is", income)

	expenses := fExpenses()
	fmt.Println("Your expenses is", expenses)

	_ = balance // to avoid unused variable warning
}

// Asks the user to provide their name.
func askName() string {
	fmt.Println("Hello! Please enter your name:")
	reader := bufio.NewReader(os.Stdin)
	name, _ := reader.ReadString('\n')
	return strings.TrimSpace(name)
}

// Greets a user by name.
func greet(user string) {
	fmt.Printf("Hello, %s!\n", user)
}

func fBalance() int {
	fmt.Println("Enter Current Balance:")
	reader := bufio.NewReader(os.Stdin)
	input, _ := reader.ReadString('\n')
	input = strings.TrimSpace(input)

	balance, _ := strconv.Atoi(input)
	return balance
}

func fIncome() int {
	fmt.Println("Enter income:")
	reader := bufio.NewReader(os.Stdin)
	input, _ := reader.ReadString('\n')
	input = strings.TrimSpace(input)

	income, _ := strconv.Atoi(input)
	return income
}

func fExpenses() int {
	fmt.Println("Enter total expenses:")
	reader := bufio.NewReader(os.Stdin)
	input, _ := reader.ReadString('\n')
	input = strings.TrimSpace(input)

	expenses, _ := strconv.Atoi(input)
	return expenses
}
