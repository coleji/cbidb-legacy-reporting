package Components

import core.Component
import core.Message.SpecificPageNoArgMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

class Counter
  (increment: SpecificPageNoArgMessage, decrement: SpecificPageNoArgMessage)
  (count: Int)
extends Component {
  lazy val vnode: VNode = {
    val upSpec: js.Object = json("props" -> json(
      "type" -> "button", "value" -> "+"
    ), "on" -> json("click" -> increment))

    val downSpec: js.Object = json("props" -> json(
      "type" -> "button", "value" -> "-"
    ), "on" -> json("click" -> decrement))

    h("div.counter", js.Array(
      h("input.up", upSpec: js.Any),
      h("span.val", count.asInstanceOf[js.Any]),
      h("input.down", downSpec: js.Any)
    ))
  }
}
