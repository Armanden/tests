#include <stdio.h>
#include <raylib.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

const char *g[] = {"up", "down", "left", "right"}; 

int main() {
    Color green = {20, 160, 133, 255};
    int ballX = 400;
    int ballY = 400;
    int speed = 5; // Ball movement speed
    const int ballRadius = 20; // Radius of the ball
    InitWindow(800, 800, "Random Ball");
    SetTargetFPS(60);

    srand((unsigned int)time(NULL));
    const char *c = g[rand() % (sizeof(g) / sizeof(g[0]))]; 

    float changeDirectionTimer = 0.0f; 
    const float changeInterval = 1.0f;  

    while (!WindowShouldClose()) {
        // Update the timer
        changeDirectionTimer += GetFrameTime();

        // Check if it's time to change direction
        if (changeDirectionTimer >= changeInterval) {
            c = g[rand() % (sizeof(g) / sizeof(g[0]))]; // Choose a new direction
            changeDirectionTimer = 0.0f; // Reset timer
        }

        // Move the ball in the current direction with boundary checks
        if (strcmp(c, "right") == 0) {
            if (ballX + speed + ballRadius <= GetScreenWidth()) {
                ballX += speed;
            }
        } else if (strcmp(c, "left") == 0) {
            if (ballX - speed - ballRadius >= 0) {
                ballX -= speed;
            }
        } else if (strcmp(c, "up") == 0) {
            if (ballY - speed - ballRadius >= 0) {
                ballY -= speed;
            }
        } else if (strcmp(c, "down") == 0) {
            if (ballY + speed + ballRadius <= GetScreenHeight()) {
                ballY += speed;
            }
        }

        // Draw the ball
        BeginDrawing();
        ClearBackground(green);
        DrawCircle(ballX, ballY, ballRadius, WHITE);
        EndDrawing();
    }

    CloseWindow(); // Close the window cleanly
    return 0;
}

