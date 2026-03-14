#set page(width: auto, height: auto)
#import "@preview/cetz:0.4.2"

We are working on
#cetz.canvas({
  import cetz.draw: *
  line((-1.5, 0), (1.5, 0))
  line((0, -1.5), (0, 1.5))

circle((0, 0))
line((0, 0), (1, 1))

line((-1.5, 0), (1.5, 0))
line((0, -1.5), (0, 1.5))


rect((0, 0), (0.5, 0.5))
rect((-0.5, -0.5), (-1, -1))

grid((-1.5, -1.5), (1.5, 1.5), step: 0.5, stroke: gray + 0.2pt)

line((-2,0), (2,0))
  line((0,-1), (0,4))


})