package Pages.CounterPage

import VNode.SnabbdomFacade.VNode
import core.Page

case class CounterPage(render: (VNode => Unit)) extends Page[CounterPageModel](render) {
  val defaultModel: CounterPageModel = CounterPageModel(0, 0)
  val view = new CounterPageView(render)
}
