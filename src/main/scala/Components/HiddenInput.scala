package Components

import VNode.SnabbdomFacade.VNode
import VNode.SnabbdomFacade.snabbdom.h
import scalajs.js.Dynamic.{literal => json}

object HiddenInput {
  def apply(name: String, value: String): VNode =
    h("input", json("props" -> json("type" -> "hidden", "name" -> name, "value" -> value)))
}
