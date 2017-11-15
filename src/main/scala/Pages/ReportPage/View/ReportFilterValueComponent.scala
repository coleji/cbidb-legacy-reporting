package Pages.ReportPage.View

import Pages.ReportPage.Messages.UpdateFilterValue.UpdateFilterValueJSON
import Pages.ReportPage.Model.FilterState.SingleFilter
import Pages.ReportPage.Model.FilterType
import core.Main.Target
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h
import org.scalajs

import scala.scalajs.js

case class ReportFilterValueComponent(sf: SingleFilter, updateFilterValue: SpecificPageMessage[js.Object]) {
  def onProps(eventType: String): js.Object = js.Dynamic.literal(eventType -> ((e: scalajs.dom.TextEvent) => {
    val payload: UpdateFilterValueJSON = js.Dynamic.literal(
      "sfHashCode" -> sf.hashCode().toString,
      "newValue" -> e.target.asInstanceOf[Target].value
    ).asInstanceOf[UpdateFilterValueJSON]
    println(e.target.asInstanceOf[Target].value)
    updateFilterValue(payload)
  }))

  def render: VNode = sf.filter.definition.filterType match {
    case FilterType.INT_FILTER_TYPE => {
      val props = js.Dynamic.literal(
        "props" -> js.Dynamic.literal("type" -> "input", "value" -> sf.filter.value.toString, "size" -> "10"),
        "on" -> onProps("input")
      )
      h("input", props)
    }
    case FilterType.DROPDOWN_FILTER_TYPE => h("select", js.Dynamic.literal("on" -> onProps("change")), sf.filter.definition.dropdownValues.map(dv => {
      val props = if(dv.`return`.toString == sf.filter.value.toString) {
        js.Dynamic.literal("props" -> js.Dynamic.literal("selected" -> "selected", "value" -> (dv.`return`: js.Any)))
      } else js.Dynamic.literal("props" -> js.Dynamic.literal("value" -> (dv.`return`: js.Any)))
      h("option", props, dv.display: js.Any)
    }))
    case _ => h("span", "Unknown filter type!": js.Any)
  }
}