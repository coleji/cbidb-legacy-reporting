package Pages.ReportPage.Messages

import Pages.ReportPage.Model.FilterState.CompositeFilter
import Pages.ReportPage.Model.ReportPageModel
import core.Message

import scala.scalajs.js

object AddSingleFilter extends Message[ReportPageModel, CompositeFilter] {
  def update: ReportPageModel => CompositeFilter => ReportPageModel =
    model => cf => {
      model.cloneAndAddSingleFilter(cf.hashCode())
    }

  @js.native
  trait UpdateFilterTypeJSON extends js.Object{
    val sfHashCode: String = js.native
    val newValue: String = js.native
  }
}