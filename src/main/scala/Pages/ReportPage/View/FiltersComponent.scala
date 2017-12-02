package Pages.ReportPage.View

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Model.FilterState.{CompositeFilter, SingleFilter}
import Pages.ReportPage.Model.ReportPageModel
import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import core.Message.SpecificPageMessage

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}
import scala.scalajs.js.JSConverters._

case class FiltersComponent (
  searchModelForHashCode: SpecificPageMessage[Int],
  updateFilterValue: SpecificPageMessage[js.Object],
  updateFilterType: SpecificPageMessage[js.Object],
  addSingleFilter: SpecificPageMessage[CompositeFilter],
  deleteFilter: SpecificPageMessage[js.Object],
  addNestedCompositeFilter: SpecificPageMessage[Int]
) {
  val SPACING_PER_LEVEL: Int = 40

  def apply(model: ReportPageModel, level: Int, cf: CompositeFilter): VNode = {
    val filterNodes: js.Array[VNode] = {
      val filterTRs: List[VNode] = cf.filters.map({
        case sf: SingleFilter => SingleFilterComponent(searchModelForHashCode, updateFilterValue, updateFilterType, deleteFilter)(model)(sf)
        case cfInner: CompositeFilter =>
          tr(td(
            classes = List("filter"),
            props = Map("colSpan" -> "3"),
            contents = FiltersComponent(
              searchModelForHashCode,
              updateFilterValue,
              updateFilterType,
              addSingleFilter,
              deleteFilter,
              addNestedCompositeFilter
            )(model, level + 1, cfInner)
          ))
      }).flatMap(tr1 => List(
        tr(td(cf.comparator.toString)),
        tr1
      ))

      val buttons = js.Array(
        button(
          classes = List("btn", "btn-default", "btn-xs"),
          props = Map("type" -> "button"),
          events = Map("click" -> (() => addSingleFilter(cf))),
          contents = cf.comparator.toString
        ),
        span("   "),
        button(
          classes = List("btn", "btn-default", "btn-xs"),
          events = Map("click" -> (() => {
            addNestedCompositeFilter(cf.hashCode())
          })),
          contents = "("
        )
      )

      filterTRs match {
        case Nil => buttons
        case x :: xs => xs.toJSArray ++ buttons
      }
    }

    table(tbody(tr(js.Array(
      td(
        style = { if (level > 0) Map("border-right" -> "1px solid #aaaaaa") else Map.empty },
        contents = div(
          classes = List("spacer"),
          style = Map(
            "width" -> ((SPACING_PER_LEVEL * level) + "px"),
            "textAlign" -> "right",
            "paddingRight" -> "5px"
          ),
          contents = {
            if (level > 0) {
              button(
                classes = List("btn", "btn-primary", "btn-xs"),
                contents = "X",
                props = Map("type" -> "button"),
                events = Map("click" -> (() => {
                  deleteFilter(json(
                    "deleteType" -> DeleteFilter.DELETE_TYPE_COMPOSITE,
                    "deleteHash" -> cf.hashCode()
                  ))
                }))
              )
            } else null
          }
        )
      ),
      td(
        style = Map("paddingLeft" -> "5px"),
        contents = table(tbody(filterNodes))
      )
    ))))
  }
}
