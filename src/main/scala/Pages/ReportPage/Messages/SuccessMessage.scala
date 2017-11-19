package Pages.ReportPage.Messages

import JSONDecoders.{ReportableEntityNative, ReportableEntityResultWrapper}
import Pages.ReportPage.Model.FilterState.{Comparator, CompositeFilter, SingleFilter}
import Pages.ReportPage.Model._
import core.{AsyncSuccess, Message}

import scala.scalajs.js

object SuccessMessage extends Message[ReportPageModel, ReportableEntityResultWrapper] {
  def update: ReportPageModel => ReportableEntityResultWrapper => ReportPageModel =
    _ => wrapper => {
      val entitiesCast = wrapper.data.runOptions.asInstanceOf[js.Array[ReportableEntityNative]]
      val entities = entitiesCast.map(e => ReportableEntity(
        e.entityName,
        e.displayName,
        e.fieldData.map(f => ReportField(f.fieldName, f.fieldDisplayName, f.isDefault)),
        e.filterData.map(f => ReportFilterDefinition(
          f.filterName,
          f.displayName,
          FilterType(f.filterType),
          f.default,
          f.values.map(d => d.map(v => ReportFilterDropdownValue(v.display, v.`return`)))
        ))
      ))

      val firstEntity: ReportableEntity = entities.head

      println(firstEntity.fieldData.filter(_.isDefault).toSet)
      println(firstEntity.fieldData.filter(_.isDefault).length)

      ReportPageModel(
        AsyncSuccess(entities),
        Some(firstEntity),
        Some(new CompositeFilter(Comparator.AND, List.empty)),
        Some(firstEntity.fieldData.filter(_.isDefault).toSet)
      )
    }
}