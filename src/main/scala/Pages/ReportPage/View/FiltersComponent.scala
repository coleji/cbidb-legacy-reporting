package Pages.ReportPage.View

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Model.FilterState.{CompositeFilter, SingleFilter}
import Pages.ReportPage.Model.ReportPageModel
import core.Message.SpecificPageMessage
import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h

import scala.scalajs.js
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
  val colspan2: js.Object = js.Dynamic.literal("props" -> js.Dynamic.literal("colSpan" -> "3"))

  def apply(model: ReportPageModel, level: Int, cf: CompositeFilter): VNode = {
    val addSingleFilterProps = js.Dynamic.literal(
      "on" -> js.Dynamic.literal("click" -> (() => addSingleFilter(cf))),
      "props" -> js.Dynamic.literal("type" -> "button")
    )
    val addNestedCompositeFilterProps = js.Dynamic.literal("on" -> js.Dynamic.literal("click" -> (() => {
      addNestedCompositeFilter(cf.hashCode())
    })))
    val spacerProps: js.Object = js.Dynamic.literal("style" -> js.Dynamic.literal(
      "width" -> ((SPACING_PER_LEVEL * level) + "px"),
      "textAlign" -> "right",
      "paddingRight" -> "5px"
    ))
    val downTriangle = "\u25BC"
    val rightTriangle = "\u25B6"
    val filterNodes: js.Array[VNode] = cf.filters.map({
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
      tr,
      h("tr", h("td", cf.comparator.toString: js.Any))
    )).toJSArray
      .concat(js.Array(
        h("button.btn.btn-default.btn-xs", addSingleFilterProps, downTriangle: js.Any),
        h("span", "   ": js.Any),
        h("button.btn.btn-default.btn-xs", addNestedCompositeFilterProps, rightTriangle: js.Any)
      ))

    val spacerChildren: js.Array[VNode] = {
      if (level > 0) {
        val payload: js.Object = js.Dynamic.literal(
          "deleteType" -> DeleteFilter.DELETE_TYPE_COMPOSITE,
          "deleteHash" -> cf.hashCode()
        )
        val props = js.Dynamic.literal(
          "on" -> js.Dynamic.literal("click" -> (() => {
            deleteFilter(payload)
          })),
          "props" -> js.Dynamic.literal("type" -> "button")
        )
        js.Array(h("button.btn.btn-primary.btn-xs", props, "X": js.Any))
      } else js.Array()
    }

    val borderRight = js.Dynamic.literal("style" -> js.Dynamic.literal("border-right" -> "1px solid #aaaaaa"))
    val paddingLeft = js.Dynamic.literal("style" -> js.Dynamic.literal("paddingLeft" -> "5px"))

    h("table", h("tbody", h("tr", js.Array(
      h("td", {if (level > 0) borderRight else js.Dynamic.literal()}, h("div.spacer", spacerProps, spacerChildren)),
      h("td", paddingLeft, h("table", h("tbody", filterNodes)))
    ))))
  }
}