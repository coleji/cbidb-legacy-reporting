package Pages.ReportPage.View

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Messages.UpdateFilterType.UpdateFilterTypeJSON
import Pages.ReportPage.Model.FilterState.SingleFilter
import Pages.ReportPage.Model.ReportPageModel
import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import core.Main.Target
import core.Message.SpecificPageMessage
import org.scalajs

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

case class SingleFilterComponent(
  searchModelForHashCode: SpecificPageMessage[Int],
  updateFilterValue: SpecificPageMessage[js.Object],
  updateFilterType: SpecificPageMessage[js.Object],
  deleteFilter: SpecificPageMessage[js.Object]
) {
  def apply(model: ReportPageModel)(sf: SingleFilter): VNode = {
    val cellPadding = Map("padding" -> "3px 5px")

    tr(js.Array(
      td(
        style = cellPadding,
        contents = select(
          events = Map("change" -> ((e: scalajs.dom.TextEvent) => {
            val payload: UpdateFilterTypeJSON = json(
              "sfHashCode" -> sf.hashCode().toString,
              "newValue" -> e.target.asInstanceOf[Target].value
            ).asInstanceOf[UpdateFilterTypeJSON]
            updateFilterType(payload)
          })),
          contents = model.selectedEntity.get.filterData.map(f => {
            option(
              props = {
                Map("value" -> f.filterName) ++ {
                  if (sf.filter.definition.filterName == f.filterName) Map("selected" -> "selected")
                  else Map.empty
                }
              },
              contents = f.displayName
            )
          })
        )
      ),
      td(style = cellPadding, contents = ReportFilterValueComponent(sf, updateFilterValue).render),
      td(style = cellPadding, contents = span(
        style = Map("cursor" -> "pointer"),
        events = Map("click" -> (() => {
          val payload: js.Object = json(
            "deleteType" -> DeleteFilter.DELETE_TYPE_SINGLE,
            "deleteHash" -> sf.hashCode()
          )
          deleteFilter(payload)
        })),
        contents = img(
          props = Map("src" -> "/images/trashcan.svg"),
          style = Map("width" -> "25px")
        )
      ))
    ))
  }
}
