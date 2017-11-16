package Pages.ReportPage.Messages

import JSONDecoders.{ReportableEntityNative, ReportableEntityResultWrapper}
import Pages.ReportPage.Model.FilterState.{Comparator, CompositeFilter, SingleFilter}
import Pages.ReportPage.Model._
import core.{AsyncSuccess, Message}

import scala.scalajs.js

object SuccessMessage extends Message[ReportPageModel, ReportableEntityResultWrapper] {
  def update: ReportPageModel => ReportableEntityResultWrapper => ReportPageModel =
    _ => wrapper => {
      wrapper.data.runOptions
      val entitiesCast = wrapper.data.runOptions.asInstanceOf[js.Array[ReportableEntityNative]]
      val entities = entitiesCast.map(e => ReportableEntity(
        e.entityName,
        e.displayName,
        e.fieldData.map(f => ReportField(f.fieldName, f.fieldDisplayName)),
        e.filterData.map(f => ReportFilterDefinition(
          f.filterName,
          f.displayName,
          FilterType(f.filterType),
          f.default,
          f.values.map(v => ReportFilterDropdownValue(v.display, v.`return`))
        ))
      ))

      val apClassInstance: ReportableEntity = entities.filter(_.entityName == "ApClassInstance").head
      val filterYear: ReportFilterDefinition = apClassInstance.filterData.filter(_.filterName == "ApClassInstanceFilterYear").head
      val filterType: ReportFilterDefinition = apClassInstance.filterData.filter(_.filterName == "ApClassInstanceFilterType").head

      ReportPageModel(
        AsyncSuccess(entities),
        Some(apClassInstance),
        Some(new CompositeFilter(Comparator.AND, List(
          new CompositeFilter(Comparator.OR, List(
            new SingleFilter(ReportFilterValue(filterYear, "2015")),
            new SingleFilter(ReportFilterValue(filterYear, "2016")),
            new SingleFilter(ReportFilterValue(filterYear, "2016")),
            new SingleFilter(ReportFilterValue(filterYear, "2018")),
            new SingleFilter(ReportFilterValue(filterType, "1"))
          )))
        )),
        Some(Set(apClassInstance.fieldData(2)))
      )
    }
}