package demo.basic.js;

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlin.browser.document

fun DemoJsMain(args: Array<String>) {
    var root = document.getElementById("root")
    //  create a div tag which contains a h1 tag and a button
    var div = document.create.div {
        h1() {
            + "Hello JavaScript"
        }
        button(classes = "btn") {
            + "Click Me!"
            onClickFunction = { println("Clicked!!!")}
        }
    }
    // add div tag to root
    root!!.appendChild(div)
}
