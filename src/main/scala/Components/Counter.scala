package Components

import core.Component
import core.Message.SpecificPageNoArgMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h

import scala.scalajs.js

class Counter
  (increment: SpecificPageNoArgMessage, decrement: SpecificPageNoArgMessage)
  (count: Int)
extends Component {
  lazy val vnode: VNode = {
    val upSpec: js.Object = js.Dynamic.literal("props" -> scalajs.js.Dynamic.literal(
      "type" -> "button", "value" -> "+"
    ), "on" -> scalajs.js.Dynamic.literal("click" -> increment))

    val downSpec: js.Object = js.Dynamic.literal("props" -> js.Dynamic.literal(
      "type" -> "button", "value" -> "-"
    ), "on" -> js.Dynamic.literal("click" -> decrement))

    h("div.counter", js.Array(
      h("input.up", upSpec: js.Any),
      h("span.val", count.asInstanceOf[js.Any]),
      h("input.down", downSpec: js.Any)
    ))
  }
}
