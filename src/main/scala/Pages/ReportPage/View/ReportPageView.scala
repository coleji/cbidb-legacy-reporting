package Pages.ReportPage.View

import Components.HiddenInput
import JSONDecoders._
import Pages.ReportPage.Messages._
import Pages.ReportPage.Model.FilterState.CompositeFilter
import Pages.ReportPage.Model._
import VNode.SnabbdomFacade.VNode
import VNode.SnabbdomFacade.snabbdom.h
import core._
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.response.SimpleHttpResponse
import monix.execution.Scheduler.Implicits.global
import org.scalajs.dom.document

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}
import scala.scalajs.js.{JSON, URIUtils}
import scala.util.{Failure, Success}

class ReportPageView(render: VNode => Unit) extends View[ReportPageModel](render) {
  private val view = this

  def apply(model: ReportPageModel): VNode = {
    // start render
    model.options match {
      case Uninitialized => {
        val request = HttpRequest("http://localhost:9000/reporting/get-report-run-options")
        request.send().onComplete({
          case res:Success[SimpleHttpResponse] => SuccessMessage(view)(model)({
            val rawResult: String = res.get.body
            val rawJSON: js.Object = JSON.parse(rawResult).asInstanceOf[js.Object]
            rawJSON.asInstanceOf[ReportableEntityResultWrapper]
          })
          case e: Failure[SimpleHttpResponse] => println("Async call failed")
        })
        MarkInitialized(view)(model)()
        h("div#page", "uninitialized!": js.Any)
      }
      case Waiting => {
        h("div#page1", js.Array(
          h("span#yo", "waiting!": js.Any)
        ))
      }
      case AsyncSuccess(_: js.Array[ReportableEntity]) => {
        val entityDropdown = EntityDropdown(SetEntity(view)(model))(model)
        val tdStyle = json("style" -> json(
          "verticalAlign" -> ("top": js.Any),
          "padding" -> "25px",
          "width" -> "50%"
        ))

        val fullWidth = json("style" -> json("width" -> "100%"))
        println(model.getSpecString)
        h("div#whatever", js.Array(
          entityDropdown,
          h("br"),
          h("table", fullWidth, h("tbody", h("tr", js.Array(
            h("td", tdStyle, model.filters match {
              case None => h("span", "no filters": js.Any)
              case Some(cf: CompositeFilter) => FiltersComponent(
                SearchModelForHash(view)(model),
                UpdateFilterValue(view)(model),
                UpdateFilterType(view)(model),
                AddSingleFilter(view)(model),
                DeleteFilter(view)(model),
                AddNestedCompositeFilter(view)(model)
              )(model, 0, cf)
            }),
            h("td", tdStyle, FieldsComponent(UpdateFields(view)(model))(model))
          )))),
          h("br"),
          h("form", {json("props" -> json("method" -> "get", "action" -> "http://localhost:9000/report"))}, js.Array(
            HiddenInput("baseEntityString", model.selectedEntity.get.entityName),
            HiddenInput("filterSpec", model.getSpecString),
            HiddenInput("fieldSpec", model.fields.get.map(f => f.fieldName).mkString(",")),
            HiddenInput("outputType", "tsv"),
            h("button.btn.btn-primary", json("props" -> json("type" -> "submit")), "Run Report": js.Any)
          ))
        ))
      }
    }
  }
}
