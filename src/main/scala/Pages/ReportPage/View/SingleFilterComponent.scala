package Pages.ReportPage.View

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Messages.UpdateFilterType.UpdateFilterTypeJSON
import Pages.ReportPage.Model.FilterState.SingleFilter
import Pages.ReportPage.Model.ReportPageModel
import core.Main.Target
import core.Message.SpecificPageMessage
import VNode.SnabbdomFacade.VNode
import VNode.SnabbdomFacade.snabbdom.h
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
    val cellPadding = json("style" -> json("padding" -> "3px 5px"))

    val delete = json(
      "on" -> json("click" -> (() => {
        val payload: js.Object = json(
          "deleteType" -> DeleteFilter.DELETE_TYPE_SINGLE,
          "deleteHash" -> sf.hashCode()
        )
        deleteFilter(payload)
      })),
      "style" -> json("cursor" -> "pointer")
    )

    val typeSelectProps = json("on" -> json("change" -> ((e: scalajs.dom.TextEvent) => {
      val payload: UpdateFilterTypeJSON = json(
        "sfHashCode" -> sf.hashCode().toString,
        "newValue" -> e.target.asInstanceOf[Target].value
      ).asInstanceOf[UpdateFilterTypeJSON]
      updateFilterType(payload)
    })))

    val imgProps = json(
      "props" -> json("src" -> "/images/trashcan.svg"),
      "style" -> json("width" -> "25px")
    )

    h("tr", js.Array(
      h("td", cellPadding,
        h("select", typeSelectProps, model.selectedEntity.get.filterData.map(f => {
          val props = if(sf.filter.definition.filterName == f.filterName) {
            json("props" -> json(
              "selected" -> "selected",
              "value" -> f.filterName
            ))
          } else {
            json("props" -> json(
              "value" -> f.filterName
            ))
          }
          h("option", props, f.displayName: js.Any)
        }))
      ),
      h("td", cellPadding, ReportFilterValueComponent(sf, updateFilterValue).render),
      h("td", cellPadding, h("span", delete, h("img", imgProps)))
    ))
  }
}
