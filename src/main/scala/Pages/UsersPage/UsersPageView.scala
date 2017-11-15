package Pages.UsersPage

import core.ApiEndpointFacade.User
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
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
    println("rendering!")
    model.result match {
      case Uninitialized => {
        println("uninitialized!")
        val request = HttpRequest("http://localhost:9000/users")
        request.send().onComplete({
          case res:Success[SimpleHttpResponse] => SuccessMessage(view)(model)({
            val rawResult: String = res.get.body
            val rawJSON: js.Object = JSON.parse(rawResult).asInstanceOf[js.Object]
            val asApiResult = rawJSON.asInstanceOf[ApiResult]
            User.parse(asApiResult): js.Array[User]
          })
          case e: Failure[SimpleHttpResponse] => println("Huston, we got a problem!")
        })
        MarkInitialized(view)(model)
        h("div#page", "uninitialized!": js.Any)
      }
      case Waiting => {
        println("waiting!")
        h("div#page", js.Array(
          h("span#yo", "waiting!": js.Any)
        ))
      }
      case AsyncSuccess(users: js.Array[User]) => {
        println("woo success!")

        println(users)
        //println(model.get)
        h("div#page",
          h("div#t1.container", h("div", h("div", h("table", h("tbody", h("tr", h("td",
            h("table#apInstances.table.table-striped", js.Array(
              h("thead", h("tr",
                User.fields.map(f => h("th", f._2: js.Any)).toJSArray
              )),
              h("tbody",
                users.map(u => h("tr", js.Array(
                  h("td", u.userId: js.Any),
                  h("td", u.userName: js.Any),
                  h("td", u.firstName: js.Any),
                  h("td", u.lastName: js.Any),
                  h("td", u.email: js.Any),
                  h("td", u.active.toString: js.Any),
                  h("td", u.hideFromClose.toString: js.Any)
                )))
              )
            ))
          ))))))
        ))
      }
    }
  }
}
