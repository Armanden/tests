#include "raylib.h"
#include <string.h>
#include <stdbool.h>

#define MAX_LINES 10
#define LINE_HEIGHT 30
#define INPUT_FONT_SIZE 20
#define ITEM_FONT_SIZE 20

void DrawTextMultiline(const char *texts[], int count, int x, int y) {
    for (int i = 0; i < count; i++) {
        DrawText(texts[i], x, y + (i * LINE_HEIGHT), ITEM_FONT_SIZE, BLACK);
    }
}

int main(void) {
    InitWindow(600, 800, "Todo List");
    SetTargetFPS(60);

    // Todo storage
    char items[MAX_LINES][256] = { 0 };
    bool completed[MAX_LINES] = { 0 };
    int itemsCount = 0;

    // Input buffer
    char text[256] = { 0 };
    int letterCount = 0;
    const char *placeholder = "Type here ...";

    // Selection: -1 = input box, 0..itemsCount-1 = item selection
    int selected = -1;

    while (!WindowShouldClose()) {
        // --- Input handling ---

        // Navigate selection (cycles through: input -> items -> input)
        if (IsKeyPressed(KEY_UP)) {
            selected--;
            if (selected < -1) selected = itemsCount - 1;
        }
        if (IsKeyPressed(KEY_DOWN)) {
            selected++;
            if (selected > itemsCount - 1) selected = -1;
        }

        // Backspace edits input only
        if (IsKeyPressed(KEY_BACKSPACE)) {
            if (selected == -1) {
                if (letterCount > 0) {
                    letterCount--;
                    text[letterCount] = '\0';
                }
            } else {
                // If an item is selected, backspace will remove it (alias to delete)
                // Uncomment next block if you prefer backspace to delete selection:
                // // Remove selected item
                // for (int i = selected; i < itemsCount - 1; i++) {
                //     strcpy(items[i], items[i+1]);
                //     completed[i] = completed[i+1];
                // }
                // itemsCount--;
                // selected = -1;
            }
        }

        // Delete selected item
        if (IsKeyPressed(KEY_DELETE)) {
            if (selected >= 0 && selected < itemsCount) {
                for (int i = selected; i < itemsCount - 1; i++) {
                    strcpy(items[i], items[i + 1]);
                    completed[i] = completed[i + 1];
                }
                itemsCount--;
                selected = -1;
            }
        }

        // Toggle completion on selected item
        if (IsKeyPressed(KEY_SPACE)) {
            if (selected >= 0 && selected < itemsCount) {
                completed[selected] = !completed[selected];
            }
        }

        // Add item on Enter (only from input)
        if ((IsKeyPressed(KEY_ENTER) || IsKeyPressed(KEY_KP_ENTER)) && selected == -1) {
            if (letterCount > 0 && itemsCount < MAX_LINES) {
                // Copy input to items array
                strncpy(items[itemsCount], text, sizeof(items[0]) - 1);
                items[itemsCount][sizeof(items[0]) - 1] = '\0';
                completed[itemsCount] = false;
                itemsCount++;
                // clear input
                letterCount = 0;
                text[0] = '\0';
            }
        }

        // Character input for the input buffer
        int key = GetKeyPressed();
        while (key > 0) {
            if (selected == -1) { // only accept typing when input is active
                if (key >= 32 && key <= 126) { // printable ASCII
                    if (letterCount < (int)(sizeof(text) - 1)) {
                        text[letterCount] = (char)key;
                        letterCount++;
                        text[letterCount] = '\0';
                    }
                }
            }
            key = GetKeyPressed();
        }

        // --- Drawing ---
        BeginDrawing();
            ClearBackground(RAYWHITE);

            DrawText("Todo List", 10, 10, 30, BLACK);

            // Draw input box
            int inputX = 10;
            int inputY = 50;
            int inputW = 560;
            int inputH = LINE_HEIGHT;
            DrawRectangleLines(inputX - 4, inputY - 4, inputW + 8, inputH + 8, LIGHTGRAY);
            if (selected == -1) {
                DrawRectangle(inputX - 4, inputY - 4, inputW + 8, inputH + 8, Fade(GRAY, 0.05f)); // subtle highlight
            }
            if (letterCount == 0) {
                DrawText(placeholder, inputX + 4, inputY + 4, INPUT_FONT_SIZE, LIGHTGRAY);
            } else {
                DrawText(text, inputX + 4, inputY + 4, INPUT_FONT_SIZE, BLACK);
            }
            DrawText("Press Enter to add", 400, inputY + 4, 12, DARKGRAY);

            // Draw items
            int startY = inputY + inputH + 16;
            for (int i = 0; i < itemsCount; i++) {
                int y = startY + i * LINE_HEIGHT;

                // Selection highlight
                if (selected == i) {
                    DrawRectangle(6, y - 4, 588, LINE_HEIGHT, Fade(SKYBLUE, 0.15f));
                }

                // Checkbox
                const int boxX = 12;
                const int boxY = y;
                DrawRectangleLines(boxX, boxY, 18, 18, DARKGRAY);
                if (completed[i]) {
                    DrawLine(boxX + 3, boxY + 9, boxX + 7, boxY + 13, DARKGREEN);
                    DrawLine(boxX + 7, boxY + 13, boxX + 15, boxY + 5, DARKGREEN);
                }

                // Text color depending on completion
                Color textColor = completed[i] ? GRAY : BLACK;
                DrawText(items[i], boxX + 28, y, ITEM_FONT_SIZE, textColor);

                // Strikethrough for completed
                if (completed[i]) {
                    int tw = MeasureText(items[i], ITEM_FONT_SIZE);
                    int lineY = y + ITEM_FONT_SIZE/2;
                    DrawLine(boxX + 28, lineY, boxX + 28 + tw, lineY, GRAY);
                }
            }

            // Footer / instructions
            DrawText("Up/Down: select     Space: toggle done     Delete: remove item", 10, 700, 14, DARKGRAY);
            DrawText(TextFormat("Items: %d/%d", itemsCount, MAX_LINES), 10, 730, 14, DARKGRAY);

        EndDrawing();
    }

    CloseWindow();
    return 0;
}
