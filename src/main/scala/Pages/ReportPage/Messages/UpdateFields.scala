package Pages.ReportPage.Messages

import Pages.ReportPage.Model.ReportPageModel
import core.Message

import scala.scalajs.js

object UpdateFields extends Message[ReportPageModel, js.Array[js.Any]] {
  def update: ReportPageModel => js.Array[js.Any] => ReportPageModel =
    model => selectedFilters => {
      model.cloneAndSetFilters(selectedFilters)
    }

  @js.native
  trait UpdateFilterTypeJSON extends js.Object{
    val sfHashCode: String = js.native
    val newValue: String = js.native
  }
}
