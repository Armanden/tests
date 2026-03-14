#set page(width: auto, height: auto)
#import "@preview/cetz:0.4.2"
#import "@preview/cetz-plot:0.1.3"

#cetz.canvas({
  import cetz.draw: *
  import cetz-plot: *

set-style(axes: (
		storke: (dash: "dotted", paint: gray),
		x: (mark: (start: ">", end: ">" ), padding: 1 ),
		y: (mark: none),
		tick: (stroke: gray + .5pt),
		))

plot.plot(size: (5,4), axis-style: "school-book",
y-tick-step: none, {
	     plot.add(calc.sin, domain: (0, calc.pi * 2))
})


})