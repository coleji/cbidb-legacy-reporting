package core

import CbiUtil.Initializable
import Pages.AsyncPage.AsyncPage
import Pages.CounterPage.CounterPage
import Pages.ReportPage.ReportPage
import Pages.StringReversePage.StringReversePage
import Pages.UsersPage.UsersPage
import VNode.SnabbdomFacade.VNode

object Router {
  val render = new Initializable[VNode => Unit]
  // TODO: make better
  def route(path: String): Page[_] = path match {
    case "/counter" => CounterPage(render.get)
    case "/string-reverse" => StringReversePage(render.get)
    case "/async" => AsyncPage(render.get)
    case "/users" => UsersPage(render.get)
    case _ => ReportPage(render.get)
  }
}
