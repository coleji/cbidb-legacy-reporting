package Pages.ReportPage.View

import Pages.ReportPage.Model.ReportPageModel
import core.Main.{Globals, Target}
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import org.scalajs

import scala.scalajs.js

case class FieldsComponent(
  updateFields: SpecificPageMessage[js.Array[js.Any]]
) {
  def apply(model: ReportPageModel): VNode = {
    println(model.fields)
    val selectProps = js.Dynamic.literal(
      "props" -> js.Dynamic.literal(
        "multiple" -> ("multiple": js.Any),
        "size" -> ("15": js.Any)
      ),
      "on" -> js.Dynamic.literal("change" -> ((e: scalajs.dom.TextEvent) => {
        val selectedOptions: js.Array[js.Any] = e.target.asInstanceOf[Target].selectedOptions.map(_.value)
        updateFields(selectedOptions)
      }))
    )
    h("select#fields_" + model.hashCode().toString, selectProps, model.selectedEntity.get.fieldData.map(rf => {
      val props = {
        if (model.fields.get.contains(rf)) {
          js.Dynamic.literal("props" -> js.Dynamic.literal(
            "selected" -> "selected",
            "value" -> (rf.fieldName: js.Any)
          ))
        } else {
          js.Dynamic.literal("props" -> js.Dynamic.literal(
            "selected" -> "",
            "value" -> (rf.fieldName: js.Any)
          ))
        }
      }
      h("option", props, rf.fieldDisplayName: js.Any)
    }))
  }
}
