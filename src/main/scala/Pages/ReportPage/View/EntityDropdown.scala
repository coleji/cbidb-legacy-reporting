package Pages.ReportPage.View

import Pages.ReportPage.Model.{ReportPageModel, ReportableEntity}
import core.Main.{Target, dropdownNull}
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import org.scalajs

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

case class EntityDropdown(
 change: SpecificPageMessage[String]
) {
  def apply(model: ReportPageModel): VNode = {
    val entities: js.Array[ReportableEntity] = model.getOptions
    val selectedEntity: Option[ReportableEntity] = model.selectedEntity
    val selectSpec = json("on" -> json("change" -> ((e: scalajs.dom.TextEvent) => {
      change(e.target.asInstanceOf[Target].value.toString)
    })))
    val defaultSpec: js.Object = json(
      "props" -> json("value" -> dropdownNull)
    )
    h("div", js.Array(
      h("span", "One row per...  ": js.Any),
      h("select#entity", selectSpec, entities.map(e => {
        val spec: js.Object = json(
          "props" -> json(
            "value" -> e.entityName,
            "selected" -> (if (Some(e) == selectedEntity) "selected" else "")
          )
        )
        h("option", spec, e.displayName: js.Any)
      }))
    ))

  }
}