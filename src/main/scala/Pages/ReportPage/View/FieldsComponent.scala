package Pages.ReportPage.View

import Pages.ReportPage.Model.ReportPageModel
import core.Main.{Globals, Target}
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import org.scalajs

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

case class FieldsComponent(
  updateFields: SpecificPageMessage[js.Array[js.Any]]
) {
  def apply(model: ReportPageModel): VNode = {
    val selectProps = json(
      "props" -> json(
        "multiple" -> ("multiple": js.Any),
        "size" -> ("15": js.Any)
      ),
      "on" -> json("change" -> ((e: scalajs.dom.TextEvent) => {
        val selectedOptions: js.Array[js.Any] = e.target.asInstanceOf[Target].selectedOptions.map(_.value)
        updateFields(selectedOptions)
      }))
    )
    h("select", selectProps, model.selectedEntity.get.fieldData.map(rf => {
      val props = {
        if (model.fields.get.contains(rf)) {
          json("props" -> json(
            "selected" -> "selected",
            "value" -> (rf.fieldName: js.Any)
          ))
        } else {
          json("props" -> json(
            "selected" -> "",
            "value" -> (rf.fieldName: js.Any)
          ))
        }
      }
      h("option", props, rf.fieldDisplayName: js.Any)
    }))
  }
}
