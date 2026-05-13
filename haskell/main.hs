
square x = x * x

factorial 0 = 1
factorial n = n * factorial (n - 1)

isEven n =
    if n `mod` 2 == 0
        then True
        else False

main = do
    putStrLn "Enter a number:"
    
    input <- getLine
    let number = read input :: Int

    putStrLn ("Square: " ++ show (square number))
    putStrLn ("Factorial: " ++ show (factorial number))
    putStrLn ("Even? " ++ show (isEven number))

    let numbers = [1,2,3,4,5]
    putStrLn ("List doubled: " ++ show (map (*2) numbers))
