#include <stdio.h>
#include <math.h>
#include <raylib.h>

// Helper: clamp value between a and b
static float Clamp(float v, float a, float b) {
    if (v < a) return a;
    if (v > b) return b;
    return v;
}

int main(void)
{
    const int screenW = 1200;
    const int screenH = 900;
    InitWindow(screenW, screenH, "Platformer");
    SetTargetFPS(120);

    // Player (circle)
    float px = 10.0f;
    const float radius = 10.0f;
    float py = (float)screenH - radius; // start on ground (center y)

    // Movement / physics
    const float speed = 300.0f;     // horizontal speed (px/s)
    const float gravity = 1500.0f;  // gravity accel (px/s^2)
    const float jumpForce = 650.0f; // initial jump velocity (px/s)
    float vy = 0.0f;                // vertical velocity
    bool onGround = true;

    // One platform
    Rectangle plat = { 400, 650, 80, 20 };
    Color platColor = GREEN;

    while (!WindowShouldClose())
    {
        float dt = GetFrameTime();

        // Horizontal input (simple)
        if ((IsKeyDown(KEY_RIGHT) || IsKeyDown(KEY_D)))
            px += speed * dt;
        if ((IsKeyDown(KEY_LEFT) || IsKeyDown(KEY_A)))
            px -= speed * dt;

        // Keep inside screen horizontally (clamp by radius)
        px = Clamp(px, radius, screenW - radius);

        // Jump (only if on ground)
        if ((IsKeyPressed(KEY_SPACE) || IsKeyPressed(KEY_UP) || IsKeyPressed(KEY_W)) && onGround)
        {
            vy = -jumpForce *1.5;
            onGround = false;
        }

        // Apply gravity and integrate position
        vy += gravity * dt *1.2;
        py += vy * dt *1.2;

        // --- Platform collision (circle vs rect) ---
        // Find closest point on rectangle to circle center
        float closestX = Clamp(px, plat.x, plat.x + plat.width);
        float closestY = Clamp(py, plat.y, plat.y + plat.height);

        float dx = px - closestX;
        float dy = py - closestY;
        float distSq = dx*dx + dy*dy;
        float rSq = radius * radius;

        if (distSq < rSq)
        {
            float dist = sqrtf(distSq);
            // Avoid division by zero; if exactly overlapping, push up
            float nx = 0.0f, ny = -1.0f;
            if (dist > 0.0001f) {
                nx = dx / dist;
                ny = dy / dist;
            }

            float penetration = radius - dist;
            // Push circle out along normal by penetration amount
            px += nx * penetration;
            py += ny * penetration;

            // Interpret normal to determine contact type
            // If normal points mostly up (ny < -0.5) -> landed on top
            if (ny < -0.5f) {
                // Land on top of platform
                vy = 0.0f;
                onGround = true;
                // Correct exact position to sit on platform top
                py = plat.y - radius;
            }
            // If normal points mostly down -> hit head
            else if (ny > 0.5f) {
                vy = 0.0f;
                // place just below platform
                py = plat.y + plat.height + radius;
            }
            else {
                // Side collision - stop horizontal movement by nudging out
                // No horizontal velocity tracked here, so just ensure player isn't stuck
                // Keep onGround as-is (likely false unless also on top)
            }
        } else {
            // If not colliding with platform, and not at ground, ensure onGround false
            // (We will set true again if ground collision occurs below)
            if (py < (float)screenH - radius - 0.5f) onGround = false;
        }

        // Ground collision (bottom of screen)
        float groundY = (float)screenH - radius;
        if (py > groundY)
        {
            py = groundY;
            vy = 0.0f;
            onGround = true;
        }

        BeginDrawing();
        ClearBackground(BLACK);

        DrawRectangleRec(plat, platColor);
        DrawCircle((int)roundf(px), (int)roundf(py), (int)radius, WHITE);

        DrawText(onGround ? "Grounded" : "Airborne", 10, 10, 20, GRAY);

        EndDrawing();
    }

    CloseWindow();
    return 0;
}
