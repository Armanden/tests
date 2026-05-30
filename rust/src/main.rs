use std::io;
use num_bigint::BigInt;

fn main() {

	let mut n1 = BigInt::from(0);
	let mut n2 = BigInt::from(1);

	let mut input = String::new();

	println!("fibonacci sequence");
	println!("enter the amount of output");
	io::stdin()
		.read_line(&mut input)
    	.expect("Failed to read line");

    let input: usize = input.trim().parse().expect("Please enter a number");


	for i in 0..=input {
		let next = &n1 + &n2;
		n1 = n2;
		n2 = next;
		println!("{} {} {}",i, ":", n1)
		
		
	}

	
}
