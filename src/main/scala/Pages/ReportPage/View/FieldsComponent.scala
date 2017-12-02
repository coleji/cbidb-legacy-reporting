package Pages.ReportPage.View

import Pages.ReportPage.Model.ReportPageModel
import VNode.SnabbdomFacade.VNode
import _root_.VNode.{option, select}
import core.Main.{Globals, Target}
import core.Message.SpecificPageMessage
import org.scalajs

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

case class FieldsComponent(
  updateFields: SpecificPageMessage[js.Array[js.Any]]
) {
  def apply(model: ReportPageModel): VNode = {
    select(
      id = "selectFields",
      props = Map(
        "multiple" -> "multiple",
        "size" -> "15"
      ),
      events = Map("change" -> ((e: scalajs.dom.TextEvent) => {
        val selectedOptions: js.Array[js.Any] = e.target.asInstanceOf[Target].selectedOptions.map(_.value)
        Globals.testThing = selectedOptions
        Globals.testFn()
        updateFields(selectedOptions)
      })),
      contents = model.selectedEntity.get.fieldData.map(rf => {
        option(
          props = Map(
            "value" -> rf.fieldName,
            "selected" -> { if (model.fields.get.contains(rf)) "selected" else "" }
          ),
          contents = rf.fieldDisplayName
        )
      })
    )
  }
}
