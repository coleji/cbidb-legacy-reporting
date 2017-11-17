package Pages.ReportPage.Messages

import Pages.ReportPage.Model.FilterState.{Comparator, CompositeFilter, SingleFilter}
import Pages.ReportPage.Model.{ReportFilterDefinition, ReportFilterValue, ReportPageModel, ReportableEntity}
import core.Main.dropdownNull
import core.Message

object SetEntity extends Message[ReportPageModel, String] {
  def update: ReportPageModel => String => ReportPageModel =
    model => selectedEntityName => {
      selectedEntityName match {
        case `dropdownNull` => ReportPageModel(model.options, None, None, None)
        case name => {
          val selectedEntity: ReportableEntity = model.getOptions.filter(_.entityName == name).head
          if (selectedEntity.entityName == "ApClassInstance") {
            val filterYear: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "ApClassInstanceFilterYear").head
            val filterType: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "ApClassInstanceFilterType").head
            ReportPageModel(model.options, Some(selectedEntity), Some(new CompositeFilter(Comparator.AND, List(
                new SingleFilter(ReportFilterValue(filterYear, "2017"))
              ))),
              Some(Set(selectedEntity.fieldData.head))
            )
          } else if (selectedEntity.entityName == "JpClassInstance") {
            val filterYear: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "JpClassInstanceFilterYear").head
            val filterType: ReportFilterDefinition = selectedEntity.filterData.filter(_.filterName == "JpClassInstanceFilterType").head
            ReportPageModel(model.options, Some(selectedEntity), Some(new CompositeFilter(Comparator.AND, List(
                new SingleFilter(ReportFilterValue(filterYear, "2017"))
              ))),
              Some(Set(selectedEntity.fieldData.head))
            )
          } else {
            ReportPageModel(model.options, Some(selectedEntity), Some(new CompositeFilter(Comparator.AND, List.empty)), Some(Set(selectedEntity.fieldData.head)))
          }
        }
      }
    }
}
