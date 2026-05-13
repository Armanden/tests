package main

import "core:fmt"
import "core:os"
import "core:strings"

CollisionObject :: struct {
    x, y: f32,
    width, height: f32,
}

LevelMap :: struct {
    width, height: int,
    player_start_x, player_start_y: f32,
    target_x, target_y: f32,
    obstacles: []CollisionObject,
    name: string,
}

Level :: struct {
    number: int,
    level_map: LevelMap,
}

GameState :: struct {
    current_level: int,
    levels: []Level,
    player: CollisionObject,
    target: CollisionObject,
    obstacles: []CollisionObject,
}

level_data := []Level{
    {number = 1, level_map = {width = 20, height = 15, player_start_x = 2, player_start_y = 2, target_x = 15, target_y = 2, name = "The Beginning", obstacles = {}}},
    {number = 2, level_map = {width = 25, height = 18, player_start_x = 2, player_start_y = 2, target_x = 20, target_y = 2, name = "The Maze", obstacles = {
        {x = 160, y = 0, width = 64, height = 256},
        {x = 320, y = 160, width = 64, height = 416},
        {x = 480, y = 0, width = 64, height = 320},
    }}},
    {number = 3, level_map = {width = 30, height = 20, player_start_x = 2, player_start_y = 2, target_x = 25, target_y = 2, name = "The Gauntlet", obstacles = {
        {x = 96, y = 0, width = 64, height = 480},
        {x = 256, y = 160, width = 64, height = 480},
        {x = 416, y = 0, width = 64, height = 320},
        {x = 416, y = 384, width = 64, height = 256},
        {x = 576, y = 160, width = 64, height = 480},
        {x = 736, y = 0, width = 64, height = 384},
    }}},
}

init_game :: proc() -> GameState {
    level := level_data[0]
    
    return GameState{
        current_level = 0,
        levels = level_data,
        player = {x = level.level_map.player_start_x * 32, y = level.level_map.player_start_y * 32, width = 32, height = 32},
        target = {x = level.level_map.target_x * 32, y = level.level_map.target_y * 32, width = 32, height = 32},
        obstacles = level.level_map.obstacles,
    }
}

check_collision :: proc(a, b: CollisionObject) -> bool {
    return !(a.x + a.width < b.x ||
             b.x + b.width < a.x ||
             a.y + a.height < b.y ||
             b.y + b.height < a.y)
}

check_wall_collision :: proc(player: CollisionObject, obstacles: []CollisionObject) -> bool {
    for obs in obstacles {
        if check_collision(player, obs) {
            return true
        }
    }
    return false
}

next_level :: proc(state: ^GameState) -> bool {
    if state.current_level < len(state.levels) - 1 {
        state.current_level += 1
        load_level(state)
        return true
    }
    return false
}

load_level :: proc(state: ^GameState) {
    level := state.levels[state.current_level]
    level_map := level.level_map
    
    state.player.x = level_map.player_start_x * 32
    state.player.y = level_map.player_start_y * 32
    state.target.x = level_map.target_x * 32
    state.target.y = level_map.target_y * 32
    state.obstacles = level_map.obstacles
    
    fmt.println("Loading Map:", level_map.name)
    fmt.println("Map size:", level_map.width, "x", level_map.height)
}

move_player :: proc(state: ^GameState, dx, dy: f32) {
    old_x := state.player.x
    old_y := state.player.y
    
    state.player.x += dx
    state.player.y += dy
    
    if check_wall_collision(state.player, state.obstacles) {
        state.player.x = old_x
        state.player.y = old_y
        fmt.println("Blocked by wall!")
        return
    }
    
    if check_collision(state.player, state.target) {
        fmt.println("Target reached!")
        if next_level(state) {
            fmt.println("Advanced to next level!")
        } else {
            fmt.println("All levels completed!")
        }
    }
}

render :: proc(state: ^GameState) {
    level := state.levels[state.current_level]
    level_map := level.level_map
    
    fmt.println()
    fmt.println("====================")
    fmt.println("Level", level.number, "-", level_map.name)
    fmt.println("====================")
    fmt.printf("Player: (%.0f, %.0f)\n", state.player.x, state.player.y)
    fmt.printf("Target: (%.0f, %.0f)\n", state.target.x, state.target.y)
    fmt.printf("Obstacles: %d\n", len(state.obstacles))
    fmt.println("====================")
}

main :: proc() {
    args := os.args
    
    state := init_game()
    render(&state)
    
    if len(args) < 2 {
        fmt.println("Usage: level_game <moves>")
        fmt.println("Example: level_game wsddawss")
        return
    }
    
    moves := args[1]
    
    speed: f32 = 32.0
    
    for i in 0..<len(moves) {
        m := moves[i]
        dx, dy: f32 = 0, 0
        
        if m == 'w' {
            dy = -speed
        } else if m == 's' {
            dy = speed
        } else if m == 'a' {
            dx = -speed
        } else if m == 'd' {
            dx = speed
        } else {
            fmt.println("Invalid move:", m)
            continue
        }
        
        move_player(&state, dx, dy)
        render(&state)
        
        if state.current_level >= len(state.levels) {
            fmt.println("YOU WIN! All levels completed!")
            break
        }
    }
    
    if state.current_level < len(state.levels) {
        fmt.println("Game incomplete. Moves remaining.")
    }
}