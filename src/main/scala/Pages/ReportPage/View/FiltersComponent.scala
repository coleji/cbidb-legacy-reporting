package Pages.ReportPage.View

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Model.FilterState.{CompositeFilter, SingleFilter}
import Pages.ReportPage.Model.ReportPageModel
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h

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
  val colspan2: js.Object = json("props" -> json("colSpan" -> "3"))

  def apply(model: ReportPageModel, level: Int, cf: CompositeFilter): VNode = {
    val addSingleFilterProps = json(
      "on" -> json("click" -> (() => addSingleFilter(cf))),
      "props" -> json("type" -> "button")
    )
    val addNestedCompositeFilterProps = json("on" -> json("click" -> (() => {
      addNestedCompositeFilter(cf.hashCode())
    })))
    val spacerProps: js.Object = json("style" -> json(
      "width" -> ((SPACING_PER_LEVEL * level) + "px"),
      "textAlign" -> "right",
      "paddingRight" -> "5px"
    ))

    val filterNodes: js.Array[VNode] = {
      val filterTRs: List[VNode] = cf.filters.map({
        case sf: SingleFilter => SingleFilterComponent(searchModelForHashCode, updateFilterValue, updateFilterType, deleteFilter)(model)(sf)
        case cfInner: CompositeFilter =>
          h("tr", h("td.filter", colspan2, FiltersComponent(
            searchModelForHashCode,
            updateFilterValue,
            updateFilterType,
            addSingleFilter,
            deleteFilter,
            addNestedCompositeFilter
          )(model, level + 1, cfInner)))
      }).flatMap(tr => List(
        h("tr", h("td", cf.comparator.toString: js.Any)),
        tr
      ))

      val buttons = js.Array(
        h("button.btn.btn-default.btn-xs", addSingleFilterProps, cf.comparator.toString: js.Any),
        h("span", "   ": js.Any),
        h("button.btn.btn-default.btn-xs", addNestedCompositeFilterProps, "(": js.Any)
      )

      filterTRs match {
        case Nil => buttons
        case x :: xs => xs.toJSArray ++ buttons
      }
    }

    val spacerChildren: js.Array[VNode] = {
      if (level > 0) {
        val payload: js.Object = json(
          "deleteType" -> DeleteFilter.DELETE_TYPE_COMPOSITE,
          "deleteHash" -> cf.hashCode()
        )
        val props = json(
          "on" -> json("click" -> (() => {
            deleteFilter(payload)
          })),
          "props" -> json("type" -> "button")
        )
        js.Array(h("button.btn.btn-primary.btn-xs", props, "X": js.Any))
      } else js.Array()
    }

    val borderRight = json("style" -> json("border-right" -> "1px solid #aaaaaa"))
    val paddingLeft = json("style" -> json("paddingLeft" -> "5px"))

    h("table", h("tbody", h("tr", js.Array(
      h("td", {if (level > 0) borderRight else json()}, h("div.spacer", spacerProps, spacerChildren)),
      h("td", paddingLeft, h("table", h("tbody", filterNodes)))
    ))))
  }
}