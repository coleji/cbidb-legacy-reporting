package Pages.ReportPage.View

import Components.HiddenInput
import JSONDecoders._
import Pages.ReportPage.Messages._
import Pages.ReportPage.Model.FilterState.CompositeFilter
import Pages.ReportPage.Model._
import VNode.SnabbdomFacade.VNode
import _root_.VNode._
import core._
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.response.SimpleHttpResponse
import monix.execution.Scheduler.Implicits.global

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}

class ReportPageView(render: VNode => Unit) extends View[ReportPageModel](render) {
  private val view = this

  def apply(model: ReportPageModel): VNode = {
    // start render
    model.options match {
      case Uninitialized => {
        val request = HttpRequest(Main.API_LOCATION + "/reporting/get-report-run-options")
        request.send().onComplete({
          case res:Success[SimpleHttpResponse] => SuccessMessage(view)(model)({
            val rawResult: String = res.get.body
            val rawJSON: js.Object = JSON.parse(rawResult).asInstanceOf[js.Object]
            rawJSON.asInstanceOf[ReportableEntityResultWrapper]
          })
          case e: Failure[SimpleHttpResponse] => println("Async call failed")
        })
        MarkInitialized(view)(model)()
        div(id = "page", contents = "uninitialized!")
      }
      case Waiting => {
        div(id = "page", contents = span("waiting!"))
      }
      case AsyncSuccess(_: js.Array[ReportableEntity]) => {
        val entityDropdown: VNode = EntityDropdown(SetEntity(view)(model))(model)
        val tdStyle = Map(
          "verticalAlign" -> "top",
          "padding" -> "25px",
          "width" -> "50%"
        )

        val fullWidth = Map("width" -> "100%")
        div(id = "whatever", contents = js.Array(
          entityDropdown,
          table(style = fullWidth, contents = tbody(tr(js.Array(
            td(style = tdStyle, contents = model.filters match {
              case None => span("no filters")
              case Some(cf: CompositeFilter) => FiltersComponent(
                SearchModelForHash(view)(model),
                UpdateFilterValue(view)(model),
                UpdateFilterType(view)(model),
                AddSingleFilter(view)(model),
                DeleteFilter(view)(model),
                AddNestedCompositeFilter(view)(model)
              )(model, 0, cf)
            }),
            td(
              style = tdStyle,
              contents = FieldsComponent(UpdateFields(view)(model))(model)
            )
          )))),
          br(),
          form(props = Map("method" -> "post", "action" -> (Main.API_LOCATION + "/report")), contents = js.Array(
            HiddenInput("baseEntityString", model.selectedEntity.get.entityName),
            HiddenInput("filterSpec", model.getSpecString),
            HiddenInput("fieldSpec", model.fields.get.map(f => f.fieldName).mkString(",")),
            HiddenInput("outputType", "tsv"),
            button(
              classes = List("btn", "btn-primary"),
              props = Map("type" -> "submit"),
              contents = "Run Report"
            )
          ))
        ))
      }
    }
  }
}
