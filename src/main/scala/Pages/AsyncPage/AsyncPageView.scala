package Pages.AsyncPage

import core.ApiEndpointFacade.JpTeam
import VNode.SnabbdomFacade.VNode
import VNode.SnabbdomFacade.snabbdom.h
import core._
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.response.SimpleHttpResponse
import monix.execution.Scheduler.Implicits.global

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}

class AsyncPageView(render: VNode => Unit) extends View[AsyncPageModel](render) {
  private val view = this

  object MarkInitialized extends NoArgMessage[AsyncPageModel] {
    def update: AsyncPageModel => AsyncPageModel =
      _ => AsyncPageModel(Waiting)
  }

  object AsyncSuccessMessage extends Message[AsyncPageModel, js.Any] {
    override def update: AsyncPageModel => js.Any => AsyncPageModel =
      _ => teams => AsyncPageModel(AsyncSuccess(teams.asInstanceOf[js.Array[JpTeam]]))
  }

  def apply(model: AsyncPageModel): VNode = {
    model.result match {
      case Uninitialized => {
        val request = HttpRequest("http://localhost:9000/jp-teams")
        request.send().onComplete({
          case res:Success[SimpleHttpResponse] => {
            val teamList: js.Array[JpTeam] = {
              val rawResult: String = res.get.body
              val rawJSON: js.Object = JSON.parse(rawResult).asInstanceOf[js.Object]
              val asApiResult = rawJSON.asInstanceOf[ApiResult]
              val ret: js.Array[JpTeam] = JpTeam.parse(asApiResult)
              ret
            }
            AsyncSuccessMessage(view)(model)(teamList)
          }
          case e: Failure[SimpleHttpResponse] => println("Async call failed")
        })
        MarkInitialized(view)(model)()
        h("div#page", "uninitialized!": js.Any)
      }
      case Waiting => {
        h("div#page", js.Array(
          h("span#yo", "waiting!": js.Any)
        ))
      }
      case AsyncSuccess(teams: js.Array[JpTeam]) => {
        h("div#page",
          h("ul", teams.map(t => h("li", t.teamName + ": " + t.points: js.Any)))
        )
      }
    }
  }
}
