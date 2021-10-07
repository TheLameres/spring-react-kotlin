package thelameres.hospital.client

import kotlinx.browser.document
import react.dom.render

fun main() {
    kotlinext.js.require("bootstrap/dist/css/bootstrap.css")
    kotlinext.js.require("bootstrap/dist/js/bootstrap.js")
    render(document.getElementById("root")) {
        child(App::class) {}
    }
}

const val SERVER = ""
