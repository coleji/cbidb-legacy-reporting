package Pages.ReportPage.View

import Pages.ReportPage.Messages.UpdateFilterValue.UpdateFilterValueJSON
import Pages.ReportPage.Model.FilterState.SingleFilter
import Pages.ReportPage.Model.FilterType
import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import core.Main.Target
import core.Message.SpecificPageMessage
import org.scalajs
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

case class ReportFilterValueComponent(sf: SingleFilter, updateFilterValue: SpecificPageMessage[js.Object]) {
  val cellPadding = Map("padding" -> "3px 5px")
  def onProps(eventType: String, valueIndex: Int): Map[String, js.Any] = {
    val callback: js.Function1[dom.TextEvent, Unit] = (e: scalajs.dom.TextEvent) => {
      val payload: UpdateFilterValueJSON = json(
        "sfHashCode" -> sf.hashCode().toString,
        "valueIndex" -> valueIndex,
        "newValue" -> e.target.asInstanceOf[Target].value
      ).asInstanceOf[UpdateFilterValueJSON]
      updateFilterValue(payload)
    }
    Map(eventType -> callback)
  }

  private val filterTypes = sf.filter.definition.filterType

  def render: VNode = table(tr(filterTypes.zip(0 to filterTypes.length).map(t => t._1 match {
    case FilterType.INT_FILTER_TYPE | FilterType.DOUBLE_FILTER_TYPE | FilterType.DATE_FILTER_TYPE => td(
      style = cellPadding,
      contents = input(
        props = Map("type" -> "input", "value" -> sf.filter.value(t._2).toString, "size" -> "10"),
        events = onProps("input", t._2)
      )
    )

    case FilterType.DROPDOWN_FILTER_TYPE => td(
      style = cellPadding,
      contents = select(
        events = onProps("change", t._2),
        contents = sf.filter.definition.dropdownValues.head.map(dv => {
          val props = Map("value" -> dv.`return`) ++ {
            if(dv.`return`.toString == sf.filter.value(t._2).toString) Map("selected" -> "selected")
            else Map.empty
          }
          option(props = props, contents = dv.display)
        })
      )
    )
    case _ => td(style = cellPadding, contents = span("Unknown filter type!"))
  })))
}