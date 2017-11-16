package Pages.ReportPage

import Pages.ReportPage.Model.ReportPageModel
import Pages.ReportPage.View.ReportPageView
import core.SnabbdomFacade.VNode
import core.{Page, Uninitialized}

case class ReportPage(render: (VNode => Unit)) extends Page[ReportPageModel](render) {
  val defaultModel: ReportPageModel = ReportPageModel(Uninitialized, None, None, None)
  val view = new ReportPageView(render)
}