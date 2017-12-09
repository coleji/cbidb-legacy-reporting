package Pages.LoginPage

import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import core.Main.Globals
import core.{Main, View}

import scala.scalajs.js

class LoginPageView (render: VNode => Unit) extends View[LoginPageModel](render) {
  def apply(model: LoginPageModel): VNode = {
    form(
      props = Map("method" -> "post", "action" -> (Main.API_LOCATION + "/authenticate")),
      contents = js.Array(
        input(props = Map("type" -> "hidden", "name" -> "redirect-url", "value" -> (
          Globals.window.location.protocol + "//" + Globals.window.location.host + Globals.window.location.pathname
        ))),
        table(tbody(js.Array(
          tr(js.Array(
            td(label(
              props = Map("for" -> "username"),
              contents = "Username"
            )),
            td(input(
              id = "username",
              props = Map("name" -> "username", "type" -> "text", "size" -> "15")
            ))
          )),
          tr(js.Array(
            td(label(
              props = Map("for" -> "password"),
              contents = "Password"
            )),
            td(input(
              id = "password",
              props = Map("name" -> "password", "type" -> "password", "size" -> "15")
            ))
          )),
          tr(td(
            props = Map("colSpan" -> "2"),
            style = Map("textAlign" -> "right"),
            contents = button(
              classes = List("btn", "btn-primary"),
              props = Map("type" -> "submit"),
              contents = "Submit"
            )
          ))
        )))
      )
    )
  }
}
