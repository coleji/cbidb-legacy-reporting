package Pages.StringReversePage

import Components.StringReverse
import VNode.SnabbdomFacade.VNode
import _root_.VNode.div
import core.Message.SpecificPageMessage
import core.{Message, View}

import scala.scalajs.js

class StringReversePageView(render: VNode => Unit) extends View[StringReversePageModel](render) {
  private val view = this

  object ChangeFirstName extends Message[StringReversePageModel, String] {
    def update: StringReversePageModel => String => StringReversePageModel =
      model => payload => StringReversePageModel(payload, model.lastName)
  }

  object ChangeLastName extends Message[StringReversePageModel, String] {
    def update: StringReversePageModel => String => StringReversePageModel =
      model => payload => StringReversePageModel(model.firstName, payload)
  }

  def apply(model: StringReversePageModel): VNode = {
    val firstName: StringReverse = {
      val change: SpecificPageMessage[String] = ChangeFirstName(view)(model)
      new StringReverse(change)(model.firstName)
    }

    val lastName: StringReverse = {
      val change: SpecificPageMessage[String] = ChangeLastName(view)(model)
      new StringReverse(change)(model.lastName)
    }

    div(id = "page", contents = js.Array(
      firstName.vnode,
      lastName.vnode
    ))
  }
}
