package $organization$.web

import org.scalajs.dom
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.JsDom.all._

@JSExportTopLevel("$name;format="Camel"$App")
object $name;format="Camel"$App {

  def setupUI(): Unit = {
    val helloWidget = h1(cls := "ui header")("Hello $name;format="Camel"$!")
    dom.document.body.appendChild(helloWidget.render)
  }

  @JSExport
  def main(): Unit = {
    dom.document.addEventListener ("DOMContentLoaded", { _: dom.Event =>
      println("DOM content loaded")
      setupUI()
    })
  }
}
