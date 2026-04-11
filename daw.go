package main

import (
	"log"
	"os"
	"path/filepath"
	"time"

	"github.com/faiface/beep"
	"github.com/faiface/beep/speaker"
	"github.com/faiface/beep/wav"
	"github.com/gotk3/gotk3/gtk"
)

type Track struct {
	name     string
	streamer beep.StreamSeekCloser
	ctrl     *beep.Ctrl
}

var tracks []*Track

func loadTrack(path string, sr beep.SampleRate) (*Track, error) {
	f, err := os.Open(path)
	if err != nil {
		return nil, err
	}

	streamer, _, err := wav.Decode(f)
	if err != nil {
		return nil, err
	}

	ctrl := &beep.Ctrl{Streamer: streamer, Paused: true}

	return &Track{
		name:     filepath.Base(path),
		streamer: streamer,
		ctrl:     ctrl,
	}, nil
}

func main() {
	// ---------------- AUDIO INIT (ONLY ONCE)
	sr := beep.SampleRate(44100)
	speaker.Init(sr, sr.N(time.Second/10))

	// ---------------- LOAD TRACKS
	files, err := os.ReadDir("samples")
	if err != nil {
		log.Fatal(err)
	}

	for _, f := range files {
		if filepath.Ext(f.Name()) != ".wav" {
			continue
		}

		t, err := loadTrack("samples/"+f.Name(), sr)
		if err != nil {
			log.Println(err)
			continue
		}

		tracks = append(tracks, t)

		// IMPORTANT: each track must be played individually
		speaker.Play(t.ctrl)
	}

	// ---------------- GTK INIT
	gtk.Init(nil)

	win, _ := gtk.WindowNew(gtk.WINDOW_TOPLEVEL)
	win.SetTitle("Mini DAW (GTK Go)")
	win.SetDefaultSize(500, 300)

	box, _ := gtk.BoxNew(gtk.ORIENTATION_VERTICAL, 5)
	win.Add(box)

	for _, t := range tracks {
		track := t

		row, _ := gtk.BoxNew(gtk.ORIENTATION_HORIZONTAL, 5)
		label, _ := gtk.LabelNew(track.name)

		playBtn, _ := gtk.ButtonNewWithLabel("Play")
		stopBtn, _ := gtk.ButtonNewWithLabel("Stop")

		playBtn.Connect("clicked", func() {
			track.ctrl.Paused = false
		})

		stopBtn.Connect("clicked", func() {
			track.ctrl.Paused = true
			track.streamer.Seek(0)
		})

		row.Add(label)
		row.Add(playBtn)
		row.Add(stopBtn)

		box.Add(row)
	}

	win.Connect("destroy", func() {
		gtk.MainQuit()
	})

	win.ShowAll()
	gtk.Main()
}
