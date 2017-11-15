package core

import core.SnabbdomFacade.VNode

abstract class Page[T <: Model](render: (VNode => Unit)) {
  val defaultModel: T
  val view: View[T]
  def renderPage(): Unit = {
    render(view(defaultModel))
  }
}
