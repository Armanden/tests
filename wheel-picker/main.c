#include "raylib.h"
#include <math.h>

#define ITEM_COUNT 10
#define ITEM_HEIGHT 40
#define VISIBLE_ITEMS 5

int main(void)
{
    InitWindow(400, 600, "Wheel Picker");
    SetTargetFPS(60);

    const char *items[ITEM_COUNT] = {
        "Apple", "Banana", "Cherry", "Date", "Elderberry",
        "Fig", "Grape", "Honeydew", "Kiwi", "Lemon"
    };

    float scrollOffset = 0.0f;
    int selectedIndex = 0;

    while (!WindowShouldClose())
    {
        // Input
        float wheel = GetMouseWheelMove();
        scrollOffset += wheel * ITEM_HEIGHT;

        // Snap to closest item
        if (fabs(scrollOffset) >= ITEM_HEIGHT)
        {
            int step = (int)(scrollOffset / ITEM_HEIGHT);
            selectedIndex -= step;
            scrollOffset -= step * ITEM_HEIGHT;
        }

        // Clamp selection
        if (selectedIndex < 0) selectedIndex = 0;
        if (selectedIndex >= ITEM_COUNT) selectedIndex = ITEM_COUNT - 1;

        BeginDrawing();
        ClearBackground(RAYWHITE);

        int centerY = GetScreenHeight() / 2;

        // Draw items
        for (int i = -VISIBLE_ITEMS/2; i <= VISIBLE_ITEMS/2; i++)
        {
            int index = selectedIndex + i;
            if (index < 0 || index >= ITEM_COUNT) continue;

            float y = centerY + i * ITEM_HEIGHT + scrollOffset;

            // Fade effect based on distance
            float dist = fabs((float)i + scrollOffset / ITEM_HEIGHT);
            float alpha = 1.0f - fminf(dist * 0.3f, 0.8f);

            Color color = Fade(BLACK, alpha);

            DrawText(items[index], 150, (int)y - 10, 20, color);
        }

        // Highlight center
        DrawRectangleLines(100, centerY - ITEM_HEIGHT/2, 200, ITEM_HEIGHT, RED);

        EndDrawing();
    }

    CloseWindow();
    return 0;
}
