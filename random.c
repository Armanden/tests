#include <stdio.h>
#include <raylib.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

const char *g[] = {"up", "down", "left", "right"}; 

int main() {
    Color colors[] = {
        {20, 160, 133, 255}, // Original green
        {255, 0, 0, 255},   // Red
        {0, 255, 0, 255},   // Green
        {0, 0, 255, 255},   // Blue
        {255, 255, 0, 255}, // Yellow
        {255, 165, 0, 255}  // Orange
    };
    
    int ballX = 400;
    int ballY = 400;
    int speed = 5; 
    const int ballRadius = 20; 
    InitWindow(800, 800, "Disco Ball");
    SetTargetFPS(60);

    srand((unsigned int)time(NULL));
    const char *c = g[rand() % (sizeof(g) / sizeof(g[0]))]; 

    float changeDirectionTimer = 0.0f; 
    const float changeInterval = 1.0f;  

    Color currentBackgroundColor = colors[rand() % (sizeof(colors) / sizeof(colors[0]))]; 

    while (!WindowShouldClose()) {
        // Update the timer
        changeDirectionTimer += GetFrameTime();


        if (changeDirectionTimer >= changeInterval) {
            c = g[rand() % (sizeof(g) / sizeof(g[0]))];
            currentBackgroundColor = colors[rand() % (sizeof(colors) / sizeof(colors[0]))]; 
            changeDirectionTimer = 0.0f; 
        }

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

        BeginDrawing();
        ClearBackground(currentBackgroundColor); 
        DrawCircle(ballX, ballY, ballRadius, WHITE);
        EndDrawing();
    }

    CloseWindow(); 
    return 0;
}

