package core

import CbiUtil.Initializable
import Pages.AsyncPage.AsyncPage
import Pages.CounterPage.CounterPage
import Pages.LoginPage.LoginPage
import Pages.ReportPage.ReportPage
import Pages.StringReversePage.StringReversePage
import Pages.UsersPage.UsersPage
import VNode.SnabbdomFacade.VNode
import core.Main.Globals

import scala.scalajs.js

object Router {
  val render = new Initializable[VNode => Unit]
  // TODO: make better
  def route(path: String): Page[_] = path match {
    case "/counter" => CounterPage(render.get)
    case "/string-reverse" => StringReversePage(render.get)
    case "/async" => AsyncPage(render.get)
    case "/users" => UsersPage(render.get)
    case "/login" => LoginPage(render.get)
    case _ => ReportPage(render.get)
  }

  def transition(path: String): Unit = {
    val emptyObj: js.Object = js.Dynamic.literal()
    Globals.history.pushState(emptyObj, "", path)
    route(path).renderPage()
  }
}
