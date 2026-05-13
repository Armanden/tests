package main

import (
	"embed"
	"html/template"
	"net/http"
)
//go:embed templates/*.html
var files embed.FS

var tmpl = template.Must(template.ParseFS(files, "templates/*.html"))

func main() {
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		tmpl.ExecuteTemplate(w, "index.html", map[string]string{
			"Name": "asshole",
		})
	})

	http.ListenAndServe(":8080", nil)
}
