package Pages.ReportPage.View

import Pages.ReportPage.Model.ReportPageModel
import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import core.Main.Target
import core.Message.SpecificPageMessage
import org.scalajs

import scala.scalajs.js

case class EntityDropdown(
 change: SpecificPageMessage[String]
) {
  def apply(model: ReportPageModel): VNode =
    div(contents = js.Array(
      span(contents = "@One row per...  "),
      select(
        id = "entity",
        events = Map("change" -> ((e: scalajs.dom.TextEvent) => {
          change(e.target.asInstanceOf[Target].value.toString)
        })),
        contents = model.getOptions.map(e => {
          option(
            props = Map(
              "value" -> e.entityName,
              "selected" -> (if (Some(e) == model.selectedEntity) "selected" else "")),
            contents = e.displayName
          )
        })
      )
    ))
}