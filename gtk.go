package main

import (
	"github.com/gotk3/gotk3/gtk"
)

func main() {
	gtk.Init(nil)

	win, _ := gtk.WindowNew(gtk.WINDOW_TOPLEVEL)
	win.SetTitle("GTK Test")
	win.SetDefaultSize(300, 200)

	win.ShowAll()
	win.Connect("destroy", func() {
		gtk.MainQuit()
	})

	gtk.Main()
}
