package Pages.ReportPage.View

import Pages.ReportPage.Model.{ReportPageModel, ReportableEntity}
import core.Main.{Target, dropdownNull}
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import org.scalajs

import scala.scalajs.js

case class EntityDropdown(
 change: SpecificPageMessage[String]
) {
  def apply(model: ReportPageModel): VNode = {
    val entities: js.Array[ReportableEntity] = model.getOptions
    val selectedEntity: Option[ReportableEntity] = model.selectedEntity
    val selectSpec = js.Dynamic.literal("on" -> js.Dynamic.literal("change" -> ((e: scalajs.dom.TextEvent) => {
      change(e.target.asInstanceOf[Target].value.toString)
    })))
    val defaultSpec: js.Object = js.Dynamic.literal(
      "props" -> js.Dynamic.literal("value" -> dropdownNull)
    )
    h("select#entity", selectSpec, js.Array({
      h("option", defaultSpec, "- Select -": js.Any)
    }) concat entities.map(e => {
      val spec: js.Object = js.Dynamic.literal(
        "props" -> js.Dynamic.literal(
          "value" -> e.entityName,
          "selected" -> (if (Some(e) == selectedEntity) "selected" else "")
        )
      )
      h("option", spec, e.displayName: js.Any)
    }))
  }
}