package Pages.CounterPage

import core.Page
import core.SnabbdomFacade.VNode

case class CounterPage(render: (VNode => Unit)) extends Page[CounterPageModel](render) {
  val defaultModel: CounterPageModel = CounterPageModel(0, 0)
  val view = new CounterPageView(render)
}
