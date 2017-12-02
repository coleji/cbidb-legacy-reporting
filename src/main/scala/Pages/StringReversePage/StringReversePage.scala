package Pages.StringReversePage

import core.Page
import VNode.SnabbdomFacade.VNode

case class StringReversePage(render: (VNode => Unit)) extends Page[StringReversePageModel](render) {
  val defaultModel: StringReversePageModel = StringReversePageModel("", "")
  val view = new StringReversePageView(render)
}
