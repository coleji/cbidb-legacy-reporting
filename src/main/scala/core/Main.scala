package core

import VNode.SnabbdomFacade.{VNode, patch, toVNode}
import _root_.VNode.div
import monix.execution.Scheduler
import org.scalajs.dom.document

import scala.concurrent.ExecutionContext
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope
import monix.execution.Scheduler.Implicits.global


object Main {

  @js.native
  class Target extends js.Object {
    val value: js.Any = js.native
    val selectedOptions: js.Array[Target] = js.native
  }

  @js.native
  @JSGlobalScope
  object Globals extends js.Object {
    var testThing: js.Object = js.native
    var testFn: js.Function0[Any] = js.native
    object window extends js.Object {
      object location extends js.Object {
        def pathname: String = js.native
        def host: String = js.native
        def hostname: String = js.native
        def protocol: String = js.native
      }
    }
    object history extends js.Object {
      def pushState(state: js.Object, title: String, path: String): Unit = js.native
    }
  }

  val dropdownNull: String = "%null%"

  val BASE_LOCATION: String = Globals.window.location.protocol + "//" + Globals.window.location.host
  val API_LOCATION: String = BASE_LOCATION + "/api"

  var rootElement: VNode = toVNode(document.getElementById("root"))
  def updateRootElement(newRoot: VNode): Unit = {
    patch(rootElement, newRoot)
    rootElement = newRoot
  }

  def main(args: Array[String]): Unit = {
    println("starting on path " + Globals.window.location.pathname)
    println("host: " + Globals.window.location.host)
    println("hostname: " + Globals.window.location.hostname)
    println("protocol: " + Globals.window.location.protocol)
    println(API_LOCATION)
    Router.render.set((view: VNode) => updateRootElement(div(id = "root", contents = view)))
    Router.route(Globals.window.location.pathname).map(p => {
      import scala.concurrent.ExecutionContext.Implicits.global
      p.renderPage()
    })
    //UsersPage(render).renderPage()
    //AsyncPage(render).renderPage()
    //StringReversePage(render).renderPage()
    //CounterPage(render).renderPage()
  }
}