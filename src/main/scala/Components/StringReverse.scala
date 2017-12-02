package Components

import VNode.SnabbdomFacade.VNode
import _root_.VNode.{div, input, span}
import core.Component
import core.Main.Target
import core.Message.SpecificPageMessage
import org.scalajs

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

class StringReverse
  (change: SpecificPageMessage[String])
  (s: String)
extends Component {
  val id: String = "stringRev"
  lazy val vnode: VNode =
    div(classes = List("counter"), contents = js.Array(
      input(
        id = id,
        props = Map("type" -> "text", "value" -> s),
        events = Map("input" -> ((e: scalajs.dom.TextEvent) => {
          change(e.target.asInstanceOf[Target].value.toString)
        }))
      ),
      span(classes = List("val"), contents = s.reverse)
    ))
}