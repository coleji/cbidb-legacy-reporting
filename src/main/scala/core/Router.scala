package core

import CbiUtil.Initializable
import Pages.AsyncPage.AsyncPage
import Pages.CounterPage.CounterPage
import Pages.LoginPage.LoginPage
import Pages.ReportPage.ReportPage
import Pages.StringReversePage.StringReversePage
import Pages.UsersPage.UsersPage
import VNode.SnabbdomFacade.VNode
import core.ApiEndpointFacade.JpTeam
import core.Main.Globals
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.response.SimpleHttpResponse
import monix.execution.Scheduler

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.scalajs.js
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import monix.execution.Scheduler.Implicits.global

object Router {
  val render = new Initializable[VNode => Unit]
  // TODO: make better
  def route(path: String): Future[Page[_]] = {
    import monix.execution.Scheduler.Implicits.global
    val p = Promise[Page[_]]
    val request = HttpRequest(Main.API_LOCATION + "/is-logged-in-as-staff")
    request.send().onComplete(r => {
      import monix.execution.Scheduler.Implicits.global
      r match {
        case res:Success[SimpleHttpResponse] => {
          val isLoggedIn: Boolean = res.get.body != "false"
          val page = {
            if (isLoggedIn) {
              path match {
                case "/counter" => CounterPage(render.get)
                case "/string-reverse" => StringReversePage(render.get)
                case "/async" => AsyncPage(render.get)
                case "/users" => UsersPage(render.get)
                case "/login" => LoginPage(render.get)
                case _ => ReportPage(render.get)
              }
            } else {
              LoginPage(render.get)
            }
          }
          p.success(page)
        }
        case e: Failure[SimpleHttpResponse] => p.success(LoginPage(render.get))
      }

    })
    p.future
  }

  def transition(path: String): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    val emptyObj: js.Object = js.Dynamic.literal()
    Globals.history.pushState(emptyObj, "", path)
    route(path).map(p => p.renderPage())
  }
}
