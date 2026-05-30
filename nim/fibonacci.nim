import bigints
import strutils

var n1 = initBigInt(0)
var n2 = initBigInt(1)

echo "enter the amount to output"
let input = parseInt(readLine(stdin))

for i in 0..input:
  stdout.write(i, ": ", n1)
  echo ""
    
  let next = n1 + n2
  n1 = n2
  n2 = next
