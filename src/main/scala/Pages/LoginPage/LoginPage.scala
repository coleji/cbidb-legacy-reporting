package Pages.LoginPage

import VNode.SnabbdomFacade.VNode
import core.Page

case class LoginPage(render: (VNode => Unit)) extends Page[LoginPageModel](render) {
  val defaultModel: LoginPageModel = LoginPageModel(None, None)
  val view = new LoginPageView(render)
}