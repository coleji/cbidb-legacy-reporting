package Pages.ReportPage.Messages

import Pages.ReportPage.Model.ReportPageModel
import core.Message

import scala.scalajs.js

object UpdateFilterValue extends Message[ReportPageModel, js.Object] {
  def update: ReportPageModel => js.Object => ReportPageModel =
    model => updateFilterValueJSON => {
      val updateFilterValueJSONCast = updateFilterValueJSON.asInstanceOf[UpdateFilterValueJSON]
      model.cloneAndUpdateValue(updateFilterValueJSONCast.sfHashCode, updateFilterValueJSONCast.newValue)
    }

  @js.native
  trait UpdateFilterValueJSON extends js.Object{
    val sfHashCode: String = js.native
    val newValue: String = js.native
  }
}