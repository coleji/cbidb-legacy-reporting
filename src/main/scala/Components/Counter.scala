package Components

import VNode.SnabbdomFacade.VNode
import _root_.VNode.{div, input, span}
import core.Component
import core.Message.SpecificPageNoArgMessage

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

class Counter
  (increment: SpecificPageNoArgMessage, decrement: SpecificPageNoArgMessage)
  (count: Int)
extends Component {
  lazy val vnode: VNode =
    div(classes = List("counter"), contents = js.Array(
      input(
        classes = List("up"),
        props = Map("type" -> "button", "value" -> "+"),
        events = Map("click" -> increment)
      ),
      span(classes = List("val"), contents = count.toString),
      input(
        classes = List("down"),
        props = Map("type" -> "button", "value" -> "-"),
        events = Map("click" -> decrement)
      )
    ))
}
