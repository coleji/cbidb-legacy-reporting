package Pages.StringReversePage

import core.Page
import core.SnabbdomFacade.VNode

case class StringReversePage(render: (VNode => Unit)) extends Page[StringReversePageModel](render) {
  val defaultModel: StringReversePageModel = StringReversePageModel("", "")
  val view = new StringReversePageView(render)
}
