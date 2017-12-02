package Pages.ReportPage.View

import Pages.ReportPage.Model.ReportPageModel
import core.Main.Target
import core.Message.SpecificPageMessage
import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import org.scalajs
import VNode.Contents

import scala.scalajs.js

case class EntityDropdown(
 change: SpecificPageMessage[String]
) {
  def apply(model: ReportPageModel): VNode =
    div(contents = Contents(js.Array(
      span(contents = Contents("One row per...  ": js.Any)),
      select(
        id = "entity",
        events = Map("change" -> ((e: scalajs.dom.TextEvent) => {
          change(e.target.asInstanceOf[Target].value.toString)
        })),
        contents = Contents(model.getOptions.map(e => {
          option(
            props = Map(
              "value" -> e.entityName,
              "selected" -> (if (Some(e) == model.selectedEntity) "selected" else "")),
            contents = Contents(e.displayName)
          )
        }))
      )
    )))
}