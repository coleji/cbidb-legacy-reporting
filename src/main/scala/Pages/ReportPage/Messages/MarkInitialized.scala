package Pages.ReportPage.Messages

import Pages.ReportPage.Model.ReportPageModel
import core.{NoArgMessage, Waiting}

object MarkInitialized extends NoArgMessage[ReportPageModel] {
  def update: ReportPageModel => ReportPageModel =
    _ => ReportPageModel(Waiting, None, None, None)
}
