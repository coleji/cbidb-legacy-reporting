package Components

import core.Component
import core.Main.Target
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import org.scalajs
import org.scalajs.dom.document

import scala.scalajs.js

class StringReverse
  (change: SpecificPageMessage[String])
  (s: String)
extends Component {
  val id: String = "stringRev"
  lazy val vnode: VNode = {
    def value: js.Any = document.getElementById(id).textContent


    val spec: js.Object = js.Dynamic.literal("props" -> js.Dynamic.literal(
      "type" -> "text", "value" -> s
    ), "on" -> js.Dynamic.literal("input" -> ((e: scalajs.dom.TextEvent) => {
      change(e.target.asInstanceOf[Target].value.toString)
    })))


    h("div.counter", js.Array(
      h("input#" + id, spec: js.Any),
      h("span.val", s.reverse.asInstanceOf[js.Any]),
    ))
  }
}