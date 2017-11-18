package Components

import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import scalajs.js.Dynamic.{literal => json}

object HiddenInput {
  def apply(name: String, value: String): VNode =
    h("input", json("props" -> json("type" -> "hidden", "name" -> name, "value" -> value)))
}
