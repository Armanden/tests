import naylib

const
  ScreenWidth = 1280
  ScreenHeight = 720

initWindow(ScreenWidth, ScreenHeight, "Tiny 3D Game")

var camera = Camera3D(
  position: Vector3(x: 0, y: 2, z: 6),
  target: Vector3(x: 0, y: 1, z: 0),
  up: Vector3(x: 0, y: 1, z: 0),
  fovy: 60,
  projection: Perspective
)

setCameraMode(camera, FirstPerson)

setTargetFPS(60)

while not windowShouldClose():

  # Update camera movement
  updateCamera(addr camera)

  beginDrawing()

  clearBackground(RayWhite)

  beginMode3D(camera)

  # Ground
  drawPlane(
    Vector3(x: 0, y: 0, z: 0),
    Vector2(x: 20, y: 20),
    LightGray
  )

  # Cubes in the world
  for x in -4..4:
    for z in -4..4:
      if (x + z) mod 2 == 0:
        drawCube(
          Vector3(x: float32(x * 2), y: 1, z: float32(z * 2)),
          1,
          2,
          1,
          SkyBlue
        )

  # Player marker
  drawCube(
    Vector3(x: 0, y: 1, z: 0),
    1,
    2,
    1,
    Red
  )

  drawGrid(20, 1)

  endMode3D()

  drawText(
    "WASD + Mouse to move",
    20,
    20,
    20,
    Black
  )

  drawFPS(20, 50)

  endDrawing()

closeWindow()
