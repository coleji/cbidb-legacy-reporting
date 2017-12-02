package Pages.AsyncPage

import VNode.SnabbdomFacade.VNode
import core.{Page, Uninitialized}

case class AsyncPage(render: (VNode => Unit)) extends Page[AsyncPageModel](render) {
  val defaultModel: AsyncPageModel = AsyncPageModel(Uninitialized)
  val view = new AsyncPageView(render)
}