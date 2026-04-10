package main

import rl "vendor:raylib"
import "core:math"

SCREEN_WIDTH :: 1000
SCREEN_HEIGHT :: 800

main :: proc() {
    // Graph settings (locals inside main)
    scale : f32 = 50.0     // pixels per unit
    offset : rl.Vector2 = rl.Vector2{f32(SCREEN_WIDTH/2), f32(SCREEN_HEIGHT/2)}

    // Function to graph
    f :: proc(x: f32) -> f32 {
        return math.sin(x)
    }

    rl.InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Odin Graphing Calculator")
    defer rl.CloseWindow()

    rl.SetTargetFPS(60)

    for !rl.WindowShouldClose() {

        // Zoom
        wheel := rl.GetMouseWheelMove()
        if wheel != 0 {
            scale *= 1 + wheel * 0.1
        }

        // Pan
        if rl.IsKeyDown(rl.KeyboardKey.RIGHT) do offset.x -= 5
        if rl.IsKeyDown(rl.KeyboardKey.LEFT)  do offset.x += 5
        if rl.IsKeyDown(rl.KeyboardKey.UP)    do offset.y += 5
        if rl.IsKeyDown(rl.KeyboardKey.DOWN)  do offset.y -= 5

        rl.BeginDrawing()
        rl.ClearBackground(rl.BLACK)

        draw_grid(scale, offset)
        draw_axes(offset)
        draw_function(scale, offset, f)

        rl.DrawText("Mouse wheel = zoom | Arrows = pan", 10, 10, 20, rl.WHITE)

        rl.EndDrawing()
    }
}

// Draw grid
draw_grid :: proc(scale: f32, offset: rl.Vector2) {
    for x in -20..=20 {  // inclusive range
        screen_x := offset.x + f32(x) * scale
        rl.DrawLine(i32(screen_x), 0, i32(screen_x), SCREEN_WIDTH, rl.DARKGRAY)
    }

    for y in -20..=20 {
        screen_y := offset.y + f32(y) * scale
        rl.DrawLine(0, i32(screen_y), SCREEN_WIDTH, i32(screen_y), rl.DARKGRAY)
    }
}

// Draw axes
draw_axes :: proc(offset: rl.Vector2) {
    rl.DrawLine(0, i32(offset.y), SCREEN_WIDTH, i32(offset.y), rl.WHITE)
    rl.DrawLine(i32(offset.x), 0, i32(offset.x), SCREEN_HEIGHT, rl.WHITE)
}

// Draw function curve
draw_function :: proc(scale: f32, offset: rl.Vector2, f: proc(f32) -> f32) {
    prev := rl.Vector2{}
    first := true

    for px in 0..<SCREEN_WIDTH {  // exclusive range
        x := (f32(px) - offset.x) / scale
        y := f(x)

        py := offset.y - y * scale

        current := rl.Vector2{f32(px), py}

        if !first {
            rl.DrawLineV(prev, current, rl.GREEN)
        }

        prev = current
        first = false
    }
}
