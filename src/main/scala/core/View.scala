package core

import core.SnabbdomFacade.VNode

abstract class View[T <: Model](val render: VNode => Unit) {
  def apply(m: T): VNode
}
