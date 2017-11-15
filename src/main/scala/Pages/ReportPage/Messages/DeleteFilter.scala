package Pages.ReportPage.Messages

import Pages.ReportPage.Model.ReportPageModel
import core.Message

import scala.scalajs.js

object DeleteFilter extends Message[ReportPageModel, js.Object] {
  def update: ReportPageModel => js.Object => ReportPageModel =
    model => payload => {
      model.cloneAndDropExpression(payload.asInstanceOf[DeleteFilterPayload])
    }

  val DELETE_TYPE_SINGLE = "Single"
  val DELETE_TYPE_COMPOSITE = "Composite"


  @js.native
  trait DeleteFilterPayload extends js.Object{
    val deleteType: String = js.native
    val deleteHash: Int = js.native
  }
}