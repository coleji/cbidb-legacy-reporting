package Pages.UsersPage

import VNode.SnabbdomFacade.VNode
import core.{Page, Uninitialized}

case class UsersPage(render: (VNode => Unit)) extends Page[UsersPageModel](render) {
  val defaultModel: UsersPageModel = UsersPageModel(Uninitialized)
  val view = new UsersPageView(render)
}