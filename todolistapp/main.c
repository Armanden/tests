#include "raylib.h"
#include <string.h>
#include <stdio.h>

#define MAX_TEXT_LENGTH 256

int main(void) {
    InitWindow(600, 800, "Todo List");
    SetTargetFPS(60);

    char text[MAX_TEXT_LENGTH] = { 0 };
    int letterCount = 0;
    const char *placeholder = "Type here ...";

    while (!WindowShouldClose()) {
        // Handle backspace
        if (IsKeyPressed(KEY_BACKSPACE)) {
            if (letterCount > 0) {
                letterCount--;
                text[letterCount] = '\0';
            }
        }

        // Handle character input
        int key = GetKeyPressed();
        while (key > 0) {
            // Capture valid keyboard input based on the default keyboard layout
            if (key >= 32 && key <= 126) { // ASCII range
                if (letterCount < (int)(sizeof(text) - 1)) {
                    text[letterCount] = (char)key; // Store the character
                    letterCount++;
                    text[letterCount] = '\0'; // Null-terminate the string
                }
            }
            key = GetKeyPressed();
        }

        // Begin drawing
        BeginDrawing();
        ClearBackground(RAYWHITE);
        DrawText("Todo List", 10, 10, 30, BLACK);

        // Draw placeholder or text
        if (letterCount == 0) {
            DrawText(placeholder, 10, 40, 20, LIGHTGRAY);
        } else {
            DrawText(text, 10, 40, 20, BLACK);
        }
        
        EndDrawing();
    }

    CloseWindow();
    return 0;
}

