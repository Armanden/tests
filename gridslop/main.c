#include "raylib.h"
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

const int screenW = 540;
const int screenH = 540;
const int gridSize = 9;
const float cellSize = 60.0f; // Size of each cell in pixels

// Structure to represent the cell's state
typedef struct {
int value;     // Value of the cell, 0 means empty
bool isFixed;  // True if cell has a fixed value from generation
} Cell;

// Function prototypes
void GenerateSudoku(Cell cells[9][9]);
bool SolveSudoku(Cell cells[9][9]);
bool IsValid(Cell cells[9][9], int row, int col, int num);
void DrawBoard(Cell cells[9][9], int selectedRow, int selectedCol);
void ShowMenu();

int main(void) {
InitWindow(screenW, screenH, "Sudoku");
SetTargetFPS(300);



Cell cells[9][9]; // Fixed-size array for cells
bool inGame = false;

while (!WindowShouldClose()) {
    if (!inGame) {
        ShowMenu();
        if (IsMouseButtonPressed(MOUSE_LEFT_BUTTON)) {
            Vector2 mousePos = GetMousePosition();
            if (mousePos.x > screenW / 4 && mousePos.x < (screenW / 4) * 3 &&
                mousePos.y > screenH / 2 && mousePos.y < screenH / 2 + 50) {
                GenerateSudoku(cells);
                inGame = true;
            } else if (mousePos.y > screenH / 2 + 60 && mousePos.y < screenH / 2 + 110) {
                CloseWindow();
                return 0; // Exit game
            }
        }
    } else {
        // Start drawing
        BeginDrawing();
        ClearBackground(WHITE);

        // Draw the Sudoku board
        static int selectedRow = -1;
        static int selectedCol = -1;

        DrawBoard(cells, selectedRow, selectedCol);

        // Check for mouse click to select cell
        if (IsMouseButtonPressed(MOUSE_LEFT_BUTTON)) {
            Vector2 mousePosition = GetMousePosition();
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    float x = col * cellSize;
                    float y = row * cellSize;
                    if (mousePosition.x >= x && mousePosition.x <= x + cellSize &&
                        mousePosition.y >= y && mousePosition.y <= y + cellSize) {
                        selectedRow = row;
                        selectedCol = col;
                    }
                }
            }
        }

        // Keyboard input for numbers 1-9
        for (int num = 1; num <= 9; num++) {
            if (IsKeyPressed(num + KEY_ONE - 1)) { // Adjust for key mapping
                if (selectedRow >= 0 && selectedRow < gridSize &&
                    selectedCol >= 0 && selectedCol < gridSize &&
                    !cells[selectedRow][selectedCol].isFixed) {
                    cells[selectedRow][selectedCol].value = num; // Set cell value
                }
            }
        }

        EndDrawing();
    }
}

CloseWindow();
return 0;}

void DrawBoard(Cell cells[9][9], int selectedRow, int selectedCol) {
for (int row = 0; row < gridSize; row++) {
for (int col = 0; col < gridSize; col++) {
float x = col * cellSize;
float y = row * cellSize;



        // Determine color of cell
        Color cellColor = LIGHTGRAY;
        if (selectedRow == row && selectedCol == col) cellColor = GREEN; // Highlight selected cell

        DrawRectangle(x, y, cellSize, cellSize, cellColor);
        DrawRectangleLines(x, y, cellSize, cellSize, BLACK);

        // Draw the cell value
        if (cells[row][col].value > 0) {
            DrawText(TextFormat("%d", cells[row][col].value), 
                     x + cellSize / 2 - 10, 
                     y + cellSize / 2 - 10, 
                     20, BLACK);
        }
    }
}

// Draw grid lines for the 3x3 sections
for (int i = 1; i < 3; i++) {
  DrawRectangle(i * (cellSize * 3), 0, 5, screenH, BLACK);
        DrawRectangle(0, i * (cellSize * 3), screenW, 5, BLACK);
    }
}

void ShowMenu() {
    BeginDrawing();
    ClearBackground(RAYWHITE);
    DrawText("Sudoku Game", screenW / 3, screenH / 4, 40, BLACK);
    DrawRectangle(screenW / 4, screenH / 2, screenW / 2, 50, LIGHTGRAY);
    DrawText("Start Game", screenW / 3 + 50, screenH / 2 + 10, 20, BLACK);
    DrawRectangle(screenW / 4, screenH / 2 + 60, screenW / 2, 50, LIGHTGRAY);
    DrawText("Exit", screenW / 3 + 150, screenH / 2 + 70, 20, BLACK);
    EndDrawing();
}

void GenerateSudoku(Cell cells[9][9]) {
    // Clear the grid
    for (int row = 0; row < gridSize; row++) {
        for (int col = 0; col < gridSize; col++) {
            cells[row][col].value = 0;
            cells[row][col].isFixed = false;
        }
    }

    // Generate a completely solved Sudoku puzzle
    SolveSudoku(cells);

    // Remove some numbers randomly to create the puzzle
    int removeCount = rand() % 20 + 20; // Remove between 20 to 40 numbers
    while (removeCount > 0) {
        int row = rand() % gridSize;
        int col = rand() % gridSize;

        if (cells[row][col].isFixed) {
            cells[row][col].value = 0; // Remove number
            removeCount--;
        }
    }
}

bool SolveSudoku(Cell cells[9][9]) {
    for (int row = 0; row < gridSize; row++) {
        for (int col = 0; col < gridSize; col++) {
            if (cells[row][col].value == 0) { // Find an empty cell
                for (int num = 1; num <= 9; num++) {
                    if (IsValid(cells, row, col, num)) {
                        cells[row][col].value = num; // Place the number
                        if (SolveSudoku(cells)) {
                            return true; // If solved, return
                        }
                        cells[row][col].value = 0; // Backtrack
                    }
                }
                return false; // No valid number found, trigger backtrack
            }
        }
    }
    return true; // Solved
}

bool IsValid(Cell cells[9][9], int row, int col, int num) {
    // Check row
    for (int c = 0; c < gridSize; c++) {
        if (cells[row][c].value == num) return false;
    }
    // Check column
    for (int r = 0; r < gridSize; r++) {
        if (cells[r][col].value == num) return false;
    }
    // Check 3x3 box
    int boxRowStart = row / 3 * 3;
    int boxColStart = col / 3 * 3;
    for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
            if (cells[boxRowStart + r][boxColStart + c].value == num) return false;
        }
    }
    return true; // Valid
}


