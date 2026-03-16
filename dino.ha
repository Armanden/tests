use fmt;
use time;

const WIDTH: int = 40;
const GROUND: int = 5;

fn draw(dino_y: int, cactus_x: int, score: int) void = {
	fmt::printfln("\x1b[2J\x1b[H")!;

	for (let y = 0; y < GROUND + 2; y += 1) {
		for (let x = 0; x < WIDTH; x += 1) {

			if (x == 5 && y == dino_y) {
				fmt::printfln("D")!;
			} else if (x == cactus_x && y == GROUND) {
				fmt::printfln("#")!;
			} else if (y == GROUND + 1) {
				fmt::printfln("-")!;
			} else {
				fmt::printfln(" ")!;
			};

		};
		fmt::printfln("\n")!;
	};

	fmt::printfln("Score: {}", score)!;
};

export fn main() void = {
	let cactus_x: int = WIDTH;
	let dino_y: int = GROUND;
	let velocity: int = 0;
	let score: int = 0;

	for (true) {
		velocity -= 1;
		dino_y -= velocity;

		if (dino_y > GROUND) {
			dino_y = GROUND;
			velocity = 0;
		};

		cactus_x -= 1;
		if (cactus_x < 0) {
			cactus_x = WIDTH;
		};

		if (cactus_x == 5 && dino_y == GROUND) {
			fmt::printfln("Game Over!")!;
			fmt::printfln("Final Score: {}", score)!;
			break;
		};

		draw(dino_y, cactus_x, score);

		score += 1;
		time::sleep(time::SECOND / 10 );
	};
};
