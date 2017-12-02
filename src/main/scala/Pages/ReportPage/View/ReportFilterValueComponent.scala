package Pages.ReportPage.View

import Pages.ReportPage.Messages.UpdateFilterValue.UpdateFilterValueJSON
import Pages.ReportPage.Model.FilterState.SingleFilter
import Pages.ReportPage.Model.FilterType
import VNode.SnabbdomFacade.VNode
import _root_.VNode.SnabbdomFacade.snabbdom.h
import core.Main.Target
import core.Message.SpecificPageMessage
import org.scalajs

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

case class ReportFilterValueComponent(sf: SingleFilter, updateFilterValue: SpecificPageMessage[js.Object]) {
  //val tableId: String = "filterValue_" + sf.hashCode().toString
  val cellPadding = json("style" -> json("padding" -> "3px 5px"))
  def onProps(eventType: String, valueIndex: Int): js.Object = json(eventType -> ((e: scalajs.dom.TextEvent) => {
    val payload: UpdateFilterValueJSON = json(
      "sfHashCode" -> sf.hashCode().toString,
      "valueIndex" -> valueIndex,
      "newValue" -> e.target.asInstanceOf[Target].value
    ).asInstanceOf[UpdateFilterValueJSON]
    updateFilterValue(payload)
  }))

  private val filterTypes = sf.filter.definition.filterType

  def render: VNode = h("table", h("tr", filterTypes.zip(0 to filterTypes.length).map(t => t._1 match {
    case FilterType.INT_FILTER_TYPE | FilterType.DOUBLE_FILTER_TYPE | FilterType.DATE_FILTER_TYPE => {
      val props = json(
        "props" -> json("type" -> "input", "value" -> sf.filter.value(t._2).toString, "size" -> "10"),
        "on" -> onProps("input", t._2)
      )
      h("td", cellPadding, h("input", props))
    }
    case FilterType.DROPDOWN_FILTER_TYPE => h("td", cellPadding, h("select", json("on" -> onProps("change", t._2)), sf.filter.definition.dropdownValues.head.map(dv => {
      val props = if(dv.`return`.toString == sf.filter.value(t._2).toString) {
        json("props" -> json("selected" -> "selected", "value" -> (dv.`return`: js.Any)))
      } else json("props" -> json("value" -> (dv.`return`: js.Any)))
      h("option", props, dv.display: js.Any)
    })))
    case _ => h("td", cellPadding, h("span", "Unknown filter type!": js.Any))
  })))
}