package Components

import VNode.SnabbdomFacade.VNode
import _root_.VNode.input


object HiddenInput {
  def apply(name: String, value: String): VNode =
    input(
      props = Map("type" -> "hidden", "name" -> name, "value" -> value)
    )
}
