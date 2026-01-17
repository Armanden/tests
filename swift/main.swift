// GuessTheNumber.swift
// Simple command-line "Guess the Number" game in Swift

import Foundation

let lowerBound = 1
let upperBound = 100
let secret = Int.random(in: lowerBound...upperBound)

print("Welcome to Guess the Number!")
print("I'm thinking of a number between \(lowerBound) and \(upperBound).")
print("Try to guess it. Type 'q' to quit.\n")

var attempts = 0
var finished = false

while !finished {
    attempts += 1
    print("Enter your guess (#\(attempts)):", terminator: " ")
    guard let input = readLine()?.trimmingCharacters(in: .whitespacesAndNewlines), !input.isEmpty else {
        print("Please enter a number.")
        continue
    }
    if input.lowercased() == "q" {
        print("You gave up! The number was \(secret).")
        break
    }
    if let guess = Int(input) {
        if guess < secret {
            print("Too low.\n")
        } else if guess > secret {
            print("Too high.\n")
        } else {
            print("Correct! You guessed the number in \(attempts) attempt(s). 🎉")
            finished = true
        }
    } else {
        print("Invalid input. Please enter a whole number.\n")
    }
}
