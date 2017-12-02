package Pages.StringReversePage

import VNode.SnabbdomFacade.VNode
import core.Page

case class StringReversePage(render: (VNode => Unit)) extends Page[StringReversePageModel](render) {
  val defaultModel: StringReversePageModel = StringReversePageModel("", "")
  val view = new StringReversePageView(render)
}
