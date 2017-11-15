package Pages.ReportPage.Messages

import Pages.ReportPage.Model.ReportPageModel
import core.Message

import scala.scalajs.js

object AddNestedCompositeFilter extends Message[ReportPageModel, Int] {
  def update: ReportPageModel => Int => ReportPageModel =
    model => hashCode => {
      model.cloneAndAddCompositeFilter(hashCode)
    }

  @js.native
  trait UpdateFilterTypeJSON extends js.Object{
    val sfHashCode: String = js.native
    val newValue: String = js.native
  }
}