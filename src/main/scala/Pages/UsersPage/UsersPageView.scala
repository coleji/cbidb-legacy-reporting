package Pages.UsersPage

import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import core.ApiEndpointFacade.User
import core._
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.response.SimpleHttpResponse
import monix.execution.Scheduler.Implicits.global

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}

class UsersPageView (render: VNode => Unit) extends View[UsersPageModel](render) {
  private val view = this
  type PageMessage = Message[UsersPageModel, js.Any]

  object MarkInitialized extends PageMessage {
    def update: UsersPageModel => js.Any => UsersPageModel =
      _ => _ => UsersPageModel(Waiting)
  }

  object SuccessMessage extends PageMessage {
    def update: UsersPageModel => js.Any => UsersPageModel =
      _ => users => UsersPageModel(AsyncSuccess(users.asInstanceOf[js.Array[User]]))
  }
  def apply(model: UsersPageModel): VNode = {
    model.result match {
      case Uninitialized => {
        val request = HttpRequest(Main.API_LOCATION + "/users")
        request.send().onComplete({
          case res:Success[SimpleHttpResponse] => SuccessMessage(view)(model)({
            val rawResult: String = res.get.body
            val rawJSON: js.Object = JSON.parse(rawResult).asInstanceOf[js.Object]
            val asApiResult = rawJSON.asInstanceOf[ApiResult]
            User.parse(asApiResult): js.Array[User]
          })
          case e: Failure[SimpleHttpResponse] => println("Async call failed")
        })
        MarkInitialized(view)(model)
        div(id = "page", contents = "uninitialized!")
      }
      case Waiting => {
        div(id = "page", contents = "waiting!")
      }
      case AsyncSuccess(users: js.Array[User]) => {
        div(id = "page", contents =
          div(id = "t1", classes = List("container"), contents = div(div(table(tbody(tr(td(
            table(
              id = "apInstances",
              classes = List("table", "table-striped"),
              contents = js.Array(
                thead(tr(
                  User.fields.map(f => th(f._2)).toJSArray
                )),
                tbody(
                  users.map(u => tr(js.Array(
                    td(u.userId),
                    td(u.userName),
                    td(u.firstName),
                    td(u.lastName),
                    td(u.email),
                    td(u.active.toString),
                    td(u.hideFromClose.toString)
                  )))
                )
              )
            )
          ))))))
        ))
      }
    }
  }
}
