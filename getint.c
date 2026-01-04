#include <raylib.h>

int main() {
    // Initialization
    const int screenWidth = 800;
    const int screenHeight = 600;
    InitWindow(screenWidth, screenHeight, "Raylib Keyboard Input Example");

    // Text buffer to store input
    char text[256] = "Type here...";
    int letterCount = 0;

    while (!WindowShouldClose()) {
        // Update
        if (IsKeyPressed(KEY_ENTER)) {
            letterCount = 0; // Reset input on Enter
        }

        // Check for character input
        for (int i = 0; i < 26; i++) { // Check for A-Z keys
            if (IsKeyPressed(KEY_A + i)) {
                text[letterCount] = 'A' + i;
                letterCount++;
            }
        }

        // Ensure text does not exceed the maximum length
        if (letterCount >= sizeof(text) - 1) {
            letterCount = sizeof(text) - 2; // Leave space for null terminator
        }
        text[letterCount] = '\0'; // Null-terminate the string

        // Draw
        BeginDrawing();
        ClearBackground(RAYWHITE);
        DrawText(text, 10, 10, 20, BLACK);
        EndDrawing();
    }

    // Cleanup
    CloseWindow();
    return 0;
}

