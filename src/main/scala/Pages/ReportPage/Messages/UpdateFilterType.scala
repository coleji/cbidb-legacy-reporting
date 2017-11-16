package Pages.ReportPage.Messages

import Pages.ReportPage.Model.ReportPageModel
import core.Message

import scala.scalajs.js

object UpdateFilterType extends Message[ReportPageModel,js.Object] {
  def update: ReportPageModel => js.Object => ReportPageModel =
    model => updateFilterTypeJSON => {
      val updateFilterValueJSONCast = updateFilterTypeJSON.asInstanceOf[UpdateFilterTypeJSON]
      model.cloneAndUpdateType(updateFilterValueJSONCast.sfHashCode, updateFilterValueJSONCast.newValue)
    }

  @js.native
  trait UpdateFilterTypeJSON extends js.Object{
    val sfHashCode: String = js.native
    val newValue: String = js.native
  }
}