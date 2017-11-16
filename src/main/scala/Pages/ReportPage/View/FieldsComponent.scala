package Pages.ReportPage.View

import Pages.ReportPage.Model.ReportPageModel
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h

import scala.scalajs.js

object FieldsComponent {
  def apply(model: ReportPageModel): VNode = {
    println(model.fields)
    val selectProps = js.Dynamic.literal("props" -> js.Dynamic.literal(
      "multiple" -> ("multiple": js.Any),
      "size" -> ("15": js.Any)
    ))
    h("select#fields_" + model.hashCode().toString, selectProps, model.selectedEntity.get.fieldData.map(rf => {
      val props = {
        if (model.fields.get.contains(rf)) {
          println("found one! " + rf.fieldName)
          js.Dynamic.literal("props" -> js.Dynamic.literal(
            "selected" -> "selected",
            "value" -> (rf.fieldName: js.Any)
          ))
        } else {
          println("nope " + rf.fieldName)
          js.Dynamic.literal("props" -> js.Dynamic.literal(
            "selected" -> "",
            "value" -> (rf.fieldName: js.Any)
          ))
        }
      }
      h("option", props, rf.fieldName: js.Any)
    }))
  }
}
