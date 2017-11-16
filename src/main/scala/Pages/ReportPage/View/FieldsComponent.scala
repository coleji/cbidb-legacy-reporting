package Pages.ReportPage.View

import Pages.ReportPage.Model.ReportPageModel
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h

import scala.scalajs.js

object FieldsComponent {
  def apply(model: ReportPageModel): VNode = {
    val selectProps = js.Dynamic.literal("props" -> js.Dynamic.literal(
      "multiple" -> ("multiple": js.Any),
      "size" -> ("15": js.Any)
    ))
    h("select", selectProps, model.selectedEntity.get.fieldData.map(rf => {
      h("option", rf.fieldName: js.Any)
    }))
  }
}
