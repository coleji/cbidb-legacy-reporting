package Pages.ReportPage.Messages

import Pages.ReportPage.Model.FilterState.{Comparator, CompositeFilter, SingleFilter}
import Pages.ReportPage.Model.{ReportFilterDefinition, ReportFilterValue, ReportPageModel, ReportableEntity}
import core.Main.dropdownNull
import core.Message

object SetEntity extends Message[ReportPageModel, String] {
  def update: ReportPageModel => String => ReportPageModel =
    model => selectedEntityName => {
      println("Setting entity")
      selectedEntityName match {
        case `dropdownNull` => ReportPageModel(model.options, None, None)
        case name => {
          val selectedEntity: ReportableEntity = model.getOptions.filter(_.entityName == name).head
          if (selectedEntity.entityName == "ApClassInstance") {
            val filterYear: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "ApClassInstanceFilterYear").head
            val filterType: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "ApClassInstanceFilterType").head
            ReportPageModel(model.options, Some(selectedEntity), Some(new CompositeFilter(Comparator.AND, List(
              new CompositeFilter(Comparator.OR, List(
                new SingleFilter(ReportFilterValue(filterYear, "2015")),
                new SingleFilter(ReportFilterValue(filterYear, "2016")),
                new SingleFilter(ReportFilterValue(filterYear, "2016")),
                new SingleFilter(ReportFilterValue(filterYear, "2018")),
                new SingleFilter(ReportFilterValue(filterType, "1"))
              )))
            )))
          } else if (selectedEntity.entityName == "JpClassInstance") {
            val filterYear: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "JpClassInstanceFilterYear").head
            val filterType: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "JpClassInstanceFilterType").head
            ReportPageModel(model.options, Some(selectedEntity), Some(new CompositeFilter(Comparator.AND, List(
              new CompositeFilter(Comparator.OR, List(
                new SingleFilter(ReportFilterValue(filterType, "1")),
                new SingleFilter(ReportFilterValue(filterType, "2")),
                new SingleFilter(ReportFilterValue(filterYear, "2016"))
              )))
            )))
          } else {
            ReportPageModel(model.options, Some(selectedEntity), None)
          }
        }
      }
    }
}
