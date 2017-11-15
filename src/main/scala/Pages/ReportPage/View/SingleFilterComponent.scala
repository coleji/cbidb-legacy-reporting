package Pages.ReportPage.View

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Messages.UpdateFilterType.UpdateFilterTypeJSON
import Pages.ReportPage.Model.FilterState.SingleFilter
import Pages.ReportPage.Model.ReportPageModel
import core.Main.{Globals, Target}
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

    val delete = js.Dynamic.literal("on" -> js.Dynamic.literal("click" -> (() => {
      println("deleting!")
      val payload: js.Object = js.Dynamic.literal(
        "deleteType" -> DeleteFilter.DELETE_TYPE_SINGLE,
        "deleteHash" -> sf.hashCode()
      )
      deleteFilter(payload)
    })))


    val typeSelectProps = js.Dynamic.literal("on" -> js.Dynamic.literal("change" -> ((e: scalajs.dom.TextEvent) => {
      Globals.testThing = e
      Globals.testFn()
      println(e.target.asInstanceOf[Target].value)
      val payload: UpdateFilterTypeJSON = js.Dynamic.literal(
        "sfHashCode" -> sf.hashCode().toString,
        "newValue" -> e.target.asInstanceOf[Target].value
      ).asInstanceOf[UpdateFilterTypeJSON]
      updateFilterType(payload)
    })))

    h("tr", js.Array(
      h("td", js.Array(
        h("span", delete, "X": js.Any),
        h("select", typeSelectProps, model.selectedEntity.get.filterData.map(f => {
          val props = if(sf.filter.definition.filterName == f.filterName) {
            js.Dynamic.literal("props" -> js.Dynamic.literal("selected" -> "selected"))
          } else {
            js.Dynamic.literal()
          }
          h("option", props, f.filterName: js.Any)
        })))
      ),
      h("td", ReportFilterValueComponent(sf, updateFilterValue).render)
    ))
  }
}
