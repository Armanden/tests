const std = @import("std");
const opengl_zig = @import("opengl_zig");

const c = @cImport({
    @cInclude("GLFW/glfw3.h");
    @cInclude("GL/gl.h");
    @cInclude("glad/gl.h");
});


const vertices = [_]f32{
    -0.5, -0.5, 0.0,
     0.5, -0.5, 0.0,
     0.0,  0.5, 0.0,
};


pub fn main() !void {
    if (c.glfwInit() == 0) {
        return error.InitFailed;
    }
    defer c.glfwTerminate();

    const window = c.glfwCreateWindow(800, 600, "Zig OpenGL", null, null); // initialize the window
    if (window == null) {
        return error.WindowCreationFailed;
    }

    c.glfwMakeContextCurrent(window);

if (c.gladLoadGL() == 0) return error.GLLoadFailed;


var vao: u32 = 0;
var vbo: u32 = 0;

c.glGenVertexArrays(1, &vao);
c.glGenBuffers(1, &vbo);

c.glBindVertexArray(vao);

c.glBindBuffer(c.GL_ARRAY_BUFFER, vbo);
c.glBufferData(
    c.GL_ARRAY_BUFFER,
    vertices.len * @sizeOf(f32),
    &vertices,
    c.GL_STATIC_DRAW,
);


c.glVertexAttribPointer(
    0,              // shader layout location
    3,              // 3 floats per vertex (x, y, z)
    c.GL_FLOAT,
    c.GL_FALSE,
    3 * @sizeOf(f32), // stride (distance between vertices)
    null,
);

c.glEnableVertexAttribArray(0);





    while (c.glfwWindowShouldClose(window) == 0) { // main loop like in raylib 

 c.glClearColor(0.2, 0.3, 0.8, 1.0);
    c.glClear(c.GL_COLOR_BUFFER_BIT);

// draw triangle
    c.glBindVertexArray(vao);
    c.glDrawArrays(c.GL_TRIANGLES, 0, 3);

        c.glfwPollEvents();
        c.glfwSwapBuffers(window);

    }
}

