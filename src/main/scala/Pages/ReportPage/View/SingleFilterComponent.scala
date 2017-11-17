package Pages.ReportPage.View

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Messages.UpdateFilterType.UpdateFilterTypeJSON
import Pages.ReportPage.Model.FilterState.SingleFilter
import Pages.ReportPage.Model.ReportPageModel
import core.Main.Target
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import org.scalajs

import scala.scalajs.js

case class SingleFilterComponent(
  searchModelForHashCode: SpecificPageMessage[Int],
  updateFilterValue: SpecificPageMessage[js.Object],
  updateFilterType: SpecificPageMessage[js.Object],
  deleteFilter: SpecificPageMessage[js.Object]
) {
  def apply(model: ReportPageModel)(sf: SingleFilter): VNode = {
    val props = js.Dynamic.literal(
      "props" -> js.Dynamic.literal("id" -> ("sf_" + sf.hashCode().toString)),
      "on" -> js.Dynamic.literal("click" -> (() => {searchModelForHashCode(sf.hashCode())}))
    )

    val cellPadding = js.Dynamic.literal("style" -> js.Dynamic.literal("padding" -> "3px 5px"))

    val delete = js.Dynamic.literal(
      "on" -> js.Dynamic.literal("click" -> (() => {
        val payload: js.Object = js.Dynamic.literal(
          "deleteType" -> DeleteFilter.DELETE_TYPE_SINGLE,
          "deleteHash" -> sf.hashCode()
        )
        deleteFilter(payload)
      })),
      "style" -> js.Dynamic.literal("cursor" -> "pointer")
    )

    val typeSelectProps = js.Dynamic.literal("on" -> js.Dynamic.literal("change" -> ((e: scalajs.dom.TextEvent) => {
      val payload: UpdateFilterTypeJSON = js.Dynamic.literal(
        "sfHashCode" -> sf.hashCode().toString,
        "newValue" -> e.target.asInstanceOf[Target].value
      ).asInstanceOf[UpdateFilterTypeJSON]
      updateFilterType(payload)
    })))

    val imgProps = js.Dynamic.literal(
      "props" -> js.Dynamic.literal("src" -> "/images/trashcan.svg"),
      "style" -> js.Dynamic.literal("width" -> "25px")
    )

    h("tr", js.Array(
      h("td", cellPadding, h("span", delete, h("img", imgProps))),
      h("td", cellPadding,
        h("select", typeSelectProps, model.selectedEntity.get.filterData.map(f => {
          val props = if(sf.filter.definition.filterName == f.filterName) {
            js.Dynamic.literal("props" -> js.Dynamic.literal(
              "selected" -> "selected",
              "value" -> f.filterName
            ))
          } else {
            js.Dynamic.literal("props" -> js.Dynamic.literal(
              "value" -> f.filterName
            ))
          }
          h("option", props, f.displayName: js.Any)
        }))
      ),
      h("td", cellPadding, ReportFilterValueComponent(sf, updateFilterValue).render)
    ))
  }
}
