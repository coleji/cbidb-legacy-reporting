package core

import Pages.ReportPage.ReportPage
import VNode.SnabbdomFacade.{VNode, patch, toVNode}
import _root_.VNode.div
import org.scalajs.dom.document

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope

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
      }
    }
    object history extends js.Object {
      def pushState(state: js.Object, title: String, path: String): Unit = js.native
    }
  }

  val dropdownNull: String = "%null%"

  var rootElement: VNode = toVNode(document.getElementById("root"))
  def updateRootElement(newRoot: VNode): Unit = {
    patch(rootElement, newRoot)
    rootElement = newRoot
  }

  def main(args: Array[String]): Unit = {
    println("starting on path " + Globals.window.location.pathname)
    Router.render.set((view: VNode) => updateRootElement(div(id = "root", contents = view)))
    Router.route(Globals.window.location.pathname).renderPage()
    //UsersPage(render).renderPage()
    //AsyncPage(render).renderPage()
    //StringReversePage(render).renderPage()
    //CounterPage(render).renderPage()
  }
}